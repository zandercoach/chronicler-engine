# Chronicler

A static site generator for role-playing chronicles: it turns XML-described content — heroes, non-player characters, creatures, places, adventures and sessions — into a static HTML site, using JAXB for binding and Velocity for templating.

See it running: **[chronicler.adaptive-x-ai.org](https://chronicler.adaptive-x-ai.org)**, an invented demo chronicle whose data lives in [`chronicler-data-demo`](https://github.com/zandercoach/chronicler-data-demo).

This repository holds only the **engine**. Every chronicle is a data repository of its own, with its data, its pictures and a `data/chronicler.xml` that configures the instance: title, welcome text, copyright, wiki source and icon, imprint and privacy links, map, and its own calendar.

## Building

JDK 21; the Maven wrapper brings its own Maven.

```
./mvnw verify
```

`verify` builds, runs all tests and checks the quality gates: formatting, coverage, static analysis and architecture rules.

## Running the generator

```
./mvnw dependency:build-classpath -Dmdep.outputFile=target/cp.txt
java -cp "target/classes:$(cat target/cp.txt)" coach.zander.chronicler.cli.gen.xml.XmlStaticSiteGeneratorApp \
  --data=../chronicler-data-demo --out=target/chronicler --images
```

`--data` points at a data repository; the site is written to `--out`.

## Status

A learning project: the engine is being modernised with AI agents doing much of the work, as part of the journey [Adaptive × AI](https://adaptive-x-ai.org).
