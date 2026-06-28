package coach.zander.chronicler.cli.db;

import java.io.IOException;
import java.util.List;

import coach.zander.chronicler.model.Endeavor;
import coach.zander.chronicler.model.Location;
import coach.zander.chronicler.model.Member;
import coach.zander.chronicler.model.Stakeholder;

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
    List<Endeavor> endeavors = db.getEndeavorAll();
    for (Endeavor endeavor : endeavors) {
      System.out.println(endeavor);
    }
    List<Location> locations = db.getLocationAll();
    for (Location location : locations) {
      System.out.println(location);
    }
    List<Member> members = db.getMemberAll();
    for (Member member : members) {
      System.out.println(member);
    }
    List<Stakeholder> stakeholders = db.getStakeholderAll();
    for (Stakeholder stakeholder : stakeholders) {
      System.out.println(stakeholder);
    }
  }
}
