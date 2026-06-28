package coach.zander.chronicler.cli.gen.xml;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

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
import coach.zander.chronicler.links.ChroniclerReferenceInsertionEventHandler;
import coach.zander.chronicler.model.ChroniclerData;
import coach.zander.chronicler.model.Creature;
import coach.zander.chronicler.model.Endeavor;
import coach.zander.chronicler.model.Event;
import coach.zander.chronicler.model.Location;
import coach.zander.chronicler.model.Member;
import coach.zander.chronicler.model.Stakeholder;
import coach.zander.chronicler.model.Waypoint;
import coach.zander.chronicler.util.DateFormatter;
import coach.zander.chronicler.util.UrlResolver;

public class XmlStaticSiteGenerator {

  private static final String JS_PATH = "/js";
  private static final String CSS_PATH = "/css";
  private static final String TILES_PATH = "/av_tiles";
  private static final String HTML_PATH = "/html";
  private static final String IMG_PATH = "/img";
  private static final String XML_PATH = "/data/data.xml";
  private static final String MAP_PATH = "/data/map.jpg";

  private UrlResolver resolver;
  private String chroniclerSources;
  private String projectSources;
  private String destination;
  private VelocityContext baseCtx;  

  public void close() {
    System.out.println("********** ChroniclerGenerator - Close! **********");
  }

  private void createPageChronicles(ChroniclerData data) throws IOException {

    ChroniclesBean bean = new ChroniclesBean();
    List<Endeavor> endeavors = data.getEndeavors();
    // IMPL: Order Endeavors by Date!
    bean.setPageTitle("Die Chroniken");
    bean.setEndeavors(endeavors);
    mergeTemplateWithBeanDataToFile("chronicles", bean);

    System.out.println("--> Page 'chronicles' created!");
  }

  private void createPageHome(ChroniclerData data) throws ResourceNotFoundException, ParseErrorException, MethodInvocationException,
      IOException {

    HomeBean bean = new HomeBean();
    List<Endeavor> endeavors = data.getEndeavors();

    // Latest Endeavor for index.html
    Endeavor endeavor = endeavors.get(0);
    bean.setEndeavor(endeavor);
    bean.setPageTitle("Startseite");
    mergeTemplateWithBeanDataToFile("home", bean);
    
    System.out.println("--> Page 'home' created!");
  }

  private void createPageIdx(ChroniclerData data) throws IOException {

    IdxBean bean = new IdxBean();
    bean.setPageTitle("Index");
    bean.setEndeavors(data.getEndeavors());
    bean.setMembers(data.getMembers());
    bean.setLocations(data.getLocations());
    bean.setStakeholders(data.getStakeholders());
    bean.setCreatures(data.getCreatures());
    mergeTemplateWithBeanDataToFile("idx", bean);

    System.out.println("--> Page 'idx' created!");
  }

  private void createPageMap(ChroniclerData data) throws IOException {
    MapBean bean = new MapBean();
    bean.setPageTitle("Karte");
    bean.setMap("map");

    List<Endeavor> endeavors = data.getEndeavors();

    // IMPL: order endeavors by date!
    bean.setEndeavors(endeavors);

    mergeTemplateWithBeanDataToFile("map", bean);

    System.out.println("--> Page 'map' created!");
  }

  private void createPagesEndeavors(ChroniclerData data) throws IOException {
    List<Endeavor> endeavors = data.getEndeavors();
    for (Endeavor endeavor : endeavors) {
      EndeavorBean bean = new EndeavorBean();
      bean.setEndeavor(endeavor);
      mergeTemplateWithBeanDataToFile("endeavor", bean, resolver.resolveUrlFor(endeavor));

      System.out.println("----> Endeavor " + endeavor.getTitle());
    }

    System.out.println("--> Endeavors created!");
  }

  private void createPagesMembers(ChroniclerData data) throws IOException {
    List<Member> members = data.getMembers();
    for (Member member : members) {
      MemberBean bean = new MemberBean();
      bean.setMember(member);
      mergeTemplateWithBeanDataToFile("object", bean, resolver.resolveUrlFor(member));

      System.out.println("----> Member " + member.getName());
    }
    System.out.println("--> Members created!");
  }

  private void createPagesCreatures(ChroniclerData data) throws IOException {
    List<Creature> creatures = data.getCreatures();
    for (Creature creature : creatures) {
      CreatureBean bean = new CreatureBean();
      bean.setCreature(creature);
      mergeTemplateWithBeanDataToFile("object", bean, resolver.resolveUrlFor(creature));

      System.out.println("----> Creature " + creature.getName());
    }
    System.out.println("--> Creatures created!");
  }

  private void createPagesStakeholders(ChroniclerData data) throws IOException {
    List<Stakeholder> stakeholders = data.getStakeholders();
    for (Stakeholder stakeholder : stakeholders) {
      StakeholderBean bean = new StakeholderBean();
      bean.setStakeholder(stakeholder);
      mergeTemplateWithBeanDataToFile("object", bean, resolver.resolveUrlFor(stakeholder));
      
      System.out.println("----> Stakeholder " + stakeholder.getName());
    }
    System.out.println("--> Stakeholders created!");
  }

