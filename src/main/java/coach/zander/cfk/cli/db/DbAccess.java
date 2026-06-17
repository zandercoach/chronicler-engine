package coach.zander.cfk.cli.db;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import coach.zander.cfk.cli.util.HibernateUtil;
import coach.zander.cfk.model.Abenteuer;
import coach.zander.cfk.model.Held;
import coach.zander.cfk.model.Kreatur;
import coach.zander.cfk.model.Nsc;
import coach.zander.cfk.model.Ort;

public class DbAccess {
  private SessionFactory sessionFactory;
  private Session session;
  private Transaction transaction;

  public void close() {
    if (transaction != null && transaction.isActive()) {
      transaction.commit();
    }
    if (session != null && session.isOpen()) {
      session.close();
    }
    if (sessionFactory != null && !sessionFactory.isClosed()) {
      sessionFactory.close();
    }
  }

  public List<Abenteuer> getAbenteuerAll() {
    return getNamedQueryList("Abenteuer.all");
  }

  public Abenteuer getAbenteuerLatest() {
    List<Abenteuer> abenteuers = getAbenteuerAll();
    return abenteuers.get(0);
  }

  public List<Held> getHeldAll() {
    return getNamedQueryList("Held.all");
  }

  public List<Kreatur> getKreaturAll() {
    return getNamedQueryList("Kreatur.all");
  }

  private List getNamedQueryList(String namedQuery) {
    return getSession().getNamedQuery(namedQuery).list();
  }

  private Object getNamedQueryUnique(String namedQuery) {
    return getSession().getNamedQuery(namedQuery).uniqueResult();
  }

  public List<Nsc> getNscAll() {
    return getNamedQueryList("Nsc.all");
  }

  public List<Ort> getOrtAll() {
    return getNamedQueryList("Ort.all");
  }

  public Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  public List<coach.zander.cfk.model.Session> getSessionAll() {
    return getNamedQueryList("Session.all");
  }

  public void open() throws IOException {
    open(null);
  }

  public void open(String properties) throws IOException {
    sessionFactory = HibernateUtil.buildSessionFactory(properties);
    session = getSession();
    transaction = session.beginTransaction();
  }

  public void save(Object o) {
    session.save(o);
  }
}
