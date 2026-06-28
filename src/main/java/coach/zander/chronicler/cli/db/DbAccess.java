package coach.zander.chronicler.cli.db;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import coach.zander.chronicler.cli.util.HibernateUtil;
import coach.zander.chronicler.model.Creature;
import coach.zander.chronicler.model.Endeavor;
import coach.zander.chronicler.model.Location;
import coach.zander.chronicler.model.Member;
import coach.zander.chronicler.model.Stakeholder;

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

  public List<Endeavor> getEndeavorAll() {
    return getNamedQueryList("Endeavor.all");
  }

  public Endeavor getEndeavorLatest() {
    List<Endeavor> endeavors = getEndeavorAll();
    return endeavors.get(0);
  }

  public List<Member> getMemberAll() {
    return getNamedQueryList("Member.all");
  }

  public List<Creature> getCreatureAll() {
    return getNamedQueryList("Creature.all");
  }

  private List getNamedQueryList(String namedQuery) {
    return getSession().getNamedQuery(namedQuery).list();
  }

  private Object getNamedQueryUnique(String namedQuery) {
    return getSession().getNamedQuery(namedQuery).uniqueResult();
  }

  public List<Stakeholder> getStakeholderAll() {
    return getNamedQueryList("Stakeholder.all");
  }

  public List<Location> getLocationAll() {
    return getNamedQueryList("Location.all");
  }

  public Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  public List<coach.zander.chronicler.model.Session> getSessionAll() {
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
