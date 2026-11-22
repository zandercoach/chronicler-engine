package coach.zander.chronicler.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"name", "region", "x", "y", "description"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Location extends ChroniclerObject {
  @XmlAttribute private String region;
  @XmlAttribute private Integer x;
  @XmlAttribute private Integer y;
  @XmlTransient private List<Event> events = new ArrayList<Event>();

  public void add(Event event) {
    if (event != null) {
      events.add(event);
    }
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
