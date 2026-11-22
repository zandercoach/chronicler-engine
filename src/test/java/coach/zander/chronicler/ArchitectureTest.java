package coach.zander.chronicler;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

/**
 * The engine's boundaries. All of them held without exception when they were introduced, so they
 * are strict. A future rule that the code does not keep yet can be wrapped in
 * FreezingArchRule.freeze: its existing violations become the baseline (a ratchet) and only new
 * ones fail.
 */
@AnalyzeClasses(
    packages = {"coach.zander.chronicler", "be.devijver"},
    importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {

  /** The wiki parser is third-party code and must stay independent of the engine. */
  @ArchTest
  static final ArchRule wikiParserKnowsNothingOfTheEngine =
      noClasses()
          .that()
          .resideInAPackage("be.devijver..")
          .should()
          .dependOnClassesThat()
          .resideInAPackage("coach.zander..");

  /** Only the links package talks to the wiki parser, so it can be replaced in one place. */
  @ArchTest
  static final ArchRule onlyLinksUseTheWikiParser =
      noClasses()
          .that()
          .resideInAPackage("coach.zander.chronicler..")
          .and()
          .resideOutsideOfPackage("coach.zander.chronicler.links..")
          .should()
          .dependOnClassesThat()
          .resideInAPackage("be.devijver..");

  /** The model is plain data: no generator, no page beans, no templating. */
  @ArchTest
  static final ArchRule modelStaysPlain =
      noClasses()
          .that()
          .resideInAPackage("coach.zander.chronicler.model..")
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage(
              "coach.zander.chronicler.cli..",
              "coach.zander.chronicler.beans..",
              "org.apache.velocity..");

  /** Packages depend on each other in one direction only. */
  @ArchTest
  static final ArchRule noPackageCycles =
      slices().matching("coach.zander.chronicler.(*)..").should().beFreeOfCycles();
}
