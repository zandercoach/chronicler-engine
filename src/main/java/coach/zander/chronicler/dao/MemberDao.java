package coach.zander.chronicler.dao;

import org.springframework.stereotype.Component;

import coach.zander.chronicler.model.Member;

@Component
public class MemberDao extends GenericDao<Member, String> {

  public Member findByName(String name) {
    return (Member) getQuery("byName").setParameter(1, name).getSingleResult();
  }
}
