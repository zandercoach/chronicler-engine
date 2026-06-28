package coach.zander.chronicler.cli.gen.xml;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class XmlStaticSiteGeneratorApp {
  public static void main(String[] args) throws IOException, JAXBException {
    String chroniclerSources = "src/main/resources/static";
    String destination = "target/chronicler";
    String projectSources = "../chronicler-data-cfk";

    boolean generateMap = false;
    boolean copyImages = false;
    boolean copyTiles = false;

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.register(XmlStaticSiteGenerator.class);
    context.refresh();

    XmlStaticSiteGenerator generator = context.getBean(XmlStaticSiteGenerator.class);

    generator.run(chroniclerSources, projectSources, destination, generateMap, copyImages, copyTiles);
    
    context.close();
  }
}
