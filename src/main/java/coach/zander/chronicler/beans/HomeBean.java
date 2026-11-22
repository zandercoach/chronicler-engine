package coach.zander.chronicler.beans;

import coach.zander.chronicler.model.Endeavor;
import coach.zander.chronicler.model.Member;
import java.util.ArrayList;
import java.util.List;

public class HomeBean extends ChroniclerPageBean {
  private Endeavor endeavor;
  private List<Member> members = new ArrayList<Member>();

  public Endeavor getEndeavor() {
    return endeavor;
  }

  public void setEndeavor(Endeavor endeavor) {
    this.endeavor = endeavor;
    this.setPageTitle("Startseite");
  }

  /** The heroes on the home page: all members in data order, except the retired ones. */
  public List<Member> getMembers() {
    return members;
  }

  public void setMembers(List<Member> all) {
    members = new ArrayList<Member>();
    if (all != null) {
      for (Member member : all) {
        if (!member.isRetired()) {
          members.add(member);
        }
      }
    }
  }
}
