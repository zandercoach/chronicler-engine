package coach.zander.cfk.model;

import javax.persistence.Column;
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

@Entity(name = "Wegpunkt")
@XmlAccessorType(XmlAccessType.FIELD)
public class Wegpunkt {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @XmlTransient
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "abe_id", nullable = false)
  @XmlTransient
  private Abenteuer abenteuer;
  @ManyToOne
  @JoinColumn(name = "ort")
  @XmlIDREF
  @XmlAttribute
  private Ort ort;
  @XmlAttribute
  private Integer x;
  @XmlAttribute
  private Integer y;

  public Abenteuer getAbenteuer() {
    return abenteuer;
  }

  public Integer getId() {
    return id;
  }

  public Ort getOrt() {
    return ort;
  }

  public Integer getX() {
    return x != null ? x : ort.getX();
  }

  public Integer getY() {
    return y != null ? y : ort.getY();
  }

  public void setAbenteuer(Abenteuer abenteuer) {
    this.abenteuer = abenteuer;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setOrt(Ort ort) {
    this.ort = ort;
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
    builder.append("abenteuer", abenteuer == null ? "<null>" : abenteuer.getTitle());
    builder.append("ort", ort == null ? "<null>" : ort.getName());
    builder.append("x", getX());
    builder.append("y", getY());
    return builder.toString();
  }
}
