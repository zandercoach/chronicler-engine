package coach.zander.chronicler.cli.gen.db;

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DbStaticSiteGeneratorApp {
  public static void main(String[] args) throws IOException {
    String source = "src/main/resources";
    String destination = "/work/greenpike/chronicler";

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.register(DbStaticSiteGenerator.class, DbConfig.class);
    context.refresh();

    DbStaticSiteGenerator generator = context.getBean(DbStaticSiteGenerator.class);

    generator.run(source, destination);
    
    context.close();
  }
}
