package coach.zander.chronicler.model;

import coach.zander.chronicler.model.xml.DateAdapter;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Session implements Comparable<Session> {
  private Integer id;

  @XmlAttribute
  @XmlJavaTypeAdapter(DateAdapter.class)
  @XmlID
  private Date date;

  @XmlAttribute private String location;
  private final List<Endeavor> endeavors = new ArrayList<Endeavor>();

  public void addEndeavor(Endeavor endeavor) {
    endeavors.add(endeavor);
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

  public List<Endeavor> getEndeavors() {
    return endeavors;
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
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("date", new SimpleDateFormat("yyyy-MM-dd").format(date))
        .toString();
  }
}
