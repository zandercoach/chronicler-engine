package coach.zander.cfk.beans;

import coach.zander.cfk.model.Adventure;

public class HomeBean extends CfkPageBean {
  private Adventure adventure;

  public Adventure getAdventure() {
    return adventure;
  }

  public void setAdventure(Adventure adventure) {
    this.adventure = adventure;
    this.setPageTitle("Startseite");
  }
}
