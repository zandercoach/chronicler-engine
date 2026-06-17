package coach.zander.cfk.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import coach.zander.cfk.model.Abenteuer;
import coach.zander.cfk.model.Held;

public class NavigationBean {
  private List<Held> helden;
  private List<Abenteuer> abenteuers;

  public NavigationBean() {
    setAbenteuers(abenteuers = new ArrayList<Abenteuer>());
    setHelden(new ArrayList<Held>());
  }

  public NavigationBean(List<Abenteuer> abenteuers, List<Held> helden) {
    Assert.notNull(abenteuers);
    Assert.notNull(helden);
    this.abenteuers = abenteuers;
    this.helden = helden;
  }

  public List<Abenteuer> getAbenteuers() {
    return abenteuers;
  }

  public List<Held> getHelden() {
    return helden;
  }

  public void setAbenteuers(List<Abenteuer> abenteuers) {
    Assert.notNull(abenteuers);
    this.abenteuers = abenteuers;
  }

  public void setHelden(List<Held> helden) {
    Assert.notNull(helden);
    this.helden = helden;
  }
}
