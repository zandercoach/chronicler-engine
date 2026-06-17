package coach.zander.cfk.cli.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import coach.zander.cfk.cli.db.DbAccess;
import coach.zander.cfk.model.Abenteuer;
import coach.zander.cfk.model.CFKData;
import coach.zander.cfk.model.Held;
import coach.zander.cfk.model.Kreatur;
import coach.zander.cfk.model.Nsc;
import coach.zander.cfk.model.Ort;
import coach.zander.cfk.model.Session;

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

  private CFKData loadDataFromXml() throws JAXBException, IOException {
    File file = new File(source + "/" + DATA_XML);
    JAXBContext jaxbContext = JAXBContext.newInstance(CFKData.class);
    System.out.println("JAXB Context: " + jaxbContext.getClass());

    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    CFKData data = (CFKData) jaxbUnmarshaller.unmarshal(file);

    return data;
  }

  private void run() throws JAXBException, IOException {
    System.out.println("********** XmlDbLoader - Run! **********");
    CFKData data = loadDataFromXml();
    storeDataInDB(data);
    System.out.println(data);
  }

  private void storeDataInDB(CFKData data) {
    for (Session s : data.getSessions()) {
      db.save(s);
    }
    for (Ort o : data.getOrte()) {
      db.save(o);
    }
    for (Held h : data.getHelden()) {
      db.save(h);
    }
    for (Nsc n : data.getNscs()) {
      db.save(n);
    }
    for (Kreatur k : data.getKreaturen()) {
      db.save(k);
    }
    for (Abenteuer a : data.getAbenteuers()) {
      db.save(a);
    }
  }
}
