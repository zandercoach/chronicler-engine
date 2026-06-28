package coach.zander.cfk.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@NamedQueries({ @NamedQuery(name = "Kreatur.all", query = "SELECT k FROM Kreatur k ORDER BY k.name"),
    @NamedQuery(name = "Kreatur.byName", query = "SELECT k FROM Kreatur k WHERE k.name = ?") })
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Kreatur extends CFKObject {
  @ManyToMany(mappedBy = "kreaturen")
  // @OrderBy("id")
  @XmlTransient
  private List<Ereignis> ereignisse = new ArrayList<Ereignis>();

  public void add(Ereignis ereignis) {
    if (ereignis != null) {
      ereignisse.add(ereignis);
    }
  }

  @Override
  public List<Ereignis> getEreignisse() {
    return ereignisse;
  }

  @Override
  public List<Kreatur> getKreaturen() {
    return null;
  }

  public void setEreignisse(List<Ereignis> ereignisse) {
    this.ereignisse = ereignisse;
  }
}
