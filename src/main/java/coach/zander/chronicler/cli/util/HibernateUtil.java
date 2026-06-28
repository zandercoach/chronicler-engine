package coach.zander.chronicler.cli.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public final class HibernateUtil {

  public static SessionFactory buildSessionFactory() throws IOException {
    return buildSessionFactory(null);
  }

  public static SessionFactory buildSessionFactory(String propertiesFile) throws IOException {
    Configuration configuration = new Configuration();
    configuration.configure();

    if (propertiesFile != null) {
      InputStream propertiesStream = null;
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      if (classLoader != null) {
        propertiesStream = classLoader.getResourceAsStream(propertiesFile);
      }
      Properties loaderProperties = new Properties();
      loaderProperties.load(propertiesStream);
      propertiesStream.close();
      configuration.addProperties(loaderProperties);
    }

    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
        .getBootstrapServiceRegistry();
    return configuration.buildSessionFactory(serviceRegistry);
  }
}
