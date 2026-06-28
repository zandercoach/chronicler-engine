package coach.zander.chronicler.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import coach.zander.chronicler.model.ChroniclerObject;
import coach.zander.chronicler.model.Creature;
import coach.zander.chronicler.model.Endeavor;
import coach.zander.chronicler.model.Event;
import coach.zander.chronicler.model.Location;
import coach.zander.chronicler.model.Member;
import coach.zander.chronicler.model.Stakeholder;

public abstract class ChroniclerObjectBean extends ChroniclerPageBean {
  public List<Endeavor> getAdventures() {
    SortedSet<Endeavor> endeavors = new TreeSet<Endeavor>();
    for (Event event : getObject().getEvents()) {
      endeavors.add(event.getEndeavor());
    }
    return new ArrayList<Endeavor>(endeavors);
  }

  public List<Member> getMembers() {
    SortedSet<Member> members = new TreeSet<Member>();
    for (Event event : getObject().getEvents()) {
      members.addAll(event.getMembers());
    }
    return new ArrayList<Member>(members);
  }

  public List<Creature> getCreatures() {
    SortedSet<Creature> creatures = new TreeSet<Creature>();
    for (Event event : getObject().getEvents()) {
      creatures.addAll(event.getCreatures());
    }
    return new ArrayList<Creature>(creatures);
  }

  public List<Stakeholder> getStakeholders() {
    SortedSet<Stakeholder> stakeholders = new TreeSet<Stakeholder>();
    for (Event event : getObject().getEvents()) {
      stakeholders.addAll(event.getStakeholders());
    }
    return new ArrayList<Stakeholder>(stakeholders);
  }

  public abstract ChroniclerObject getObject();

  public List<Location> getLocations() {
    SortedSet<Location> locations = new TreeSet<Location>();
    for (Event event : getObject().getEvents()) {
      Location location = event.getLocation();
      locations.add(location);
    }
    return new ArrayList<Location>(locations);
  }

  @Override
  public String getPageTitle() {
    return getObject().getClass().getSimpleName() + " - " + getObject().getName();
  }
}
