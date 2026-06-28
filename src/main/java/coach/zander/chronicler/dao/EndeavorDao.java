package coach.zander.cfk.dao;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import coach.zander.cfk.model.Adventure;

@Component
public class AdventureDao extends GenericDao<Adventure, String> {

  public Adventure findByTitle(String title) {
    return (Adventure) getQuery("byTitle").setParameter(1, title).getSingleResult();
  }

  public Adventure findLatest() {
    List<Adventure> adventures = readAllOrderedByDate();
    return adventures.size() > 0 ? adventures.get(adventures.size() - 1) : null;
  }

  public List<Adventure> readAllOrderedByDate() {
    List<Adventure> adventures = readAll();
    Collections.sort(adventures);
    return adventures;
  }
}
