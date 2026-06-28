package coach.zander.chronicler.beans;

import coach.zander.chronicler.model.Endeavor;

public class HomeBean extends ChroniclerPageBean {
  private Endeavor endeavor;

  public Endeavor getEndeavor() {
    return endeavor;
  }

  public void setEndeavor(Endeavor endeavor) {
    this.endeavor = endeavor;
    this.setPageTitle("Startseite");
  }
}
