package coach.zander.chronicler.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlTransient;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
public class Waypoint {
  @XmlTransient private Integer id;
  @XmlTransient private Endeavor endeavor;
  @XmlIDREF @XmlAttribute private Location location;
  @XmlAttribute private Integer x;
  @XmlAttribute private Integer y;

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
