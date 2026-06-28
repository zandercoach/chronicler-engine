package coach.zander.chronicler.cli.gen.xml;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import coach.zander.chronicler.cli.gen.xml.XmlStaticSiteGenerator;
import coach.zander.chronicler.cli.gen.xml.XmlStaticSiteGeneratorApp;

/**
 * Characterization tests for the live XML generation pipeline (the one actually wired up in
 * {@link XmlStaticSiteGeneratorApp}). These pin down current, observed behavior - including the two
 * Phase 0 bug fixes - using a small self-contained fixture instead of the private content repo, so the
 * test runs on the public engine repo alone.
 */
class XmlStaticSiteGeneratorCharacterizationTest {

  private static final String FIXTURE_ROOT = "src/test/resources/fixture";

  @TempDir
  Path destinationDir;

  @TempDir
  Path dataDir;

  private String destination;

  @BeforeEach
  void setupDataDirOutsideTheRepo() throws IOException {
    // Copying the xml-data fixture into a temp dir (away from the project's working directory)
    // is what actually exercises the entity-resolution fix: it proves entities resolve relative
    // to the document's own location, not to whatever the process CWD happens to be.
    FileUtils.copyDirectory(new File(FIXTURE_ROOT, "xml-data"), dataDir.resolve("data").toFile());
    FileUtils.copyDirectory(new File(FIXTURE_ROOT, "img"), dataDir.resolve("img").toFile());
    destination = destinationDir.toString();
  }

  @Test
  void copiesStaticAssetsIntoTheDestination() throws IOException, JAXBException {
    runGenerator();

    assertTrue(new File(destination, "css/style.css").isFile(), "css was not copied into the destination");
    assertTrue(new File(destination, "js/script.js").isFile(), "js was not copied into the destination");
    assertTrue(new File(destination, "index.html").isFile(), "index.html was not copied into the destination");
    assertTrue(new File(destination, "notfound.html").isFile(), "notfound.html was not copied into the destination");
  }

  @Test
  void copiesImagesIntoTheDestinationWhenRequested() throws IOException, JAXBException {
    runGenerator();

    assertTrue(new File(destination, "img/platzhalter.txt").isFile(), "images were not copied into the destination");
  }

  @Test
  void loadsDataXmlWithEntitiesThatLiveNextToTheDocumentRatherThanInTheWorkingDirectory() throws IOException, JAXBException {
    // If this regresses to CWD-relative entity resolution, this assertion - and everything
    // downstream of it - simply won't find the generated pages, because the xml-data fixture
    // lives in a JUnit temp dir, never in the project's working directory.
    runGenerator();

    assertTrue(new File(destination, "html/home.html").isFile(), "data.xml's entities did not resolve, so the home page was never generated");
  }

  @Test
  void generatesOnePageForEachEntityType() throws IOException, JAXBException {
    runGenerator();

    assertTrue(new File(destination, "html/home.html").isFile());
    assertTrue(new File(destination, "html/idx.html").isFile());
    assertTrue(new File(destination, "html/chronicles.html").isFile());
    assertTrue(new File(destination, "html/map.html").isFile());
    assertTrue(new File(destination, "html/endeavor_Test-Endeavor.html").isFile());
    assertTrue(new File(destination, "html/member_Test-Member.html").isFile());
    assertTrue(new File(destination, "html/stakeholder_Test-Stakeholder.html").isFile());
    assertTrue(new File(destination, "html/creature_Test-Creature.html").isFile());
    assertTrue(new File(destination, "html/location_Test-Location.html").isFile());
  }

  private void runGenerator() throws IOException, JAXBException {
    XmlStaticSiteGenerator generator = new XmlStaticSiteGenerator();
    generator.run(FIXTURE_ROOT + "/static", dataDir.toString(), destination, false, true, false);
  }
}
