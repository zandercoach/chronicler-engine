package coach.zander.cfk.cli.gen.xml;

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

import coach.zander.cfk.beans.AbenteuerBean;
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
import coach.zander.cfk.links.CfkReferenceInsertionEventHandler;
import coach.zander.cfk.model.Abenteuer;
import coach.zander.cfk.model.CFKData;
import coach.zander.cfk.model.Ereignis;
import coach.zander.cfk.model.Held;
import coach.zander.cfk.model.Kreatur;
import coach.zander.cfk.model.Nsc;
import coach.zander.cfk.model.Ort;
import coach.zander.cfk.model.Wegpunkt;
import coach.zander.cfk.util.DateFormatter;
import coach.zander.cfk.util.UrlResolver;

public class XmlStaticSiteGenerator {

  private UrlResolver resolver;
  private String source;
  private String imgSource;
  private String destination;
  private VelocityContext baseCtx;
  
  private String htmlPath = "/html";
  private String imgPath = "/img";

  public void close() {
    System.out.println("********** CFKGenerator - Close! **********");
  }

  private void createPageChronicles(CFKData data) throws IOException {

    ChroniclesBean bean = new ChroniclesBean();
    List<Abenteuer> abenteuers = data.getAbenteuers();
    // IMPL: Order Abenteuers by Date!
    bean.setPageTitle("Die Chroniken");
    bean.setAbenteuers(abenteuers);
    mergeTemplateWithBeanDataToFile("chronicles", bean);

    System.out.println("--> Page 'chronicles' created!");
  }

  private void createPageHome(CFKData data) throws ResourceNotFoundException, ParseErrorException, MethodInvocationException,
      IOException {

    HomeBean bean = new HomeBean();
    List<Abenteuer> abenteuers = data.getAbenteuers();
    
    // Latest Abenteuer for index.html
    Abenteuer abenteuer = abenteuers.get(0);
    bean.setAbenteuer(abenteuer);
    bean.setPageTitle("Startseite");
    mergeTemplateWithBeanDataToFile("home", bean);
    
    System.out.println("--> Page 'home' created!");
  }

  private void createPageIdx(CFKData data) throws IOException {

    IdxBean bean = new IdxBean();
    bean.setPageTitle("Index");
    bean.setAbenteuers(data.getAbenteuers());
    bean.setHelden(data.getHelden());
    bean.setOrte(data.getOrte());
    bean.setNscs(data.getNscs());
    bean.setKreaturen(data.getKreaturen());
    mergeTemplateWithBeanDataToFile("idx", bean);

    System.out.println("--> Page 'idx' created!");
  }

  private void createPageMap(CFKData data) throws IOException {
    MapBean bean = new MapBean();
    bean.setPageTitle("Karte");
    bean.setMap("map");

    List<Abenteuer> abenteuers = data.getAbenteuers();
    
    // IMPL: order abenteuers by date!
    bean.setAbenteuers(abenteuers);

    mergeTemplateWithBeanDataToFile("map", bean);

    System.out.println("--> Page 'map' created!");
  }

  private void createPagesAbenteuer(CFKData data) throws IOException {
    List<Abenteuer> abenteuers = data.getAbenteuers();
    for (Abenteuer abenteuer : abenteuers) {
      AbenteuerBean bean = new AbenteuerBean();
      bean.setAbenteuer(abenteuer);
      mergeTemplateWithBeanDataToFile("abenteuer", bean, resolver.resolveUrlFor(abenteuer));

      System.out.println("----> Abenteuer " + abenteuer.getTitle());
    }

    System.out.println("--> Abenteuers created!");
  }

  private void createPagesHelden(CFKData data) throws IOException {
    List<Held> helden = data.getHelden();
    for (Held held : helden) {
      HeldBean bean = new HeldBean();
      bean.setHeld(held);
      mergeTemplateWithBeanDataToFile("object", bean, resolver.resolveUrlFor(held));

      System.out.println("----> Held " + held.getName());
    }
    System.out.println("--> Helden created!");
  }

  private void createPagesKreaturen(CFKData data) throws IOException {
    List<Kreatur> kreaturen = data.getKreaturen();
    for (Kreatur kreatur : kreaturen) {
      KreaturBean bean = new KreaturBean();
      bean.setKreatur(kreatur);
      mergeTemplateWithBeanDataToFile("object", bean, resolver.resolveUrlFor(kreatur));

      System.out.println("----> Kreatur " + kreatur.getName());
    }
    System.out.println("--> Kreaturen created!");
  }

  private void createPagesNscs(CFKData data) throws IOException {
    List<Nsc> nscs = data.getNscs();
    for (Nsc nsc : nscs) {
      NscBean bean = new NscBean();
      bean.setNsc(nsc);
      mergeTemplateWithBeanDataToFile("object", bean, resolver.resolveUrlFor(nsc));
      
      System.out.println("----> NSC " + nsc.getName());
    }
    System.out.println("--> NSCs created!");
  }

