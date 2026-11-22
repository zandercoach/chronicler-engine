package coach.zander.chronicler.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class Member extends Person {
  @XmlTransient
  private List<Event> events = new ArrayList<Event>();
  /** Short reason a member has retired, e.g. "R.I.P."; a member with a status is retired. */
  @XmlAttribute
  private String status;
  /** One sentence explaining the status, shown with it on the member's page. */
  @XmlAttribute
  private String statusNote;

  /** Retired members keep their page but are not shown on the home page. */
  public boolean isRetired() {
    return status != null && !status.trim().isEmpty();
  }

  public String getStatus() {
    return status;
  }

  public String getStatusNote() {
    return statusNote;
  }

  public void add(Event event) {
    if (event != null) {
      events.add(event);
    }
  }

  @Override
  public List<Event> getEvents() {
    Collections.sort(events);
    return events;
  }

  @Override
  public List<Member> getMembers() {
    List<Member> members = super.getMembers();
    members.remove(this);
    return members;
  }

  public void setEvents(List<Event> events) {
    this.events = events;
  }
}