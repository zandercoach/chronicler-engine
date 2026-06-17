package coach.zander.cfk.dao;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import coach.zander.cfk.model.Abenteuer;

@Component
public class AbenteuerDao extends GenericDao<Abenteuer, String> {

  public Abenteuer findByTitle(String title) {
    return (Abenteuer) getQuery("byTitle").setParameter(1, title).getSingleResult();
  }

  public Abenteuer findLatest() {
    List<Abenteuer> abenteuers = readAllOrderedByDate();
    return abenteuers.size() > 0 ? abenteuers.get(abenteuers.size() - 1) : null;
  }

  public List<Abenteuer> readAllOrderedByDate() {
    List<Abenteuer> abenteuers = readAll();
    Collections.sort(abenteuers);
    return abenteuers;
  }
}
