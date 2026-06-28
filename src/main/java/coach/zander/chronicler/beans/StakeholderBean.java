package coach.zander.cfk.beans;

import coach.zander.cfk.model.CFKObject;
import coach.zander.cfk.model.Nsc;

public class NscBean extends CfkObjectBean {
  private Nsc nsc;

  public Nsc getNsc() {
    return nsc;
  }

  @Override
  public CFKObject getObject() {
    return getNsc();
  }

  public void setNsc(Nsc meisterperson) {
    this.nsc = meisterperson;
  }
}
