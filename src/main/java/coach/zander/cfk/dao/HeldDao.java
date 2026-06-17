package coach.zander.cfk.dao;

import org.springframework.stereotype.Component;

import coach.zander.cfk.model.Held;

@Component
public class HeldDao extends GenericDao<Held, String> {

  public Held findByName(String name) {
    return (Held) getQuery("byName").setParameter(1, name).getSingleResult();
  }
}
