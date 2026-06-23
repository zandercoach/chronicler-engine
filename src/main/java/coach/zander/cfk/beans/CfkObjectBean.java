package coach.zander.cfk.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import coach.zander.cfk.model.Adventure;
import coach.zander.cfk.model.CFKObject;
import coach.zander.cfk.model.Ereignis;
import coach.zander.cfk.model.Held;
import coach.zander.cfk.model.Kreatur;
import coach.zander.cfk.model.Nsc;
import coach.zander.cfk.model.Ort;

public abstract class CfkObjectBean extends CfkPageBean {
  public List<Adventure> getAdventures() {
    SortedSet<Adventure> adventures = new TreeSet<Adventure>();
    for (Ereignis e : getObject().getEreignisse()) {
      adventures.add(e.getAdventure());
    }
    return new ArrayList<Adventure>(adventures);
  }

  public List<Held> getHelden() {
    SortedSet<Held> helden = new TreeSet<Held>();
    for (Ereignis e : getObject().getEreignisse()) {
      helden.addAll(e.getHelden());
    }
    return new ArrayList<Held>(helden);
  }

  public List<Kreatur> getKreaturen() {
    SortedSet<Kreatur> kreaturen = new TreeSet<Kreatur>();
    for (Ereignis e : getObject().getEreignisse()) {
      kreaturen.addAll(e.getKreaturen());
    }
    return new ArrayList<Kreatur>(kreaturen);
  }

  public List<Nsc> getNscs() {
    SortedSet<Nsc> nscs = new TreeSet<Nsc>();
    for (Ereignis e : getObject().getEreignisse()) {
      nscs.addAll(e.getNscs());
    }
    return new ArrayList<Nsc>(nscs);
  }

  public abstract CFKObject getObject();

  public List<Ort> getOrte() {
    SortedSet<Ort> orte = new TreeSet<Ort>();
    for (Ereignis e : getObject().getEreignisse()) {
      Ort o = e.getOrt();
      orte.add(o);
    }
    return new ArrayList<Ort>(orte);
  }

  @Override
  public String getPageTitle() {
    return getObject().getClass().getSimpleName() + " - " + getObject().getName();
  }
}