  private void createPagesLocations(ChroniclerData data) throws IOException {
    List<Location> locations = data.getLocations();
    for (Location location : locations) {
      LocationBean bean = new LocationBean();
      bean.setLocation(location);
      mergeTemplateWithBeanDataToFile("object", bean, resolver.resolveUrlFor(location));
      
      System.out.println("----> Location " + location.getName());
    }
    System.out.println("--> Locations created!");
  }

  private void generateMap(List<Endeavor> endeavors, String map) throws IOException {
    Collections.reverse(endeavors);
    BufferedImage img = ImageIO.read(new File(map));
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
    ImageIO.write(img, "jpg", new File(destination + IMG_PATH + "/map.jpg"));

    System.out.println("--> MAP created!");  
  }

  private ChroniclerData loadDataFromXml(String xml) throws JAXBException, IOException {
	    System.setProperty("javax.xml.accessExternalDTD", "all");	  
	  
	    File file = new File(xml);
	    JAXBContext jaxbContext = JAXBContext.newInstance(ChroniclerData.class);

	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    ChroniclerData data = (ChroniclerData) jaxbUnmarshaller.unmarshal(new StreamSource(file));
	    
	    List<Endeavor> endeavors = data.getEndeavors();
	    for (Endeavor endeavor : endeavors) {
		    List<Event> events = endeavor.getEvents();
	    	for (Event event : events) {
	    		event.setEndeavor(endeavor);
		    	List<Member> members = event.getMembers();
		    	for (Member member : members) {
		    		member.add(event);
		    	}
		    	List<Stakeholder> stakeholders = event.getStakeholders();
		    	for (Stakeholder stakeholder : stakeholders) {
		    		stakeholder.add(event);
		    	}
		    	List<Creature> kreaturen = event.getCreatures();
		    	for (Creature creature : kreaturen) {
		    		creature.add(event);
		    	}
		    	Location location = event.getLocation();
		    		if (location != null) {
		    			location.add(event);
		    		}
		    }
	    }

	    return data;
	  }

  public void initialize(String chroniclerSources, String projectSources, String destination, ChroniclerData data, boolean copyImages, boolean copyMapTiles) throws IOException, JAXBException {
    System.out.println("********** ChroniclerGenerator - Initialize! **********");
    this.chroniclerSources = chroniclerSources;
    this.projectSources = projectSources;
    this.destination = destination;

    resolver = new StaticUrlResolver(projectSources + IMG_PATH);

    prepareDestination(copyImages, copyMapTiles);

    Velocity.init("src/main/resources/velocity.properties");

    baseCtx = new VelocityContext();
    baseCtx.put("resolver", resolver);
    baseCtx.put("formatter", new DateFormatter());
    
    List<Endeavor> navAdventures = data.getEndeavors();
    List<Member> navHelden = data.getMembers();
    baseCtx.put("navigation", new NavigationBean(navAdventures, navHelden));

    EventCartridge ec = new EventCartridge();
    ec.addEventHandler(new ChroniclerReferenceInsertionEventHandler(new XmlInternalLinksProvider(data), resolver));

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
    try (FileWriter fw = new FileWriter(new File(destination + HTML_PATH + "/" + destinationFileName))) {
      tpl.merge(ctx, fw);
      fw.flush();
    }
  }

  private void prepareDestination(boolean copyImages, boolean copyTiles) throws IOException {
    copyDir(new File(chroniclerSources + CSS_PATH), new File(destination + CSS_PATH));
    copyDir(new File(chroniclerSources + JS_PATH), new File(destination + JS_PATH));
    FileUtils.copyFile(new File(chroniclerSources + "/index.html"), new File(destination + "/index.html"));
    FileUtils.copyFile(new File(chroniclerSources + "/notfound.html"), new File(destination + "/notfound.html"));

	if (copyImages) {
		  copyDir(new File(projectSources + IMG_PATH), new File(destination + IMG_PATH));
	}
	if (copyTiles) {
		  copyDir(new File(projectSources + TILES_PATH), new File(destination + TILES_PATH));
	}

    copyDir(new File(chroniclerSources + HTML_PATH), new File(destination + HTML_PATH));
  }

  private void copyDir(File from, File to) throws IOException {
    if (to.exists()) {
      System.out.println("Removing direcory: " + to.getPath());
	  FileUtils.deleteDirectory(to);
	}

	FileUtils.copyDirectory(from, to);
  }

public void run(String chroniclerSources, String projectSources, String destination, boolean generateMap, boolean copyImages, boolean copyTiles) throws IOException, JAXBException {
	ChroniclerData data = loadDataFromXml(projectSources + XML_PATH);
    initialize(chroniclerSources, projectSources, destination, data, copyImages, copyTiles);

    if (generateMap) {
      generateMap(data.getEndeavors(), projectSources + MAP_PATH);
    }
    
    createPageMap(data);
    createPageHome(data);
    createPageChronicles(data);
    createPageIdx(data);
    createPagesEndeavors(data);
    createPagesLocations(data);
    createPagesMembers(data);
    createPagesStakeholders(data);
    createPagesCreatures(data);

    close();
  }
}
