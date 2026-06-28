package coach.zander.cfk.dao;

import org.springframework.stereotype.Component;

import coach.zander.cfk.model.Nsc;

@Component
public class NscDao extends GenericDao<Nsc, String> {

  public Nsc findByName(String name) {
    return (Nsc) getQuery("byName").setParameter(1, name).getSingleResult();
  }
}
