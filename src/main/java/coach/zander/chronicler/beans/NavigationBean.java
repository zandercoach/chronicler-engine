package coach.zander.cfk.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import coach.zander.cfk.model.Adventure;
import coach.zander.cfk.model.Held;

public class NavigationBean {
  private List<Held> helden;
  private List<Adventure> adventures;

  public NavigationBean() {
    setAdventures(adventures = new ArrayList<Adventure>());
    setHelden(new ArrayList<Held>());
  }

  public NavigationBean(List<Adventure> adventures, List<Held> helden) {
    Assert.notNull(adventures);
    Assert.notNull(helden);
    this.adventures = adventures;
    this.helden = helden;
  }

  public List<Adventure> getAdventures() {
    return adventures;
  }

  public List<Held> getHelden() {
    return helden;
  }

  public void setAdventures(List<Adventure> adventures) {
    Assert.notNull(adventures);
    this.adventures = adventures;
  }

  public void setHelden(List<Held> helden) {
    Assert.notNull(helden);
    this.helden = helden;
  }
}
