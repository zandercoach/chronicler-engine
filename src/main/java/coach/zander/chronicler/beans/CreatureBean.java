package coach.zander.cfk.beans;

import coach.zander.cfk.model.CFKObject;
import coach.zander.cfk.model.Kreatur;

public class KreaturBean extends CfkObjectBean {
  private Kreatur kreatur;

  public Kreatur getKreatur() {
    return kreatur;
  }

  @Override
  public CFKObject getObject() {
    return getKreatur();
  }

  public void setKreatur(Kreatur kreatur) {
    this.kreatur = kreatur;
  }
}
