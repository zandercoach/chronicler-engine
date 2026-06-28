package coach.zander.cfk.dao;

import org.springframework.stereotype.Component;

import coach.zander.cfk.model.Kreatur;

@Component
public class KreaturDao extends GenericDao<Kreatur, String> {

  public Kreatur findByName(String name) {
    return (Kreatur) getQuery("byName").setParameter(1, name).getSingleResult();
  }
}
