# CLAUDE.md

The **Chronicler engine**: a static site generator in Java that turns an XML-described chronicle into an HTML site (JAXB for binding, Velocity for templating). Public, MIT-licensed, `zandercoach/chronicler-engine`.

The engine holds no content. Each **instance** is a data repo of its own: `chronicler-data-cfk` (private campaign, DSA material, published to dsa.zander.coach) and `chronicler-data-demo` (public, invented, published to chronicler.adaptive-x-ai.org). The engine is the vehicle of the "Adaptive × AI" learning journey; decisions and milestones go to `projects/adaptive-x-ai/research/`, and whoever starts work here reads the latest entry there first.

## Build and run

JDK 21, no local Maven — use the wrapper:

    ./mvnw verify                 build, all tests, all quality gates (what CI runs)
    ./mvnw spotless:apply         fix formatting

The generator, from the engine folder (paths relative to it):

    java -cp "target/classes;<classpath>" coach.zander.chronicler.cli.gen.xml.XmlStaticSiteGeneratorApp \
      --data=../chronicler-data-demo --out=target/chronicler [--images] [--tiles] [--map]

Without arguments it reads `../chronicler-data-cfk` and writes `target/chronicler`. Get the classpath with `./mvnw dependency:build-classpath -Dmdep.outputFile=target/cp.txt`.

## Instance configuration

Everything that differs between instances lives in the data repo's `data/chronicler.xml`, read by `model/ChroniclerConfig`: `title`, `welcome`, `copyright`, `wiki` (`name`, `url`, optional `icon` in the instance's `img/`), `legal` (`imprint`, `privacy`), `map` (`enabled="true"` loads Google Maps, otherwise its text is shown — nothing from Google by default), `calendar` (`era`, `before`, `month` with `abbr`, `name`, `days`). Missing `chronicler.xml` or `calendar` stops the generator with a message — never add a silent default that brings instance-specific values back into the engine.

## Where things are

- `cli/gen/xml/` — the generator: reads `chronicler.xml`, then `data.xml` (the calendar goes to JAXB via `setAdapter`), renders `tpl/*.vm` into pages.
- `model/` — plain data: JAXB-annotated entities, `ChronicleCalendar` and `ChronicleDate`. No generator, no beans, no Velocity (enforced).
- `beans/` — page beans for the templates; `SiteBean` carries instance-wide values.
- `links/` — internal and wiki links; the only package that uses the wiki parser (enforced).
- `src/main/resources/tpl/` templates, `static/` CSS, JS and the engine's own icon.
- **`be/devijver/wikipedia/` is third-party code** (wikitext parser). Do not rename, tidy, format or analyse it; its form is its provenance. Every gate excludes it.

## Tests

The tests pin down **observed behaviour, not a specification.** A red test means behaviour changed — decide whether that was wanted, then adjust the test, never the other way round.

- `GeneratedPagesApprovalTest` — **golden master** over the fixture (`src/test/resources/fixture/`, invented, German): every page and the page list against `src/test/resources/.../approved/`. On a difference a `*.received.*` file appears; if the change is wanted, rename it to `*.approved.*` — the diff in the pull request is the review.
- `GeneratedSiteInvariantsTest` — rules every page keeps: local links resolve, no Velocity reference printed, entity pages complete and in the index, every event on the pages of those involved. `-Dchronicler.data=<data repo>` runs it against a real instance; the campaign repo's CI does that on every push.
- `XmlStaticSiteGeneratorCharacterizationTest`, `ChronicleDateTest`, `ArchitectureTest`.

JAXB skips unknown XML elements silently, so fixture XML must use the element names the model reads.

## The quality frame

`./mvnw verify` runs these gates; CI runs `verify` on every pull request, and `main` requires it.

| Gate | Checks | Baseline |
|---|---|---|
| Spotless | google-java-format | — |
| JaCoCo | line and branch coverage | `coverage.line.minimum`, `coverage.branch.minimum` in `pom.xml` |
| SpotBugs | bugs | `config/spotbugs-baseline.xml` |
| PMD | complexity, dead code, error-prone patterns (`config/pmd-ruleset.xml`) | `config/pmd-baseline.properties` |
| ArchUnit | package boundaries (`ArchitectureTest`) | none — all rules hold |

They are **ratchets**: today's state is the baseline and only getting worse fails. So:

- **A baseline only shrinks.** Fix a baselined finding, then delete its entry. Never add an entry to make a build green — fix the code, or ask Christian.
- **A minimum only rises.** When coverage goes up, raise the minimum in the same pull request (two decimals, rounded down).
- SpotBugs `EI_EXPOSE_REP`/`EI_EXPOSE_REP2` are excluded by policy: models and beans hand out their lists on purpose.
- A new architecture rule the code does not keep yet goes in as `FreezingArchRule.freeze(...)`; its existing violations become the baseline.

## Definition of Done

A change is done when:

1. `./mvnw verify` is green — tests and every gate.
2. The golden master diff, if any, is reviewed and approved on purpose.
3. Where data handling changed: invariants green against the campaign (`-Dchronicler.data=../chronicler-data-cfk`), and campaign and demo regenerated and compared with the output before.
4. Coverage minimum raised if coverage rose; baselines shortened if findings were fixed.
5. This file describes the engine as it now is.

## Working here

- One pull request per change, **straight onto `main`** — no stacked pull requests. Head branches are deleted on merge.
- Commit and push only when Christian asks; that includes local commits.
- **Claude works as Claude Craft** (`claude-craft-agent`), here and in the instance repos: commits, pushes and pull requests under that account, never under Christian's login, so he approves as code owner instead of bypassing. Christian approves and merges; Claude does neither. Per command only, never `gh auth switch`: commit with `git -c user.name="Claude Craft" -c user.email=360632363+claude-craft-agent@users.noreply.github.com`, push with a one-off `-c credential.helper=` that reads the token file, call `gh` with `GH_TOKEN` from it. Token files live in `~` and never appear in output: `.claude-craft-token-zandercoach` for the org repos, `.claude-craft-token` for `chronicler-data-cfk`.
- A formatting-only commit is merged with a merge commit and listed in `.git-blame-ignore-revs` (`git config blame.ignoreRevsFile .git-blame-ignore-revs`).
- Changes that need data on both sides (engine and instances) are merged in the order the pull requests state: an older engine ignores unknown configuration elements, but cannot read new data formats.
