package coach.zander.chronicler.beans;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import coach.zander.chronicler.model.Creature;
import coach.zander.chronicler.model.Endeavor;
import coach.zander.chronicler.model.Location;
import coach.zander.chronicler.model.Member;
import coach.zander.chronicler.model.Stakeholder;

@Component
public class EndeavorBean extends ChroniclerPageBean {
  private Endeavor endeavor;

  public Endeavor getEndeavor() {
    return endeavor;
  }

  public List<Member> getMembers() {
    return endeavor.getMembers();
  }

  public List<Creature> getCreatures() {
    return endeavor.getCreatures();
  }

  public List<Stakeholder> getStakeholders() {
    return endeavor.getStakeholders();
  }

  public List<Location> getLocations() {
    return endeavor.getLocations();
  }

  public void setEndeavor(Endeavor endeavor) {
    Assert.notNull(endeavor);
    this.endeavor = endeavor;
    this.setPageTitle(endeavor.getClass().getSimpleName() + " - " + endeavor.getTitle());
  }
}
