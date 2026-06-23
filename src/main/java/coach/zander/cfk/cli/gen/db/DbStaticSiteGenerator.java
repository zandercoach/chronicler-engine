package coach.zander.cfk.cli.gen.db;

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

import coach.zander.cfk.beans.AdventureBean;
import coach.zander.cfk.beans.CfkPageBean;
import coach.zander.cfk.beans.ChroniclesBean;
import coach.zander.cfk.beans.HeldBean;
import coach.zander.cfk.beans.HomeBean;
import coach.zander.cfk.beans.IdxBean;
import coach.zander.cfk.beans.KreaturBean;
import coach.zander.cfk.beans.MapBean;
import coach.zander.cfk.beans.NavigationBean;
import coach.zander.cfk.beans.NscBean;
import coach.zander.cfk.beans.OrtBean;
import coach.zander.cfk.cli.gen.StaticUrlResolver;
import coach.zander.cfk.dao.AdventureDao;
import coach.zander.cfk.dao.HeldDao;
import coach.zander.cfk.dao.KreaturDao;
import coach.zander.cfk.dao.NscDao;
import coach.zander.cfk.dao.OrtDao;
import coach.zander.cfk.links.CfkReferenceInsertionEventHandler;
import coach.zander.cfk.links.InternalLink;
import coach.zander.cfk.links.InternalLinksProvider;
import coach.zander.cfk.model.Adventure;
import coach.zander.cfk.model.Held;
import coach.zander.cfk.model.Kreatur;
import coach.zander.cfk.model.Nsc;
import coach.zander.cfk.model.Ort;
import coach.zander.cfk.model.Wegpunkt;
import coach.zander.cfk.util.DateFormatter;
import coach.zander.cfk.util.UrlResolver;

@ComponentScan("coach.zander.cfk.dao")
@Service
public class DbStaticSiteGenerator {

  private UrlResolver resolver;
  private String source;
  private String destination;
  private VelocityContext baseCtx;

  @Autowired
  private AdventureDao adventureDao;

  @Autowired
  private HeldDao heldDao;

  @Autowired
  private KreaturDao kreaturDao;

  @Autowired
  private NscDao nscDao;

  @Autowired
  private OrtDao ortDao;

  public void close() {
    System.out.println("********** CFKGenerator - Close! **********");
  }

  private void createPageChronicles() throws IOException {

    ChroniclesBean bean = new ChroniclesBean();
    List<Adventure> adventures = adventureDao.readAllOrderedByDate();
    bean.setPageTitle("Die Chroniken");
    bean.setAdventures(adventures);
    mergeTemplateWithBeanDataToFile("chronicles", bean);
  }

  private void createPageHome() throws ResourceNotFoundException, ParseErrorException, MethodInvocationException,
      IOException {

    HomeBean bean = new HomeBean();
    Adventure adventure = adventureDao.findLatest();
    bean.setAdventure(adventure);
    bean.setPageTitle("Startseite");

    mergeTemplateWithBeanDataToFile("home", bean);
  }

  private void createPageIdx() throws IOException {

    IdxBean bean = new IdxBean();
    bean.setPageTitle("Index");
    bean.setAdventures(adventureDao.readAll());
    bean.setHelden(heldDao.readAll());
    bean.setOrte(ortDao.readAll());
    bean.setNscs(nscDao.readAll());
    bean.setKreaturen(kreaturDao.readAll());

    mergeTemplateWithBeanDataToFile("idx", bean);
  }

  private void createPageMap() throws IOException {
    MapBean bean = new MapBean();
    bean.setPageTitle("Karte");
    bean.setMap("map");

    List<Adventure> adventures = adventureDao.readAllOrderedByDate();
    bean.setAdventures(adventures);
    // generateMap(adventures);

    mergeTemplateWithBeanDataToFile("map", bean);
  }

  private void createPagesAdventure() throws IOException {
    List<Adventure> adventures = adventureDao.readAll();
    for (Adventure adventure : adventures) {
      AdventureBean bean = new AdventureBean();
      bean.setAdventure(adventure);
      mergeTemplateWithBeanDataToFile("adventure", bean, resolver.resolveUrlFor(adventure));
    }
  }

