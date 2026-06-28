package coach.zander.chronicler.beans;

import java.util.List;

import coach.zander.chronicler.model.Creature;
import coach.zander.chronicler.model.Endeavor;
import coach.zander.chronicler.model.Location;
import coach.zander.chronicler.model.Member;
import coach.zander.chronicler.model.Stakeholder;

public class IdxBean extends ChroniclerPageBean {
  private List<Endeavor> endeavors;
  private List<Member> members;
  private List<Location> locations;
  private List<Stakeholder> stakeholders;
  private List<Creature> creatures;

  public List<Endeavor> getEndeavors() {
    return endeavors;
  }

  public List<Member> getMembers() {
    return members;
  }

  public List<Creature> getCreatures() {
    return creatures;
  }

  public List<Stakeholder> getStakeholders() {
    return stakeholders;
  }

  public List<Location> getLocations() {
    return locations;
  }

  public void setEndeavors(List<Endeavor> endeavors) {
    this.endeavors = endeavors;
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
}
