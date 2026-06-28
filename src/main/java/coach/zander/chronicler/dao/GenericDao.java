package coach.zander.cfk.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class GenericDao<T, PK extends Serializable> {
  @PersistenceContext
  protected EntityManager em;

  private final Class<T> type;

  public GenericDao() {
    Type t = getClass().getGenericSuperclass();
    ParameterizedType pt = (ParameterizedType) t;
    type = (Class) pt.getActualTypeArguments()[0];
  }

  public T create(final T t) {
    this.em.persist(t);
    return t;
  }

  public void delete(final Object id) {
    this.em.remove(this.em.getReference(type, id));
  }

  public T find(final Object id) {
    return this.em.find(type, id);
  }

  protected Query getQuery(String queryName) {
    return em.createNamedQuery(type.getSimpleName() + "." + queryName);
  }

  public List<T> readAll() {
    return readManyByNamedQuery("all");
  }

  public List<T> readManyByNamedQuery(String queryName) {
    return getQuery(queryName).getResultList();
  }

  public T readUniqueByNamedQuery(String queryName) {
    return (T) getQuery(queryName).getSingleResult();
  }

  public T update(final T t) {
    return this.em.merge(t);
  }
}