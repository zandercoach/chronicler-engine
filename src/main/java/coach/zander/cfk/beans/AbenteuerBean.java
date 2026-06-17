package coach.zander.cfk.beans;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import coach.zander.cfk.model.Abenteuer;
import coach.zander.cfk.model.Held;
import coach.zander.cfk.model.Kreatur;
import coach.zander.cfk.model.Nsc;
import coach.zander.cfk.model.Ort;

@Component
public class AbenteuerBean extends CfkPageBean {
  private Abenteuer abenteuer;

  public Abenteuer getAbenteuer() {
    return abenteuer;
  }

  public List<Held> getHelden() {
    return abenteuer.getHelden();
  }

  public List<Kreatur> getKreaturen() {
    return abenteuer.getKreaturen();
  }

  public List<Nsc> getNscs() {
    return abenteuer.getNscs();
  }

  public List<Ort> getOrte() {
    return abenteuer.getOrte();
  }

  public void setAbenteuer(Abenteuer abenteuer) {
    Assert.notNull(abenteuer);
    this.abenteuer = abenteuer;
    this.setPageTitle(abenteuer.getClass().getSimpleName() + " - " + abenteuer.getTitle());
  }
}
