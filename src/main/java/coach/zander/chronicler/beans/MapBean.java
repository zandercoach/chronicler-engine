package coach.zander.chronicler.beans;

import java.util.List;

import coach.zander.chronicler.model.Endeavor;

public class MapBean extends ChroniclerPageBean {
  private String map;

  private List<Endeavor> endeavors;

  public List<Endeavor> getEndeavors() {
    return endeavors;
  }

  public String getMap() {
    return map;
  }

  public void setEndeavors(List<Endeavor> endeavors) {
    this.endeavors = endeavors;
  }

  public void setMap(String map) {
    this.map = map;
  }
}
