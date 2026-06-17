package coach.zander.cfk.cli.db;

import java.io.IOException;
import java.util.List;

import coach.zander.cfk.model.Abenteuer;
import coach.zander.cfk.model.Held;
import coach.zander.cfk.model.Nsc;
import coach.zander.cfk.model.Ort;

public class DbLoader {
  public static void main(String[] args) throws IOException {
    DbLoader app = new DbLoader();
    app.initialize();
    app.run();
    app.close();

  }

  private DbAccess db;

  private void close() {
    db.close();
    System.out.println("********** DbLoader - Close! **********");
  }

  private void initialize() throws IOException {
    System.out.println("********** DbLoader - Initialize! **********");
    db = new DbAccess();
    db.open("dbloader.properties");
  }

  private void run() {
    System.out.println("********** DbLoader - Run! **********");
    List<Abenteuer> abenteuers = db.getAbenteuerAll();
    for (Abenteuer abenteuer : abenteuers) {
      System.out.println(abenteuer);
    }
    List<Ort> orte = db.getOrtAll();
    for (Ort ort : orte) {
      System.out.println(ort);
    }
    List<Held> helden = db.getHeldAll();
    for (Held held : helden) {
      System.out.println(held);
    }
    List<Nsc> mps = db.getNscAll();
    for (Nsc mp : mps) {
      System.out.println(mp);
    }
  }
}
