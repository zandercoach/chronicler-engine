package coach.zander.cfk.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@NamedQueries({ @NamedQuery(name = "Ort.all", query = "SELECT o FROM Ort o ORDER BY o.name"),
    @NamedQuery(name = "Ort.byName", query = "SELECT o FROM Ort o WHERE o.name = ?") })
@Entity
@XmlType(propOrder = { "name", "region", "x", "y", "description" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Ort extends CFKObject {
  @XmlAttribute
  private String region;
  @XmlAttribute
  private Integer x;
  @XmlAttribute
  private Integer y;
  @OneToMany(mappedBy = "ort")
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
  public List<Ort> getOrte() {
    return null;
  }

  public String getRegion() {
    return region;
  }

  public Integer getX() {
    return x;
  }

  public Integer getY() {
    return y;
  }

  public void setEreignisse(List<Ereignis> ereignisse) {
    this.ereignisse = ereignisse;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public void setX(Integer x) {
    this.x = x;
  }

  public void setY(Integer y) {
    this.y = y;
  }
}
