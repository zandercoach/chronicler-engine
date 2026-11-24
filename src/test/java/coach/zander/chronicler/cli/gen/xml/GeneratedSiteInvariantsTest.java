package coach.zander.chronicler.cli.gen.xml;

import static org.assertj.core.api.Assertions.assertThat;

import coach.zander.chronicler.cli.gen.StaticUrlResolver;
import coach.zander.chronicler.model.ChroniclerData;
import coach.zander.chronicler.model.ChroniclerObject;
import coach.zander.chronicler.model.Endeavor;
import coach.zander.chronicler.model.Event;
import coach.zander.chronicler.model.Location;
import coach.zander.chronicler.model.Waypoint;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.transform.stream.StreamSource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.io.TempDir;

/**
 * Rules every generated page must keep, whatever its content. Where the golden master says that
 * something changed, these say that something is broken. They need no approved files, so they can
 * run against real campaign data as well: {@code -Dchronicler.data=<path of a data repo>} generates
 * from that repo instead of the fixture.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GeneratedSiteInvariantsTest {

  private static final Pattern UNRESOLVED_VELOCITY_REFERENCE =
      Pattern.compile("\\$!?\\{?[a-zA-Z][a-zA-Z0-9_.]*");
  private static final Pattern EXTERNAL = Pattern.compile("^(https?:|mailto:|javascript:|#).*");
  private static final List<String> ENTITY_PAGE_PREFIXES =
      List.of("endeavor_", "member_", "stakeholder_", "creature_", "location_");

  private static final String DATA_PROPERTY = "chronicler.data";

  /** The data repo the site is generated from; null means the fixture. */
  private String dataRepo;

  private Path site;
  private Map<String, Document> pages;
  private Map<String, String> sources;

  @BeforeAll
  void generateOnce(@TempDir Path workDir) throws IOException, JAXBException {
    String property = System.getProperty(DATA_PROPERTY);
    if (property == null || property.isBlank()) {
      site = FixtureSite.generate(workDir);
    } else {
      dataRepo = property;
      site = workDir.resolve("site");
      new XmlStaticSiteGenerator()
          .run(FixtureSite.ENGINE_STATIC, dataRepo, site.toString(), false, true, false);
    }
    pages = new LinkedHashMap<>();
    sources = new LinkedHashMap<>();
    try (Stream<Path> files = Files.list(site.resolve("html"))) {
      for (Path page :
          files.filter(p -> p.toString().endsWith(".html")).sorted().collect(Collectors.toList())) {
        String html = Files.readString(page, StandardCharsets.UTF_8);
        String name = page.getFileName().toString();
        sources.put(name, html);
        pages.put(name, Jsoup.parse(html, page.toUri().toString()));
      }
    }
    assertThat(pages).as("generated pages").isNotEmpty();
  }

  /**
   * Today this cannot fail: ChroniclerReferenceInsertionEventHandler turns every unresolved
   * reference into an empty string, so a missing value vanishes silently and only the golden master
   * sees it. The test guards the day the handler is no longer attached - a Velocity upgrade changes
   * that API - when Velocity would print the references as text instead.
   */
  @Test
  void noPageContainsAnUnresolvedVelocityReference() {
    List<String> leftovers = new ArrayList<>();
    sources.forEach((name, html) -> {
      Matcher m = UNRESOLVED_VELOCITY_REFERENCE.matcher(html);
      while (m.find()) {
        leftovers.add(name + ": " + m.group());
      }
    });
    assertThat(leftovers).as("Velocity references rendered as text").isEmpty();
  }

  @Test
  void everyLocalLinkPointsToAFileThatExists() {
    List<String> broken = new ArrayList<>();
    pages.forEach((name, doc) -> {
      for (Element el : doc.select("a[href], link[href], script[src], img[src]")) {
        String target = el.hasAttr("href") ? el.attr("href") : el.attr("src");
        if (target.isEmpty() || EXTERNAL.matcher(target).matches()) {
          continue;
        }
        String path = URLDecoder.decode(target.replaceFirst("[#?].*$", ""), StandardCharsets.UTF_8);
        if (!Files.exists(site.resolve("html").resolve(path).normalize())) {
          broken.add(name + " -> " + target);
        }
      }
    });
    assertThat(broken).as("local links without a target").isEmpty();
  }

  @Test
  void everyEntityPageHasAHeadingTheNavigationAndADescription() {
    List<String> incomplete = new ArrayList<>();
    pages.forEach((name, doc) -> {
      if (ENTITY_PAGE_PREFIXES.stream().noneMatch(name::startsWith)) {
        return;
      }
      if (doc.select(".span9 h2").text().isBlank()) {
        incomplete.add(name + ": no heading");
      }
      if (doc.select(".navbar a[href=home.html]").isEmpty()) {
        incomplete.add(name + ": no navigation");
      }
      Element description = doc.selectFirst(".span9 h4:contains(Kurzbeschreibung)");
      Element next = description == null ? null : description.nextElementSibling();
      if (next == null || !"p".equals(next.tagName()) || next.text().isBlank()) {
        incomplete.add(name + ": no description");
      }
    });
    assertThat(incomplete).isEmpty();
  }

  @Test
  void everyEntityPageIsLinkedFromTheIndex() {
    List<String> linked = pages.get("idx.html").select("a[href]").eachAttr("href");
    List<String> entityPages =
        pages.keySet().stream()
            .filter(name -> ENTITY_PAGE_PREFIXES.stream().anyMatch(name::startsWith))
            .collect(Collectors.toList());
    assertThat(entityPages).isNotEmpty();
    assertThat(linked).as("links on idx.html").containsAll(entityPages);
  }

  @Test
  void everyEventAppearsOnThePageOfItsEndeavorAndOfEveryoneInvolved() throws JAXBException {
    StaticUrlResolver resolver = new StaticUrlResolver("");
    List<String> missing = new ArrayList<>();
    for (Endeavor endeavor : loadData().getEndeavors()) {
      for (Event event : endeavor.getEvents()) {
        List<String> involved = new ArrayList<>();
        involved.add(resolver.resolveUrlFor(endeavor));
        Stream.of(event.getMembers(), event.getStakeholders(), event.getCreatures())
            .flatMap(List::stream)
            .map(ChroniclerObject.class::cast)
            .forEach(object -> involved.add(resolver.resolveUrlFor(object)));
        if (event.getLocation() != null) {
          involved.add(resolver.resolveUrlFor(event.getLocation()));
        }
        String shownTitle = asRendered(event.getTitle());
        for (String page : involved) {
          Document doc = pages.get(page);
          boolean listed =
              doc != null
                  && doc.select(".span9 h5").stream()
                      .anyMatch(h5 -> h5.text().endsWith(shownTitle));
          if (!listed) {
            missing.add("'" + event.getTitle() + "' on " + page);
          }
        }
      }
    }
    assertThat(missing).as("events missing from the pages of those involved").isEmpty();
  }

  @Test
  void everyPlaceOnARouteAndItsEndeavorListEachOther() throws JAXBException {
    StaticUrlResolver resolver = new StaticUrlResolver("");
    List<String> missing = new ArrayList<>();
    for (Endeavor endeavor : loadData().getEndeavors()) {
      String endeavorPage = resolver.resolveUrlFor(endeavor);
      for (Waypoint waypoint : endeavor.getWaypoints()) {
        Location location = waypoint.getLocation();
        if (location == null) {
          continue;
        }
        String locationPage = resolver.resolveUrlFor(location);
        if (!sidebarLinks(locationPage).contains(endeavorPage)) {
          missing.add(endeavorPage + " on " + locationPage);
        }
        if (!sidebarLinks(endeavorPage).contains(locationPage)) {
          missing.add(locationPage + " on " + endeavorPage);
        }
      }
    }
    assertThat(missing).as("route places and endeavors not listing each other").isEmpty();
  }

  @Test
  void everyWaypointNamingAPlaceLiesOnIt() throws JAXBException {
    List<String> off = new ArrayList<>();
    for (Endeavor endeavor : loadData().getEndeavors()) {
      for (Waypoint waypoint : endeavor.getWaypoints()) {
        Location location = waypoint.getLocation();
        if (location != null
            && !(waypoint.getX().equals(location.getX())
                && waypoint.getY().equals(location.getY()))) {
          off.add(
              location.getName()
                  + " in '"
                  + endeavor.getTitle()
                  + "': waypoint "
                  + waypoint.getX()
                  + "/"
                  + waypoint.getY()
                  + ", place "
                  + location.getX()
                  + "/"
                  + location.getY());
        }
      }
    }
    assertThat(off).as("waypoints whose coordinates are not those of their place").isEmpty();
  }

  private List<String> sidebarLinks(String page) {
    Document doc = pages.get(page);
    return doc == null
        ? List.of()
        : doc.select(".sidebar-nav .nav-list a").eachAttr("href").stream()
            .map(href -> href.substring(href.lastIndexOf('/') + 1))
            .toList();
  }

  /**
   * A title as the page shows it: wiki links "[[Page|Caption]]" and "[[Page]]" render as their
   * caption.
   */
  private static String asRendered(String title) {
    return title
        .replaceAll("\\[\\[[^\\]|]*\\|([^\\]]*)\\]\\]", "$1")
        .replaceAll("\\[\\[([^\\]]*)\\]\\]", "$1");
  }

  private ChroniclerData loadData() throws JAXBException {
    System.setProperty("javax.xml.accessExternalDTD", "all");
    File data =
        dataRepo == null
            ? new File(FixtureSite.FIXTURE_ROOT, "xml-data/data.xml")
            : new File(dataRepo, "data/data.xml");
    return (ChroniclerData)
        JAXBContext.newInstance(ChroniclerData.class)
            .createUnmarshaller()
            .unmarshal(new StreamSource(data));
  }
}
