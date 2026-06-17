package coach.zander.cfk.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity(name = "Ereignis")
@XmlType(propOrder = { "title", "description", "nscs", "helden", "kreaturen" })
public class Ereignis extends CFKActivity {
  @ManyToOne
  @JoinColumn(name = "abe_id", nullable = false)
  @XmlTransient
  private Abenteuer abenteuer;
  @ManyToOne
  @JoinColumn(name = "ort")
  @XmlIDREF
  @XmlAttribute
  private Ort ort;
  @ManyToMany
  @OrderBy("name")
  @JoinTable(joinColumns = @JoinColumn(name = "ere_id"), inverseJoinColumns = @JoinColumn(name = "nsc"))
  @XmlElement(name = "nsc")
  @XmlIDREF
  private List<Nsc> nscs = new ArrayList<Nsc>();
  @ManyToMany
  @OrderBy("name")
  @JoinTable(joinColumns = @JoinColumn(name = "ere_id"), inverseJoinColumns = @JoinColumn(name = "held"))
  @XmlElement(name = "held")
  @XmlIDREF
  private List<Held> helden = new ArrayList<Held>();
  @ManyToMany
  @OrderBy("name")
  @JoinTable(joinColumns = @JoinColumn(name = "ere_id"), inverseJoinColumns = @JoinColumn(name = "kreatur"))
  @XmlElement(name = "kreatur")
  @XmlIDREF
  private List<Kreatur> kreaturen = new ArrayList<Kreatur>();

  public void add(Held held) {
    if (held != null) {
      helden.add(held);
    }
  }

  public void add(Kreatur kreatur) {
    if (kreatur != null) {
      kreaturen.add(kreatur);
    }
  }

  public void add(Nsc nsc) {
    if (nsc != null) {
      nscs.add(nsc);
    }
  }

  public Abenteuer getAbenteuer() {
    return abenteuer;
  }

  public List<Held> getHelden() {
    return helden;
  }

  public List<Kreatur> getKreaturen() {
    return kreaturen;
  }

  public List<Nsc> getNscs() {
    return nscs;
  }

  public Ort getOrt() {
    return ort;
  }

  public void setAbenteuer(Abenteuer abenteuer) {
    this.abenteuer = abenteuer;
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

  public void setOrt(Ort ort) {
    this.ort = ort;
  }

  @Override
  public String toString() {
    ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
    builder.append("abenteuer", abenteuer == null ? "<null>" : abenteuer.getTitle());
    builder.append("title", getTitle());
    return builder.toString();
  }
}
