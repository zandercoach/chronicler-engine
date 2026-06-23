package coach.zander.cfk.cli.xml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import coach.zander.cfk.cli.db.DbAccess;
import coach.zander.cfk.model.Adventure;
import coach.zander.cfk.model.CFKData;

public class Db2XmlExporter {
  private static final String DATA_XML = "data.xml";

  public static void main(String[] args) throws IOException, JAXBException {
    String destination = "backup";
    Db2XmlExporter app = new Db2XmlExporter(destination);
    app.initialize();
    try {
      app.run();
    } finally {
      app.close();
    }
  }

  private final String destination;
  private DbAccess db;

  public Db2XmlExporter(String destination) {
    this.destination = destination;
  }

  private void close() {
    db.close();
    System.out.println("********** XmlExporter - Close! **********");
  }

  private void exportCFKData() throws JAXBException, IOException {
    CFKData data = new CFKData();
    data.setOrte(db.getOrtAll());
    data.setHelden(db.getHeldAll());
    data.setNscs(db.getNscAll());
    data.setKreaturen(db.getKreaturAll());
    List<Adventure> adventures = db.getAdventureAll();
    Collections.sort(adventures);
    data.setAdventures(adventures);
    data.setSessions(db.getSessionAll());
    JAXBContext context = JAXBContext.newInstance(CFKData.class);
    Marshaller m = context.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    m.marshal(data, System.out);

    Writer w = null;
    try {
      w = new FileWriter(destination + "/" + DATA_XML);
      m.marshal(data, w);
    } finally {
      w.close();
    }
  }

  private void initialize() throws IOException {
    System.out.println("********** XmlExporter - Initialize! **********");

    db = new DbAccess();
    db.open();
  }

  private void run() throws JAXBException, IOException {
    System.out.println("********** XmlExporter - Run! **********");
    exportCFKData();
  }
}
