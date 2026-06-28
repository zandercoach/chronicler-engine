package coach.zander.chronicler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Type;

import coach.zander.chronicler.model.xml.BosparanDateAdapter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ChroniclerActivity implements Comparable<ChroniclerActivity> {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@XmlTransient
	private Integer id;
	@XmlID
	private String title;
	@Column(name = "bdate")
	@Type(type = "coach.zander.chronicler.model.hibernate.BosparanDateUserType")
	@XmlAttribute
	@XmlJavaTypeAdapter(BosparanDateAdapter.class)
	private BosparanDate date;
	@Column(columnDefinition = "LONGTEXT")
	@XmlElement
	private String description;
	@XmlAttribute
	private String wikiPage;

	public int compareTo(ChroniclerActivity o) {
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
		ChroniclerActivity other = (ChroniclerActivity) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public BosparanDate getDate() {
		return date;
	}

	public String getDescription() {
		return description.strip();
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getWikiPage() {
		return wikiPage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		return result;
	}

	public void setDate(BosparanDate date) {
		this.date = date;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setWikiPage(String wikiPage) {
		this.wikiPage = wikiPage;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("title", title).toString();
	}
}
