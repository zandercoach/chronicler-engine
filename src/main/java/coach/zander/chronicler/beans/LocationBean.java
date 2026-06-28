package coach.zander.cfk.beans;

import coach.zander.cfk.model.CFKObject;
import coach.zander.cfk.model.Ort;

public class OrtBean extends CfkObjectBean {
  private Ort ort;

  @Override
  public CFKObject getObject() {
    return getOrt();
  }

  public Ort getOrt() {
    return ort;
  }

  public void setOrt(Ort ort) {
    this.ort = ort;
  }
}
