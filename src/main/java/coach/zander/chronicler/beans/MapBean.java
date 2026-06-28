package coach.zander.cfk.beans;

import java.util.List;

import coach.zander.cfk.model.Adventure;

public class MapBean extends CfkPageBean {
  private String map;

  private List<Adventure> adventures;

  public List<Adventure> getAdventures() {
    return adventures;
  }

  public String getMap() {
    return map;
  }

  public void setAdventures(List<Adventure> adventures) {
    this.adventures = adventures;
  }

  public void setMap(String map) {
    this.map = map;
  }
}
