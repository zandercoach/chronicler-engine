package coach.zander.chronicler.dao;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import coach.zander.chronicler.model.Endeavor;

@Component
public class EndeavorDao extends GenericDao<Endeavor, String> {

  public Endeavor findByTitle(String title) {
    return (Endeavor) getQuery("byTitle").setParameter(1, title).getSingleResult();
  }

  public Endeavor findLatest() {
    List<Endeavor> endeavors = readAllOrderedByDate();
    return endeavors.size() > 0 ? endeavors.get(endeavors.size() - 1) : null;
  }

  public List<Endeavor> readAllOrderedByDate() {
    List<Endeavor> endeavors = readAll();
    Collections.sort(endeavors);
    return endeavors;
  }
}
