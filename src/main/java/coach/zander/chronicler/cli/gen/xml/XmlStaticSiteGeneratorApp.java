package coach.zander.chronicler.cli.gen.xml;

import jakarta.xml.bind.JAXBException;
import java.io.IOException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Generates the static site. Without arguments it reads the sibling data repo and writes to
 * target/chronicler, as it always did.
 *
 * <pre>
 *   --data=DIR     data repo (default ../chronicler-data-cfk)
 *   --out=DIR      destination (default target/chronicler)
 *   --static=DIR   engine's static assets (default src/main/resources/static)
 *   --images       copy the data repo's images
 *   --tiles        copy the map tiles
 *   --map          draw the endeavors onto the map
 * </pre>
 */
public class XmlStaticSiteGeneratorApp {
  public static void main(String[] args) throws IOException, JAXBException {
    String chroniclerSources = "src/main/resources/static";
    String destination = "target/chronicler";
    String projectSources = "../chronicler-data-cfk";

    boolean generateMap = false;
    boolean copyImages = false;
    boolean copyTiles = false;

    for (String arg : args) {
      if (arg.startsWith("--data=")) {
        projectSources = arg.substring("--data=".length());
      } else if (arg.startsWith("--out=")) {
        destination = arg.substring("--out=".length());
      } else if (arg.startsWith("--static=")) {
        chroniclerSources = arg.substring("--static=".length());
      } else if (arg.equals("--images")) {
        copyImages = true;
      } else if (arg.equals("--tiles")) {
        copyTiles = true;
      } else if (arg.equals("--map")) {
        generateMap = true;
      } else {
        System.err.println("Unknown argument: " + arg);
        System.err.println(
            "Usage: [--data=DIR] [--out=DIR] [--static=DIR] [--images] [--tiles] [--map]");
        System.exit(2);
      }
    }

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.register(XmlStaticSiteGenerator.class);
    context.refresh();

    XmlStaticSiteGenerator generator = context.getBean(XmlStaticSiteGenerator.class);

    generator.run(
        chroniclerSources, projectSources, destination, generateMap, copyImages, copyTiles);

    context.close();
  }
}
