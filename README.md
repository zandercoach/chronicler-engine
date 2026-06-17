# Chroniken des Fliegenden Kessels — Engine

Generator that turns XML-described campaign content (heroes, monsters, NPCs,
locations, adventures, sessions) into a static HTML website, using JAXB for
XML binding and Velocity for templating.

This repository contains only the **engine**: Java source, page templates,
and generic site assets (CSS/JS). The actual campaign content (XML data and
artwork) lives in a separate, private repository and is not included here —
much of that content references copyrighted third-party material (the DSA/
Aventuria setting) and isn't redistributable.

## Building

Requires JDK 21 and Maven.

```
mvn clean compile
```

## Running the generator

```
mvn org.codehaus.mojo:exec-maven-plugin:3.1.0:java \
  -Dexec.mainClass=coach.zander.cfk.cli.gen.xml.XmlStaticSiteGeneratorApp
```

This expects a sibling directory `../coach.zander.cfk.data` containing the content data
(see `XmlStaticSiteGeneratorApp` for the exact expected layout: `data/`,
`img/`). Output is written to `target/cfk`.

## Status

Actively being modernized (Java 21, current dependencies) and brought under
git/CI as a learning project. See open issues for in-progress work.
