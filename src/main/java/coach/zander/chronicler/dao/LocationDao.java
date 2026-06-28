package coach.zander.chronicler.dao;

import org.springframework.stereotype.Component;

import coach.zander.chronicler.model.Location;

@Component
public class LocationDao extends GenericDao<Location, String> {

  public Location findByName(String name) {
    return (Location) getQuery("byName").setParameter(1, name).getSingleResult();
  }
}
