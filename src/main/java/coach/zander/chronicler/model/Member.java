package coach.zander.cfk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@NamedQueries({ @NamedQuery(name = "Held.all", query = "SELECT h FROM Held h ORDER BY h.name"),
    @NamedQuery(name = "Held.byName", query = "SELECT h FROM Held h WHERE h.name = ?") })
@Entity(name = "Held")
@XmlAccessorType(XmlAccessType.FIELD)
public class Held extends Charakter {
  @ManyToMany(mappedBy = "helden")
  @XmlTransient
  private List<Ereignis> ereignisse = new ArrayList<Ereignis>();

  public void add(Ereignis ereignis) {
    if (ereignis != null) {
      ereignisse.add(ereignis);
    }
  }

  @Override
  public List<Ereignis> getEreignisse() {
    Collections.sort(ereignisse);
    return ereignisse;
  }

  @Override
  public List<Held> getHelden() {
    List<Held> helden = super.getHelden();
    helden.remove(this);
    return helden;
  }

  public void setEreignisse(List<Ereignis> ereignisse) {
    this.ereignisse = ereignisse;
  }
}