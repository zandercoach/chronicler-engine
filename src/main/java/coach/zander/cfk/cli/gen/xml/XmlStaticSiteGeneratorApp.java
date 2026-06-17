package coach.zander.cfk.cli.gen.xml;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class XmlStaticSiteGeneratorApp {
  public static void main(String[] args) throws IOException, JAXBException {
    String source = "src/main/resources/static";
    String destination = "target/cfk";
    String dataDir = "../cfk-data";
    String xml = dataDir + "/data/data.xml";
    String map = dataDir + "/data/map.jpg";
    String imgSource = dataDir + "/img";

    boolean generateMap = false;
    boolean copyImages = true;

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.register(XmlStaticSiteGenerator.class);
    context.refresh();

    XmlStaticSiteGenerator generator = context.getBean(XmlStaticSiteGenerator.class);

    generator.run(source, imgSource, destination, xml, map, generateMap, copyImages);
    
    context.close();
  }
}
