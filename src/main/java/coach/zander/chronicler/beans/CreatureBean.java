package coach.zander.chronicler.beans;

import coach.zander.chronicler.model.ChroniclerObject;
import coach.zander.chronicler.model.Creature;

public class CreatureBean extends ChroniclerObjectBean {
  private Creature creature;

  public Creature getCreature() {
    return creature;
  }

  @Override
  public ChroniclerObject getObject() {
    return getCreature();
  }

  public void setCreature(Creature creature) {
    this.creature = creature;
  }
}
