# CLAUDE.md

The **Chronicler engine**: a static site generator in Java that turns
XML-described campaign content into an HTML site (JAXB for binding, Velocity for
templating). Public repo under `zandercoach/chronicler-engine`, MIT-licensed.

This repo holds the engine only. The content — XML data and artwork — lives in
the private sibling repo `chronicler-data-cfk` and stays out of here, because
much of it references the copyrighted DSA/Aventuria setting.

The engine is the vehicle of the "Adaptive × AI" learning journey. Decisions and
milestones made here are recorded in `projects/adaptive-x-ai/research/`, not in
this repo; whoever starts work here reads the latest entry there first.

## Build and run

JDK 21. There is **no local Maven installation** on this machine — use the
wrapper, which fetches its own Maven 3.9.9 into `~/.m2/wrapper/dists/`:

    ./mvnw clean test          27 tests, currently green
    ./mvnw clean package

Running the generator:

    ./mvnw org.codehaus.mojo:exec-maven-plugin:3.1.0:java \
      -Dexec.mainClass=coach.zander.chronicler.cli.gen.xml.XmlStaticSiteGeneratorApp

Output goes to `target/chronicler`.

## The coupling you have to know about

`XmlStaticSiteGeneratorApp` expects the data repo as a **sibling folder** under
`../chronicler-data-cfk`. The path is hardcoded there, as are the destination and
three switches:

    boolean generateMap = false;
    boolean copyImages  = false;
    boolean copyTiles   = false;

A run therefore produces pages but **no map, no images and no tiles** unless
those are flipped. That is a configuration living in source, not a bug — but it
is the first thing to check when generated output looks empty.

## What is alive and what is legacy

The **XML path is the living one**: `cli/gen/xml/`, `model/xml/`, `tpl/*.vm`,
`resources/static/`. That is what the app wires up and what the tests cover.

The **database path is legacy and untouched**: `cli/db/`, `cli/xml/`
(`Db2XmlExporter`, `Xml2DbLoader`), `cli/util/HibernateUtil`, `dao/` and
`model/hibernate/`, together with the Spring/Hibernate dependencies in the POM.
It still compiles, nothing runs it, nothing tests it. Verify before relying on
any of it.

**`be/devijver/wikipedia/` is third-party code** — a wikitext-to-HTML parser
carried in the source tree, 52 of the 110 Java files. Do not rename it, do not
tidy it, do not pull it into the `coach.zander.chronicler` packages: the package
name is its provenance.

## The tests

The tests pin down **observed behaviour, not a specification.** A red test means behaviour changed — first decide whether the change was wanted, then adjust the test, never the other way round.

All of them run against a small, invented fixture under `src/test/resources/fixture/` instead of the private data repo, so the public repo tests on its own. The fixture is copied into a temp directory on purpose: that is what exercises XML entity resolution relative to the data file, and copying it in place would silently defeat the test. JAXB skips unknown elements without a word, so fixture XML must use the element names the model reads.

- `XmlStaticSiteGeneratorCharacterizationTest` — files and pages exist.
- `GeneratedPagesApprovalTest` — **golden master** (ApprovalTests): every generated page, and the list of pages, against its approved version in `src/test/resources/.../approved/`. It says *that* something changed. On a difference a `*.received.*` file appears next to the approved one (git-ignored); if the change is wanted, rename it to `*.approved.*` and commit it — the diff in the pull request is the review.
- `GeneratedSiteInvariantsTest` — rules every page keeps (jsoup, AssertJ): local links resolve, no Velocity reference printed as text, entity pages have heading, navigation and description, every entity page is linked from the index, every event appears on the pages of everyone involved. It says *that* something is broken, and needs no approved files. `KNOWN_BROKEN_LINKS` pins the hardcoded heroes of `home.vm`; it fails as soon as one of them works, so the entry goes with the fix.

`FixtureSite` generates with the engine's real static assets (`src/main/resources/static`). Its empty `html/` folder is kept by a `.gitkeep`; without it a fresh clone cannot run the generator.

CI (`.github/workflows/ci.yml`) runs `./mvnw verify` on every push to `main` and every pull request. `main` is protected: the `Build and test` check and a review by the code owner are required.

## Naming

The rename from `coach.zander.cfk` to `chronicler` is **done** — packages,
classes, templates and fixtures. The only `cfk` left is the path to the data
repo, and that is intended: the data repo stays campaign-specific. The POM's
`<name>` is still the campaign title ("Chroniken des Fliegenden Kessels"); the
`artifactId` carries the new name.

Eclipse metadata (`.classpath`, `.project`, `.settings/`) is versioned along with
the rest. Only `/target/` is ignored.

## Rules

- **Public repo:** commit only when explicitly asked, and push only when
  explicitly asked.
- Line endings are frozen by `.gitattributes` (LF everywhere, CRLF only for
  `.bat`/`.cmd`/`.reg`). Do not change it — see the root `CLAUDE.md` for why.
