package coach.zander.chronicler.beans;

import coach.zander.chronicler.model.ChroniclerObject;
import coach.zander.chronicler.model.Member;

public class MemberBean extends ChroniclerObjectBean {
  private Member member;

  public Member getMember() {
    return member;
  }

  @Override
  public ChroniclerObject getObject() {
    return getMember();
  }

  public void setMember(Member member) {
    this.member = member;
  }
}
