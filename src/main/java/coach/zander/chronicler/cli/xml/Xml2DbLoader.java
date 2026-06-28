package coach.zander.chronicler.cli.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import coach.zander.chronicler.cli.db.DbAccess;
import coach.zander.chronicler.model.ChroniclerData;
import coach.zander.chronicler.model.Creature;
import coach.zander.chronicler.model.Endeavor;
import coach.zander.chronicler.model.Location;
import coach.zander.chronicler.model.Member;
import coach.zander.chronicler.model.Session;
import coach.zander.chronicler.model.Stakeholder;

public class Xml2DbLoader {
  private static final String DATA_XML = "data.xml";

  public static void main(String[] args) throws IOException, JAXBException {
    String source = "backup";
    Xml2DbLoader app = new Xml2DbLoader(source);
    app.initialize();
    try {
      app.run();
    } finally {
      app.close();
    }
  }

  private final String source;
  private DbAccess db;

  public Xml2DbLoader(String source) {
    this.source = source;
  }

  private void close() {
    db.close();
    System.out.println("********** XmlDbLoader - Close! **********");
  }

  private void initialize() throws IOException {
    System.out.println("********** XmlDbLoader - Initialize! **********");

    db = new DbAccess();
    db.open("xml_dbloader.properties");
  }

  private ChroniclerData loadDataFromXml() throws JAXBException, IOException {
    File file = new File(source + "/" + DATA_XML);
    JAXBContext jaxbContext = JAXBContext.newInstance(ChroniclerData.class);
    System.out.println("JAXB Context: " + jaxbContext.getClass());

    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    ChroniclerData data = (ChroniclerData) jaxbUnmarshaller.unmarshal(file);

    return data;
  }

  private void run() throws JAXBException, IOException {
    System.out.println("********** XmlDbLoader - Run! **********");
    ChroniclerData data = loadDataFromXml();
    storeDataInDB(data);
    System.out.println(data);
  }

  private void storeDataInDB(ChroniclerData data) {
    for (Session s : data.getSessions()) {
      db.save(s);
    }
    for (Location o : data.getLocations()) {
      db.save(o);
    }
    for (Member h : data.getMembers()) {
      db.save(h);
    }
    for (Stakeholder n : data.getStakeholders()) {
      db.save(n);
    }
    for (Creature k : data.getCreatures()) {
      db.save(k);
    }
    for (Endeavor a : data.getEndeavors()) {
      db.save(a);
    }
  }
}
