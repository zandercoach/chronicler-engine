package coach.zander.chronicler.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class Stakeholder extends Person {
  @XmlTransient
  private List<Event> events = new ArrayList<Event>();

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
  public List<Stakeholder> getStakeholders() {
    List<Stakeholder> stakeholders = super.getStakeholders();
    stakeholders.remove(this);
    return stakeholders;
  }

  public void setEvents(List<Event> events) {
    this.events = events;
  }
}
