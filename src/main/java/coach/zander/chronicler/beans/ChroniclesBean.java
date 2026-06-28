package coach.zander.cfk.beans;

import java.util.List;

import coach.zander.cfk.model.Adventure;

public class ChroniclesBean extends CfkPageBean {
  private List<Adventure> adventures;

  public List<Adventure> getAdventures() {
    return adventures;
  }

  public void setAdventures(List<Adventure> adventures) {
    this.adventures = adventures;
  }
}
