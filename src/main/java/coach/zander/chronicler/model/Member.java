package coach.zander.chronicler.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@NamedQueries({ @NamedQuery(name = "Member.all", query = "SELECT m FROM Member m ORDER BY m.name"),
    @NamedQuery(name = "Member.byName", query = "SELECT m FROM Member m WHERE m.name = ?") })
@Entity(name = "Member")
@XmlAccessorType(XmlAccessType.FIELD)
public class Member extends Person {
  @ManyToMany(mappedBy = "members")
  @XmlTransient
  private List<Event> events = new ArrayList<Event>();

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