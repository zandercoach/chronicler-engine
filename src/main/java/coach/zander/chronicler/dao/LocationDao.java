package coach.zander.cfk.dao;

import org.springframework.stereotype.Component;

import coach.zander.cfk.model.Ort;

@Component
public class OrtDao extends GenericDao<Ort, String> {

  public Ort findByName(String name) {
    return (Ort) getQuery("byName").setParameter(1, name).getSingleResult();
  }
}
