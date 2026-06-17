package coach.zander.cfk.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class CFKData {
  private List<Ort> orte;
  private List<Held> helden;
  private List<Nsc> nscs;
  private List<Kreatur> kreaturen;
  private List<Abenteuer> abenteuers;
  private List<Session> sessions;

  @XmlElement(name = "abenteuer")
  public List<Abenteuer> getAbenteuers() {
    return abenteuers;
  }

  @XmlElementWrapper(name = "helden")
  @XmlElement(name = "held")
  public List<Held> getHelden() {
    return helden;
  }

  @XmlElementWrapper(name="kreaturen")
  @XmlElement(name = "kreatur")
  public List<Kreatur> getKreaturen() {
    return kreaturen;
  }

  @XmlElementWrapper(name="nscs")
  @XmlElement(name = "nsc")
  public List<Nsc> getNscs() {
    return nscs;
  }

  @XmlElementWrapper(name="orte")
  @XmlElement(name = "ort")
  public List<Ort> getOrte() {
    return orte;
  }

  @XmlElementWrapper(name="sessions")
  @XmlElement(name = "session")
  public List<Session> getSessions() {
    return sessions;
  }

  public void setAbenteuers(List<Abenteuer> abenteuers) {
    this.abenteuers = abenteuers;
    for (Abenteuer a : abenteuers) {
      for (Ereignis e : a.getEreignisse()) {
        e.setAbenteuer(a);
      }
      for (Wegpunkt w : a.getWegpunkte()) {
        w.setAbenteuer(a);
      }
      for (Session s : a.getSessions()) {
        s.addAbenteuer(a);
      }
    }
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

  public void setSessions(List<Session> sessions) {
    this.sessions = sessions;
  }

  @Override
  public String toString() {
    return "CFKData [orte=" + orte + ", helden=" + helden + ", nscs=" + nscs + ", kreaturen=" + kreaturen
        + ", abenteuers=" + abenteuers + ", sessions=" + sessions + "]";
  }

}
