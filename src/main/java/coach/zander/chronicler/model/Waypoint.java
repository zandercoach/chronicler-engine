package coach.zander.chronicler.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity(name = "Waypoint")
@XmlAccessorType(XmlAccessType.FIELD)
public class Waypoint {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @XmlTransient
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "end_id", nullable = false)
  @XmlTransient
  private Endeavor endeavor;
  @ManyToOne
  @JoinColumn(name = "location")
  @XmlIDREF
  @XmlAttribute
  private Location location;
  @XmlAttribute
  private Integer x;
  @XmlAttribute
  private Integer y;

  public Endeavor getEndeavor() {
    return endeavor;
  }

  public Integer getId() {
    return id;
  }

  public Location getLocation() {
    return location;
  }

  public Integer getX() {
    return x != null ? x : location.getX();
  }

  public Integer getY() {
    return y != null ? y : location.getY();
  }

  public void setEndeavor(Endeavor endeavor) {
    this.endeavor = endeavor;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public void setX(Integer x) {
    this.x = x;
  }

  public void setY(Integer y) {
    this.y = y;
  }

  @Override
  public String toString() {
    ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
    builder.append("endeavor", endeavor == null ? "<null>" : endeavor.getTitle());
    builder.append("location", location == null ? "<null>" : location.getName());
    builder.append("x", getX());
    builder.append("y", getY());
    return builder.toString();
  }
}
