package coach.zander.chronicler.cli.gen.db;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coach.zander.chronicler.beans.ChroniclerPageBean;
import coach.zander.chronicler.beans.ChroniclesBean;
import coach.zander.chronicler.beans.CreatureBean;
import coach.zander.chronicler.beans.EndeavorBean;
import coach.zander.chronicler.beans.HomeBean;
import coach.zander.chronicler.beans.IdxBean;
import coach.zander.chronicler.beans.LocationBean;
import coach.zander.chronicler.beans.MapBean;
import coach.zander.chronicler.beans.MemberBean;
import coach.zander.chronicler.beans.NavigationBean;
import coach.zander.chronicler.beans.StakeholderBean;
import coach.zander.chronicler.cli.gen.StaticUrlResolver;
import coach.zander.chronicler.dao.CreatureDao;
import coach.zander.chronicler.dao.EndeavorDao;
import coach.zander.chronicler.dao.LocationDao;
import coach.zander.chronicler.dao.MemberDao;
import coach.zander.chronicler.dao.StakeholderDao;
import coach.zander.chronicler.links.ChroniclerReferenceInsertionEventHandler;
import coach.zander.chronicler.links.InternalLink;
import coach.zander.chronicler.links.InternalLinksProvider;
import coach.zander.chronicler.model.Creature;
import coach.zander.chronicler.model.Endeavor;
import coach.zander.chronicler.model.Location;
import coach.zander.chronicler.model.Member;
import coach.zander.chronicler.model.Stakeholder;
import coach.zander.chronicler.model.Waypoint;
import coach.zander.chronicler.util.DateFormatter;
import coach.zander.chronicler.util.UrlResolver;

@ComponentScan("coach.zander.chronicler.dao")
@Service
public class DbStaticSiteGenerator {

  private UrlResolver resolver;
  private String source;
  private String destination;
  private VelocityContext baseCtx;

  @Autowired
  private EndeavorDao endeavorDao;

  @Autowired
  private MemberDao memberDao;

  @Autowired
  private CreatureDao creatureDao;

  @Autowired
  private StakeholderDao stakeholderDao;

  @Autowired
  private LocationDao locationDao;

  public void close() {
    System.out.println("********** ChroniclerGenerator - Close! **********");
  }

  private void createPageChronicles() throws IOException {

    ChroniclesBean bean = new ChroniclesBean();
    List<Endeavor> endeavor = endeavorDao.readAllOrderedByDate();
    bean.setPageTitle("Die Chroniken");
    bean.setEndeavors(endeavor);
    mergeTemplateWithBeanDataToFile("chronicles", bean);
  }

  private void createPageHome() throws ResourceNotFoundException, ParseErrorException, MethodInvocationException,
      IOException {

    HomeBean bean = new HomeBean();
    Endeavor endeavor = endeavorDao.findLatest();
    bean.setEndeavor(endeavor);
    bean.setPageTitle("Startseite");

    mergeTemplateWithBeanDataToFile("home", bean);
  }

  private void createPageIdx() throws IOException {

    IdxBean bean = new IdxBean();
    bean.setPageTitle("Index");
    bean.setEndeavors(endeavorDao.readAll());
    bean.setMembers(memberDao.readAll());
    bean.setLocations(locationDao.readAll());
    bean.setStakeholders(stakeholderDao.readAll());
    bean.setCreatures(creatureDao.readAll());

    mergeTemplateWithBeanDataToFile("idx", bean);
  }

  private void createPageMap() throws IOException {
    MapBean bean = new MapBean();
    bean.setPageTitle("Karte");
    bean.setMap("map");

    List<Endeavor> endeavors = endeavorDao.readAllOrderedByDate();
    bean.setEndeavors(endeavors);
    // generateMap(endeavors);

    mergeTemplateWithBeanDataToFile("map", bean);
  }

  private void createPagesAdventure() throws IOException {
    List<Endeavor> endeavors = endeavorDao.readAll();
    for (Endeavor endeavor : endeavors) {
      EndeavorBean bean = new EndeavorBean();
      bean.setEndeavor(endeavor);
      mergeTemplateWithBeanDataToFile("endeavor", bean, resolver.resolveUrlFor(endeavor));
    }
  }

  private void createPagesMembers() throws IOException {
    List<Member> members = memberDao.readAll();
    for (Member member : members) {
      MemberBean bean = new MemberBean();
      bean.setMember(member);
      mergeTemplateWithBeanDataToFile("object", bean, resolver.resolveUrlFor(member));
    }
  }

  private void createPagesCreatures() throws IOException {
    List<Creature> creatures = creatureDao.readAll();
    for (Creature creature : creatures) {
      CreatureBean bean = new CreatureBean();
      bean.setCreature(creature);
      mergeTemplateWithBeanDataToFile("object", bean, resolver.resolveUrlFor(creature));
    }
  }

