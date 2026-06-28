package coach.zander.chronicler.dao;

import org.springframework.stereotype.Component;

import coach.zander.chronicler.model.Stakeholder;

@Component
public class StakeholderDao extends GenericDao<Stakeholder, String> {

  public Stakeholder findByName(String name) {
    return (Stakeholder) getQuery("byName").setParameter(1, name).getSingleResult();
  }
}
