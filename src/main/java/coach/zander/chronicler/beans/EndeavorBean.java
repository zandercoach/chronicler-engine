package coach.zander.cfk.beans;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import coach.zander.cfk.model.Adventure;
import coach.zander.cfk.model.Held;
import coach.zander.cfk.model.Kreatur;
import coach.zander.cfk.model.Nsc;
import coach.zander.cfk.model.Ort;

@Component
public class AdventureBean extends CfkPageBean {
  private Adventure adventure;

  public Adventure getAdventure() {
    return adventure;
  }

  public List<Held> getHelden() {
    return adventure.getHelden();
  }

  public List<Kreatur> getKreaturen() {
    return adventure.getKreaturen();
  }

  public List<Nsc> getNscs() {
    return adventure.getNscs();
  }

  public List<Ort> getOrte() {
    return adventure.getOrte();
  }

  public void setAdventure(Adventure adventure) {
    Assert.notNull(adventure);
    this.adventure = adventure;
    this.setPageTitle(adventure.getClass().getSimpleName() + " - " + adventure.getTitle());
  }
}