  private void createPagesHelden() throws IOException {
    List<Held> helden = heldDao.readAll();
    for (Held held : helden) {
      HeldBean bean = new HeldBean();
      bean.setHeld(held);
      mergeTemplateWithBeanDataToFile("object", bean, resolver.resolveUrlFor(held));
    }
  }

  private void createPagesKreaturen() throws IOException {
    List<Kreatur> kreaturen = kreaturDao.readAll();
    for (Kreatur kreatur : kreaturen) {
      KreaturBean bean = new KreaturBean();
      bean.setKreatur(kreatur);
      mergeTemplateWithBeanDataToFile("object", bean, resolver.resolveUrlFor(kreatur));
    }
  }

  private void createPagesNscs() throws IOException {
    List<Nsc> nscs = nscDao.readAll();
    for (Nsc nsc : nscs) {
      NscBean bean = new NscBean();
      bean.setNsc(nsc);
      mergeTemplateWithBeanDataToFile("object", bean, resolver.resolveUrlFor(nsc));
    }
  }

  private void createPagesOrte() throws IOException {
    List<Ort> orte = ortDao.readAll();
    for (Ort ort : orte) {
      OrtBean bean = new OrtBean();
      bean.setOrt(ort);
      mergeTemplateWithBeanDataToFile("object", bean, resolver.resolveUrlFor(ort));
    }
  }

  private void generateMap(List<Adventure> adventures) throws IOException {
    Collections.reverse(adventures);
    BufferedImage img = ImageIO.read(new File(source + "/map.jpg"));
    Graphics2D graphics = img.createGraphics();
    graphics.setStroke(new BasicStroke(50.0f));
    Wegpunkt previous = null;
    for (Adventure adventure : adventures) {
      for (Wegpunkt current : adventure.getWegpunkte()) {
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
    for (Adventure adventure : adventureDao.readAll()) {
      internalLinks.add(new InternalLink(adventure));
    }
    for (Held held : heldDao.readAll()) {
      internalLinks.add(new InternalLink(held));
    }
    for (Nsc nsc : nscDao.readAll()) {
      internalLinks.add(new InternalLink(nsc));
    }
    for (Ort ort : ortDao.readAll()) {
      internalLinks.add(new InternalLink(ort));
    }
    for (Kreatur kreatur : kreaturDao.readAll()) {
      internalLinks.add(new InternalLink(kreatur));
    }
    return internalLinks;
  }

  public void initialize(String source, String destination) throws IOException {
    System.out.println("********** CFKGenerator - Initialize! **********");
    this.source = source;
    this.destination = destination;

    resolver = new StaticUrlResolver(source + "/static/img");

    prepareDestination();

    Velocity.init("src/main/resources/velocity.properties");

    baseCtx = new VelocityContext();
    baseCtx.put("resolver", resolver);
    baseCtx.put("formatter", new DateFormatter());

    List<Adventure> navAdventures = adventureDao.readAll();
    List<Held> navHelden = heldDao.readAll();
    baseCtx.put("navigation", new NavigationBean(navAdventures, navHelden));

    EventCartridge ec = new EventCartridge();
    ec.addEventHandler(new CfkReferenceInsertionEventHandler(getInernalLinksProvider(), resolver));

    ec.attachToContext(baseCtx);
  }

  private void mergeTemplateWithBeanDataToFile(String templateBaseName, CfkPageBean bean) throws IOException {
    mergeTemplateWithBeanDataToFile(templateBaseName, bean, templateBaseName + ".html");
  }

  private void mergeTemplateWithBeanDataToFile(String templateBaseName, CfkPageBean bean, String destinationFileName)
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

    System.out.println("********** CFKGenerator - Run! **********");

    // generateMap(db.getAbenteuerAll());
    createPageMap();
    createPageHome();
    createPageChronicles();
    createPageIdx();
    createPagesAdventure();
    createPagesOrte();
    createPagesHelden();
    createPagesNscs();
    createPagesKreaturen();

    close();
  }
}
