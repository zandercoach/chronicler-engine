package coach.zander.chronicler.beans;

import java.util.List;

import coach.zander.chronicler.model.Endeavor;

public class ChroniclesBean extends ChroniclerPageBean {
  private List<Endeavor> endeavors;

  public List<Endeavor> getEndeavors() {
    return endeavors;
  }

  public void setEndeavors(List<Endeavor> endeavors) {
    this.endeavors = endeavors;
  }
}
