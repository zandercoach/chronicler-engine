package coach.zander.chronicler.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@NamedQueries({ @NamedQuery(name = "Creature.all", query = "SELECT c FROM Creature c ORDER BY c.name"),
    @NamedQuery(name = "Creature.byName", query = "SELECT c FROM Creature c WHERE c.name = ?") })
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Creature extends ChroniclerObject {
  @ManyToMany(mappedBy = "creatures")
  // @OrderBy("id")
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
  public List<Creature> getCreatures() {
    return null;
  }

  public void setEvents(List<Event> ereignisse) {
    this.events = ereignisse;
  }
}
