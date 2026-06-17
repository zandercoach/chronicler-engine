package coach.zander.cfk.beans;

import java.util.List;

import coach.zander.cfk.model.Abenteuer;

public class ChroniclesBean extends CfkPageBean {
  private List<Abenteuer> abenteuers;

  public List<Abenteuer> getAbenteuers() {
    return abenteuers;
  }

  public void setAbenteuers(List<Abenteuer> abenteuers) {
    this.abenteuers = abenteuers;
  }
}
