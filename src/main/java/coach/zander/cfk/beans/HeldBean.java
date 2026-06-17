package coach.zander.cfk.beans;

import coach.zander.cfk.model.CFKObject;
import coach.zander.cfk.model.Held;

public class HeldBean extends CfkObjectBean {
  private Held held;

  public Held getHeld() {
    return held;
  }

  @Override
  public CFKObject getObject() {
    return getHeld();
  }

  public void setHeld(Held held) {
    this.held = held;
  }
}
