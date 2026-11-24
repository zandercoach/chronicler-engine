package coach.zander.chronicler.beans;

import coach.zander.chronicler.model.Endeavor;
import coach.zander.chronicler.model.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class NavigationBean {
  private List<Member> members;
  private List<Endeavor> endeavors;

  public NavigationBean() {
    setEndeavors(endeavors = new ArrayList<Endeavor>());
    setMembers(new ArrayList<Member>());
  }

  public NavigationBean(List<Endeavor> endeavors, List<Member> members) {
    Objects.requireNonNull(endeavors, "endeavors must not be null");
    Objects.requireNonNull(members, "members must not be null");
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
    Objects.requireNonNull(endeavors, "endeavors must not be null");
    this.endeavors = endeavors;
  }

  public void setMembers(List<Member> members) {
    Objects.requireNonNull(members, "members must not be null");
    this.members = members;
  }
}
