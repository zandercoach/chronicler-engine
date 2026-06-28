package coach.zander.chronicler.beans;

import coach.zander.chronicler.model.ChroniclerObject;
import coach.zander.chronicler.model.Stakeholder;

public class StakeholderBean extends ChroniclerObjectBean {
  private Stakeholder stakeholder;

  public Stakeholder getStakeholder() {
    return stakeholder;
  }

  @Override
  public ChroniclerObject getObject() {
    return getStakeholder();
  }

  public void setStakeholder(Stakeholder stakeholder) {
    this.stakeholder = stakeholder;
  }
}
