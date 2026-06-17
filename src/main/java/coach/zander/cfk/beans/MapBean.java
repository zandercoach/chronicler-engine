package coach.zander.cfk.beans;

import java.util.List;

import coach.zander.cfk.model.Abenteuer;

public class MapBean extends CfkPageBean {
  private String map;

  private List<Abenteuer> abenteuers;

  public List<Abenteuer> getAbenteuers() {
    return abenteuers;
  }

  public String getMap() {
    return map;
  }

  public void setAbenteuers(List<Abenteuer> abenteuers) {
    this.abenteuers = abenteuers;
  }

  public void setMap(String map) {
    this.map = map;
  }
}
