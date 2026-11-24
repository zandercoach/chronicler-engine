package coach.zander.chronicler.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlType(propOrder = {"title", "description", "stakeholders", "members", "creatures"})
public class Event extends ChroniclerActivity {
  @XmlTransient private Endeavor endeavor;
  @XmlIDREF @XmlAttribute private Location location;

  @XmlElement(name = "stakeholder")
  @XmlIDREF
  private List<Stakeholder> stakeholders = new ArrayList<Stakeholder>();

  @XmlElement(name = "member")
  @XmlIDREF
  private List<Member> members = new ArrayList<Member>();

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
