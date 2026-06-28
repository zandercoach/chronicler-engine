package coach.zander.chronicler.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

@NamedQueries({ @NamedQuery(name = "Endeavor.all", query = "SELECT e FROM Endeavor e ORDER BY e.title"),
    @NamedQuery(name = "Endeavor.byTitle", query = "SELECT e FROM Endeavor e WHERE e.title = ?") })
@Entity
public class Endeavor extends ChroniclerActivity {
  @OneToMany(mappedBy = "endeavor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @XmlElement(name = "event")
  private List<Event> events = new ArrayList<Event>();

  @OneToMany(mappedBy = "endeavor", cascade = CascadeType.ALL)
  @XmlElement(name = "waypoint")
  private final List<Waypoint> waypoints = new ArrayList<Waypoint>();

  @ManyToMany(cascade = CascadeType.ALL)
  @OrderBy("id DESC")
  @JoinTable(joinColumns = @JoinColumn(name = "end_id"), inverseJoinColumns = @JoinColumn(name = "ses_id"))
  @XmlIDREF
  @XmlElement(name = "session")
  private final List<Session> sessions = new ArrayList<Session>();

  public void add(Event event) {
    if (event != null) {
      events.add(event);
      event.setEndeavor(this);
    }
  }

  public List<Event> getEvents() {
    return events;
  }

  public List<Member> getMembers() {
    SortedSet<Member> members = new TreeSet<Member>();
    for (Event eevent : events) {
      members.addAll(eevent.getMembers());
    }
    return new ArrayList<Member>(members);
  }

  public List<Creature> getCreatures() {
    SortedSet<Creature> creatures = new TreeSet<Creature>();
    for (Event e : events) {
      creatures.addAll(e.getCreatures());
    }
    return new ArrayList<Creature>(creatures);
  }

  public List<Stakeholder> getStakeholders() {
    SortedSet<Stakeholder> stakeholders = new TreeSet<Stakeholder>();
    for (Event e : events) {
      stakeholders.addAll(e.getStakeholders());
    }
    return new ArrayList<Stakeholder>(stakeholders);
  }

  public List<Location> getLocations() {
    SortedSet<Location> locations = new TreeSet<Location>();
    Location last = null;
    for (Event e : getEvents()) {
      Location location = e.getLocation();
      if (location != null && !location.equals(last)) {
        locations.add(location);
        last = location;
      }
    }
    return new ArrayList<Location>(locations);
  }

  public List<Session> getSessions() {
    return sessions;
  }

  public List<Waypoint> getWaypoints() {
    return waypoints;
  }

  public void setEvents(List<Event> events) {
    this.events = events;
  }
}
