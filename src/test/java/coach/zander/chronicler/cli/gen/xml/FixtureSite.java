package coach.zander.chronicler.cli.gen.xml;

import jakarta.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.commons.io.FileUtils;

/**
 * Generates the site from the test fixture with the engine's real static assets, the way a run on
 * real data does. The data is copied into a temp directory first, so entity resolution relative to
 * the data file stays exercised.
 */
final class FixtureSite {

  static final String FIXTURE_ROOT = "src/test/resources/fixture";
  static final String ENGINE_STATIC = "src/main/resources/static";

  private FixtureSite() {}

  /** Generates into {@code workDir}/site and returns that directory. */
  static Path generate(Path workDir) throws IOException, JAXBException {
    Path data = workDir.resolve("data-repo");
    FileUtils.copyDirectory(new File(FIXTURE_ROOT, "xml-data"), data.resolve("data").toFile());
    FileUtils.copyDirectory(new File(FIXTURE_ROOT, "img"), data.resolve("img").toFile());
    Path site = workDir.resolve("site");
    new XmlStaticSiteGenerator()
        .run(ENGINE_STATIC, data.toString(), site.toString(), false, true, false);
    return site;
  }
}
