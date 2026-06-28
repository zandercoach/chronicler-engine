package coach.zander.chronicler.model;

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

@Entity(name = "Event")
@XmlType(propOrder = { "title", "description", "stakeholders", "members", "creatures" })
public class Event extends ChroniclerActivity {
  @ManyToOne
  @JoinColumn(name = "end_id", nullable = false)
  @XmlTransient
  private Endeavor endeavor;
  @ManyToOne
  @JoinColumn(name = "location")
  @XmlIDREF
  @XmlAttribute
  private Location location;
  @ManyToMany
  @OrderBy("name")
  @JoinTable(joinColumns = @JoinColumn(name = "eve_id"), inverseJoinColumns = @JoinColumn(name = "stakeholder"))
  @XmlElement(name = "stakeholder")
  @XmlIDREF
  private List<Stakeholder> stakeholders = new ArrayList<Stakeholder>();
  @ManyToMany
  @OrderBy("name")
  @JoinTable(joinColumns = @JoinColumn(name = "eve_id"), inverseJoinColumns = @JoinColumn(name = "member"))
  @XmlElement(name = "member")
  @XmlIDREF
  private List<Member> members = new ArrayList<Member>();
  @ManyToMany
  @OrderBy("name")
  @JoinTable(joinColumns = @JoinColumn(name = "eve_id"), inverseJoinColumns = @JoinColumn(name = "creature"))
  @XmlElement(name = "creature")
  @XmlIDREF
  private List<Creature> creatures = new ArrayList<Creature>();

  public void add(Member member) {
    if (member != null) {
      members.add(member);
    }
  }

  public void add(Creature creature) {
    if (creature != null) {
      creatures.add(creature);
    }
  }

  public void add(Stakeholder stakeholder) {
    if (stakeholder != null) {
      stakeholders.add(stakeholder);
    }
  }

  public Endeavor getEndeavor() {
    return endeavor;
  }

  public List<Member> getMembers() {
    return members;
  }

  public List<Creature> getCreatures() {
    return creatures;
  }

  public List<Stakeholder> getStakeholders() {
    return stakeholders;
  }

  public Location getLocation() {
    return location;
  }

  public void setEndeavor(Endeavor endeavor) {
    this.endeavor = endeavor;
  }

  public void setMembers(List<Member> members) {
    this.members = members;
  }

  public void setCreatures(List<Creature> creatures) {
    this.creatures = creatures;
  }

  public void setStakeholders(List<Stakeholder> stakeholders) {
    this.stakeholders = stakeholders;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  @Override
  public String toString() {
    ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
    builder.append("endeavor", endeavor == null ? "<null>" : endeavor.getTitle());
    builder.append("title", getTitle());
    return builder.toString();
  }
}
