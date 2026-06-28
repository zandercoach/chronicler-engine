package coach.zander.cfk.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

@NamedQueries({ @NamedQuery(name = "Adventure.all", query = "SELECT a FROM Adventure a ORDER BY a.title"),
    @NamedQuery(name = "Adventure.byTitle", query = "SELECT a FROM Adventure a WHERE a.title = ?") })
@Entity
public class Adventure extends CFKActivity {
  @OneToMany(mappedBy = "adventure", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @XmlElement(name = "ereignis")
  private List<Ereignis> ereignisse = new ArrayList<Ereignis>();

  @OneToMany(mappedBy = "adventure", cascade = CascadeType.ALL)
  @XmlElement(name = "wegpunkt")
  private final List<Wegpunkt> wegpunkte = new ArrayList<Wegpunkt>();

  @ManyToMany(cascade = CascadeType.ALL)
  @OrderBy("id DESC")
  @JoinTable(joinColumns = @JoinColumn(name = "abe_id"), inverseJoinColumns = @JoinColumn(name = "ses_id"))
  @XmlIDREF
  @XmlElement(name = "session")
  private final List<Session> sessions = new ArrayList<Session>();

  public void add(Ereignis ereignis) {
    if (ereignis != null) {
      ereignisse.add(ereignis);
      ereignis.setAdventure(this);
    }
  }

  public List<Ereignis> getEreignisse() {
    return ereignisse;
  }

  public List<Held> getHelden() {
    SortedSet<Held> helden = new TreeSet<Held>();
    for (Ereignis e : ereignisse) {
      helden.addAll(e.getHelden());
    }
    return new ArrayList<Held>(helden);
  }

  public List<Kreatur> getKreaturen() {
    SortedSet<Kreatur> kreaturen = new TreeSet<Kreatur>();
    for (Ereignis e : ereignisse) {
      kreaturen.addAll(e.getKreaturen());
    }
    return new ArrayList<Kreatur>(kreaturen);
  }

  public List<Nsc> getNscs() {
    SortedSet<Nsc> nscs = new TreeSet<Nsc>();
    for (Ereignis e : ereignisse) {
      nscs.addAll(e.getNscs());
    }
    return new ArrayList<Nsc>(nscs);
  }

  public List<Ort> getOrte() {
    SortedSet<Ort> orte = new TreeSet<Ort>();
    Ort last = null;
    for (Ereignis e : getEreignisse()) {
      Ort o = e.getOrt();
      if (o != null && !o.equals(last)) {
        orte.add(o);
        last = o;
      }
    }
    return new ArrayList<Ort>(orte);
  }

  public List<Session> getSessions() {
    return sessions;
  }

  public List<Wegpunkt> getWegpunkte() {
    return wegpunkte;
  }

  public void setEreignisse(List<Ereignis> ereignisse) {
    this.ereignisse = ereignisse;
  }
}
