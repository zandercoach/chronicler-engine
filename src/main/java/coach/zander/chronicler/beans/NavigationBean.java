package coach.zander.chronicler.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import coach.zander.chronicler.model.Endeavor;
import coach.zander.chronicler.model.Member;

public class NavigationBean {
  private List<Member> members;
  private List<Endeavor> endeavors;

  public NavigationBean() {
    setEndeavors(endeavors = new ArrayList<Endeavor>());
    setMembers(new ArrayList<Member>());
  }

  public NavigationBean(List<Endeavor> endeavors, List<Member> members) {
    Assert.notNull(endeavors);
    Assert.notNull(members);
    this.endeavors = endeavors;
    this.members = members;
  }

  public List<Endeavor> getEndeavors() {
    return endeavors;
  }

  public List<Member> getMembers() {
    return members;
  }

  public void setEndeavors(List<Endeavor> endeavors) {
    Assert.notNull(endeavors);
    this.endeavors = endeavors;
  }

  public void setMembers(List<Member> members) {
    Assert.notNull(members);
    this.members = members;
  }
}
