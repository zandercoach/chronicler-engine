package coach.zander.chronicler.dao;

import org.springframework.stereotype.Component;

import coach.zander.chronicler.model.Creature;

@Component
public class CreatureDao extends GenericDao<Creature, String> {

  public Creature findByName(String name) {
    return (Creature) getQuery("byName").setParameter(1, name).getSingleResult();
  }
}
