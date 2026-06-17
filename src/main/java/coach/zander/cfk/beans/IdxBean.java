package coach.zander.cfk.beans;

import java.util.List;

import coach.zander.cfk.model.Abenteuer;
import coach.zander.cfk.model.Held;
import coach.zander.cfk.model.Kreatur;
import coach.zander.cfk.model.Nsc;
import coach.zander.cfk.model.Ort;

public class IdxBean extends CfkPageBean {
  private List<Abenteuer> abenteuers;
  private List<Held> helden;
  private List<Ort> orte;
  private List<Nsc> nscs;
  private List<Kreatur> kreaturen;

  public List<Abenteuer> getAbenteuers() {
    return abenteuers;
  }

  public List<Held> getHelden() {
    return helden;
  }

  public List<Kreatur> getKreaturen() {
    return kreaturen;
  }

  public List<Nsc> getNscs() {
    return nscs;
  }

  public List<Ort> getOrte() {
    return orte;
  }

  public void setAbenteuers(List<Abenteuer> abenteuers) {
    this.abenteuers = abenteuers;
  }

  public void setHelden(List<Held> helden) {
    this.helden = helden;
  }

  public void setKreaturen(List<Kreatur> kreaturen) {
    this.kreaturen = kreaturen;
  }

  public void setNscs(List<Nsc> nscs) {
    this.nscs = nscs;
  }

  public void setOrte(List<Ort> orte) {
    this.orte = orte;
  }
}
