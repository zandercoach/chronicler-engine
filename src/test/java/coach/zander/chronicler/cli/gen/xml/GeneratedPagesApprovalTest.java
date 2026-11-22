package coach.zander.chronicler.cli.gen.xml;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.bind.JAXBException;
import org.approvaltests.Approvals;
import org.approvaltests.integrations.junit5.JupiterApprovals;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.io.TempDir;

/**
 * Golden master: every page generated from the fixture is compared with its approved version under
 * src/test/resources. A difference is not automatically a bug - decide whether the change was
 * wanted, then approve the received file, never the other way round.
 */
class GeneratedPagesApprovalTest {

  @TempDir Path workDir;

  @TestFactory
  Stream<DynamicTest> generatedPage() throws IOException, JAXBException {
    Path html = FixtureSite.generate(workDir).resolve("html");
    Map<String, String> pages = new LinkedHashMap<>();
    try (Stream<Path> files = Files.list(html)) {
      for (Path page :
          files.filter(p -> p.toString().endsWith(".html")).sorted().collect(Collectors.toList())) {
        pages.put(page.getFileName().toString(), Files.readString(page, StandardCharsets.UTF_8));
      }
    }
    assertThat(pages).as("generated pages").isNotEmpty();

    DynamicTest pageList =
        JupiterApprovals.dynamicTest(
            "page list",
            options -> Approvals.verify(String.join("\n", pages.keySet()) + "\n", options));
    Stream<DynamicTest> pageTests =
        pages.entrySet().stream()
            .map(page ->
                JupiterApprovals.dynamicTest(
                    page.getKey().replaceFirst("\\.html$", ""),
                    options ->
                        Approvals.verify(
                            page.getValue(), options.forFile().withExtension(".html"))));
    return Stream.concat(Stream.of(pageList), pageTests);
  }
}