  private void createPagesNscs() throws IOException {
    List<Stakeholder> stakeholders = stakeholderDao.readAll();
    for (Stakeholder stakeholder : stakeholders) {
      StakeholderBean bean = new StakeholderBean();
      bean.setStakeholder(stakeholder);
      mergeTemplateWithBeanDataToFile("object", bean, resolver.resolveUrlFor(stakeholder));
    }
  }

  private void createPagesOrte() throws IOException {
    List<Location> locations = locationDao.readAll();
    for (Location location : locations) {
      LocationBean bean = new LocationBean();
      bean.setLocation(location);
      mergeTemplateWithBeanDataToFile("object", bean, resolver.resolveUrlFor(location));
    }
  }

  private void generateMap(List<Endeavor> endeavors) throws IOException {
    Collections.reverse(endeavors);
    BufferedImage img = ImageIO.read(new File(source + "/map.jpg"));
    Graphics2D graphics = img.createGraphics();
    graphics.setStroke(new BasicStroke(50.0f));
    Waypoint previous = null;
    for (Endeavor endeavor : endeavors) {
      for (Waypoint current : endeavor.getWaypoints()) {
        if (previous != null) {
          graphics.drawLine(previous.getX(), previous.getY(), current.getX(), current.getY());
        }
        previous = current;
      }
    }
    ImageIO.write(img, "jpg", new File(destination + "/img/map.jpg"));
  }

  private InternalLinksProvider getInernalLinksProvider() {
    InternalLinksProvider provider = new DbInternalLinksProvider() {
      @Override
      public List<InternalLink> getInternalLinks() {
        return DbStaticSiteGenerator.this.getInternalLinks();
      }
    };
    return provider;
  }

  public List<InternalLink> getInternalLinks() {
    List<InternalLink> internalLinks = new ArrayList<InternalLink>();
    for (Endeavor endeavor : endeavorDao.readAll()) {
      internalLinks.add(new InternalLink(endeavor));
    }
    for (Member member : memberDao.readAll()) {
      internalLinks.add(new InternalLink(member));
    }
    for (Stakeholder stakeholder : stakeholderDao.readAll()) {
      internalLinks.add(new InternalLink(stakeholder));
    }
    for (Location location : locationDao.readAll()) {
      internalLinks.add(new InternalLink(location));
    }
    for (Creature creature : creatureDao.readAll()) {
      internalLinks.add(new InternalLink(creature));
    }
    return internalLinks;
  }

  public void initialize(String source, String destination) throws IOException {
    System.out.println("********** ChroniclerGenerator - Initialize! **********");
    this.source = source;
    this.destination = destination;

    resolver = new StaticUrlResolver(source + "/static/img");

    prepareDestination();

    Velocity.init("src/main/resources/velocity.properties");

    baseCtx = new VelocityContext();
    baseCtx.put("resolver", resolver);
    baseCtx.put("formatter", new DateFormatter());

    List<Endeavor> navAdventures = endeavorDao.readAll();
    List<Member> navHelden = memberDao.readAll();
    baseCtx.put("navigation", new NavigationBean(navAdventures, navHelden));

    EventCartridge ec = new EventCartridge();
    ec.addEventHandler(new ChroniclerReferenceInsertionEventHandler(getInernalLinksProvider(), resolver));

    ec.attachToContext(baseCtx);
  }

  private void mergeTemplateWithBeanDataToFile(String templateBaseName, ChroniclerPageBean bean) throws IOException {
    mergeTemplateWithBeanDataToFile(templateBaseName, bean, templateBaseName + ".html");
  }

  private void mergeTemplateWithBeanDataToFile(String templateBaseName, ChroniclerPageBean bean, String destinationFileName)
      throws IOException {
    VelocityContext ctx = new VelocityContext(baseCtx);
    ctx.put("bean", bean);
    Template tpl = Velocity.getTemplate(templateBaseName + ".vm");
    FileWriter fw = new FileWriter(new File(destination + "/html/" + destinationFileName));
    tpl.merge(ctx, fw);
    fw.flush();
  }

  private void prepareDestination() throws IOException {
    File destinationDir = new File(destination);

    if (destinationDir.exists()) {
      System.out.println("Removing direcory: " + destinationDir.getPath());
      FileUtils.deleteDirectory(destinationDir);
    }

    FileUtils.copyDirectory(new File(source + "/static"), new File(destination));
  }

  @Transactional
  public void run(String source, String destination) throws IOException {
    initialize(source, destination);

    System.out.println("********** ChroniclerGenerator - Run! **********");

    // generateMap(db.getEndeavorAll());
    createPageMap();
    createPageHome();
    createPageChronicles();
    createPageIdx();
    createPagesAdventure();
    createPagesOrte();
    createPagesMembers();
    createPagesNscs();
    createPagesCreatures();

    close();
  }
}
