package coach.zander.chronicler.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ChroniclerData {
  private List<Location> locations;
  private List<Member> members;
  private List<Stakeholder> stakeholders;
  private List<Creature> creatures;
  private List<Endeavor> endeavors;
  private List<Session> sessions;

  @XmlElement(name = "endeavor")
  public List<Endeavor> getEndeavors() {
    return endeavors;
  }

  @XmlElementWrapper(name = "members")
  @XmlElement(name = "member")
  public List<Member> getMembers() {
    return members;
  }

  @XmlElementWrapper(name="creatures")
  @XmlElement(name = "creature")
  public List<Creature> getCreatures() {
    return creatures;
  }

  @XmlElementWrapper(name="stakeholders")
  @XmlElement(name = "stakeholder")
  public List<Stakeholder> getStakeholders() {
    return stakeholders;
  }

  @XmlElementWrapper(name="locations")
  @XmlElement(name = "location")
  public List<Location> getLocations() {
    return locations;
  }

  @XmlElementWrapper(name="sessions")
  @XmlElement(name = "session")
  public List<Session> getSessions() {
    return sessions;
  }

  public void setEndeavors(List<Endeavor> endeavors) {
    this.endeavors = endeavors;
    for (Endeavor endeavor : endeavors) {
      for (Event event : endeavor.getEvents()) {
        event.setEndeavor(endeavor);
      }
      for (Waypoint waypoint : endeavor.getWaypoints()) {
        waypoint.setEndeavor(endeavor);
      }
      for (Session session : endeavor.getSessions()) {
        session.addEndeavor(endeavor);
      }
    }
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

  public void setLocations(List<Location> locations) {
    this.locations = locations;
  }

  public void setSessions(List<Session> sessions) {
    this.sessions = sessions;
  }

  @Override
  public String toString() {
    return "ChroniclerData [locations=" + locations + ", members=" + members + ", stakeholders=" + stakeholders + ", creatures=" + creatures
        + ", endeavors=" + endeavors + ", sessions=" + sessions + "]";
  }

}
