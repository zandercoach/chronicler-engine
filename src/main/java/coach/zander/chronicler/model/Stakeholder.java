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

@NamedQueries({ @NamedQuery(name = "Nsc.all", query = "SELECT n FROM Nsc n ORDER BY n.name"),
    @NamedQuery(name = "Nsc.byName", query = "SELECT n FROM Nsc n WHERE n.name = ?") })
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Nsc extends Charakter {
  @ManyToMany(mappedBy = "nscs")
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
  public List<Nsc> getNscs() {
    List<Nsc> nscs = super.getNscs();
    nscs.remove(this);
    return nscs;
  }

  public void setEreignisse(List<Ereignis> ereignisse) {
    this.ereignisse = ereignisse;
  }
}
