package coach.zander.chronicler.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Stakeholder extends Person {
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
  public List<Stakeholder> getStakeholders() {
    List<Stakeholder> stakeholders = super.getStakeholders();
    stakeholders.remove(this);
    return stakeholders;
  }

  public void setEvents(List<Event> events) {
    this.events = events;
  }
}
