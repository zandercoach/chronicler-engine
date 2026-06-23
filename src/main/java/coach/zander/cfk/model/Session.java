package coach.zander.cfk.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import coach.zander.cfk.model.xml.DateAdapter;

@NamedQueries({ @NamedQuery(name = "Session.all", query = "SELECT s FROM Session s ORDER BY s.id") })
@Entity
public class Session implements Comparable<Session> {
  @Id
  @GeneratedValue
  private Integer id;
  @Column(name = "sdate")
  @XmlAttribute
  @XmlJavaTypeAdapter(DateAdapter.class)
  @XmlID
  private Date date;
  @XmlAttribute
  private String location;
  @ManyToMany(mappedBy = "sessions")
  @OrderBy("id")
  private final List<Adventure> adventures = new ArrayList<Adventure>();

  public void addAdventure(Adventure adventure) {
    adventures.add(adventure);
  }

  public int compareTo(Session o) {
    return date.compareTo(o.date);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Session other = (Session) obj;
    if (date == null) {
      if (other.date != null) {
        return false;
      }
    } else if (!date.equals(other.date)) {
      return false;
    }
    return true;
  }

  public List<Adventure> getAdventures() {
    return adventures;
  }

  public Date getDate() {
    return date;
  }

  public Integer getId() {
    return id;
  }

  public String getLocation() {
    return location;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (date == null ? 0 : date.hashCode());
    result = prime * result + (id == null ? 0 : id.hashCode());
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("date",
        new SimpleDateFormat("yyyy-MM-dd").format(date)).toString();
  }
}
