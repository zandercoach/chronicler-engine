package coach.zander.chronicler.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@XmlType(propOrder = {"name", "region", "x", "y", "description"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Location extends ChroniclerObject {
  @XmlAttribute private String region;
  @XmlAttribute private Integer x;
  @XmlAttribute private Integer y;
  @XmlTransient private List<Event> events = new ArrayList<Event>();
  @XmlTransient private final SortedSet<Endeavor> routes = new TreeSet<Endeavor>();

  public void add(Event event) {
    if (event != null) {
      events.add(event);
    }
  }

  /** An endeavor whose route passes through here, with or without an event at this place. */
  public void addRoute(Endeavor endeavor) {
    if (endeavor != null) {
      routes.add(endeavor);
    }
  }

  /** The endeavors of the events here and those whose route passes through. */
  @Override
  public List<Endeavor> getEndeavors() {
    SortedSet<Endeavor> endeavors = new TreeSet<Endeavor>(super.getEndeavors());
    endeavors.addAll(routes);
    return new ArrayList<Endeavor>(endeavors);
  }

  @Override
  public List<Event> getEvents() {
    return events;
  }

  @Override
  public List<Location> getLocations() {
    return null;
  }

  public String getRegion() {
    return region;
  }

  public Integer getX() {
    return x;
  }

  public Integer getY() {
    return y;
  }

  public void setEvents(List<Event> events) {
    this.events = events;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public void setX(Integer x) {
    this.x = x;
  }

  public void setY(Integer y) {
    this.y = y;
  }
}