  private void createPagesOrte(CFKData data) throws IOException {
    List<Ort> orte = data.getOrte();
    for (Ort ort : orte) {
      OrtBean bean = new OrtBean();
      bean.setOrt(ort);
      mergeTemplateWithBeanDataToFile("object", bean, resolver.resolveUrlFor(ort));
      
      System.out.println("----> Ort " + ort.getName());
    }
    System.out.println("--> Orte created!");
  }

  private void generateMap(List<Abenteuer> abenteuers, String map) throws IOException {
    Collections.reverse(abenteuers);
    BufferedImage img = ImageIO.read(new File(map));
    Graphics2D graphics = img.createGraphics();
    graphics.setStroke(new BasicStroke(50.0f));
    Wegpunkt previous = null;
    for (Abenteuer abenteuer : abenteuers) {
      for (Wegpunkt current : abenteuer.getWegpunkte()) {
        if (previous != null) {
          graphics.drawLine(previous.getX(), previous.getY(), current.getX(), current.getY());
        }
        previous = current;
      }
    }
    ImageIO.write(img, "jpg", new File(destination + imgPath + "/map.jpg"));

    System.out.println("--> MAP created!");  
  }

  private CFKData loadDataFromXml(String xml) throws JAXBException, IOException {
	    System.setProperty("javax.xml.accessExternalDTD", "all");	  
	  
	    File file = new File(xml);
	    JAXBContext jaxbContext = JAXBContext.newInstance(CFKData.class);

	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    CFKData data = (CFKData) jaxbUnmarshaller.unmarshal(new StreamSource(file));
	    
	    List<Abenteuer> abenteuers = data.getAbenteuers();
	    for (Abenteuer abenteuer : abenteuers) {
		    List<Ereignis> ereignisse = abenteuer.getEreignisse();
	    	for (Ereignis ereignis : ereignisse) {
	    		ereignis.setAbenteuer(abenteuer);
		    	List<Held> helden = ereignis.getHelden();
		    	for (Held held : helden) {
		    		held.add(ereignis);
		    	}
		    	List<Nsc> nscs = ereignis.getNscs();
		    	for (Nsc nsc : nscs) {
		    		nsc.add(ereignis);
		    	}
		    	List<Kreatur> kreaturen = ereignis.getKreaturen();
		    	for (Kreatur kreatur : kreaturen) {
		    		kreatur.add(ereignis);
		    	}
		    	Ort ort = ereignis.getOrt();
		    		if (ort != null) {
		    			ort.add(ereignis);
		    		}
		    }
	    }

	    return data;
	  }

  public void initialize(String source, String imgSource, String destination, CFKData data, boolean copyImages) throws IOException, JAXBException {
    System.out.println("********** CFKGenerator - Initialize! **********");
    this.source = source;
    this.imgSource = imgSource;
    this.destination = destination;

    resolver = new StaticUrlResolver(imgSource);

    prepareDestination(copyImages);

    Velocity.init("src/main/resources/velocity.properties");

    baseCtx = new VelocityContext();
    baseCtx.put("resolver", resolver);
    baseCtx.put("formatter", new DateFormatter());
    
    List<Abenteuer> navAbenteuers = data.getAbenteuers();
    List<Held> navHelden = data.getHelden();
    baseCtx.put("navigation", new NavigationBean(navAbenteuers, navHelden));

    EventCartridge ec = new EventCartridge();
    ec.addEventHandler(new CfkReferenceInsertionEventHandler(new XmlInternalLinksProvider(data), resolver));

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
    try (FileWriter fw = new FileWriter(new File(destination + htmlPath + "/" + destinationFileName))) {
      tpl.merge(ctx, fw);
      fw.flush();
    }
  }

  private void prepareDestination(boolean copyImages) throws IOException {
    copyDir(new File(source + "/css"), new File(destination + "/css"));
    copyDir(new File(source + "/js"), new File(destination + "/js"));
    FileUtils.copyFile(new File(source + "/index.html"), new File(destination + "/index.html"));
    FileUtils.copyFile(new File(source + "/notfound.html"), new File(destination + "/notfound.html"));

	if (copyImages) {
	  copyDir(new File(imgSource), new File(destination + imgPath));
	}

    copyDir(new File(source + htmlPath), new File(destination + htmlPath));
  }

  private void copyDir(File from, File to) throws IOException {
    if (to.exists()) {
      System.out.println("Removing direcory: " + to.getPath());
	  FileUtils.deleteDirectory(to);
	}

	FileUtils.copyDirectory(from, to);
  }

public void run(String source, String imgSource, String destination, String xml, String map, boolean generateMap, boolean copyImages) throws IOException, JAXBException {
	CFKData data = loadDataFromXml(xml);
    initialize(source, imgSource, destination, data, copyImages);

    if (generateMap) {
      generateMap(data.getAbenteuers(), map);
    }
    
    createPageMap(data);
    createPageHome(data);
    createPageChronicles(data);
    createPageIdx(data);
    createPagesAbenteuer(data);
    createPagesOrte(data);
    createPagesHelden(data);
    createPagesNscs(data);
    createPagesKreaturen(data);

    close();
  }
}
