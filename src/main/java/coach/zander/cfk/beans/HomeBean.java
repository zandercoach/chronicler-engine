package coach.zander.cfk.beans;

import coach.zander.cfk.model.Abenteuer;

public class HomeBean extends CfkPageBean {
  private Abenteuer abenteuer;

  public Abenteuer getAbenteuer() {
    return abenteuer;
  }

  public void setAbenteuer(Abenteuer abenteuer) {
    this.abenteuer = abenteuer;
    this.setPageTitle("Startseite");
  }
}
