package coach.zander.chronicler.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@MappedSuperclass
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ChroniclerObject implements Comparable<ChroniclerObject> {
	@Id
	@XmlAttribute
	@XmlID
	private String name;
	@Column(columnDefinition = "LONGTEXT")
	@XmlAttribute
	private String description;
	@XmlAttribute
	private String wikiPage;

	public int compareTo(ChroniclerObject o) {
		return name.compareTo(o.name);
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
		ChroniclerObject other = (ChroniclerObject) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public List<Endeavor> getEndeavors() {
		SortedSet<Endeavor> endeavors = new TreeSet<Endeavor>();
		for (Event event : getEvents()) {
			if (event.getEndeavor() != null) {
				endeavors.add(event.getEndeavor());
			}
		}
		return new ArrayList<Endeavor>(endeavors);
	}

	public String getDescription() {
		return description;
	}

	public String getWikiPage() {
		return wikiPage;
	}

	public abstract List<Event> getEvents();

	public List<Member> getMembers() {
		SortedSet<Member> members = new TreeSet<Member>();
		for (Event e : getEvents()) {
			members.addAll(e.getMembers());
		}
		return new ArrayList<Member>(members);
	}

	public List<Creature> getCreatures() {
		SortedSet<Creature> creatures = new TreeSet<Creature>();
		for (Event e : getEvents()) {
			creatures.addAll(e.getCreatures());
		}
		return new ArrayList<Creature>(creatures);
	}

	public String getName() {
		return name;
	}

	public List<Stakeholder> getStakeholders() {
		SortedSet<Stakeholder> stakeholders = new TreeSet<Stakeholder>();
		for (Event eereignis : getEvents()) {
			stakeholders.addAll(eereignis.getStakeholders());
		}
		return new ArrayList<Stakeholder>(stakeholders);
	}

	public List<Location> getLocations() {
		SortedSet<Location> locations = new TreeSet<Location>();
		Location last = null;
		for (Event event : getEvents()) {
			Location location = event.getLocation();
			if (location != null && !location.equals(last)) {
				locations.add(location);
				last = location;
			}
		}
		return new ArrayList<Location>(locations);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	public void setWikiPage(String wikiPage) {
		this.wikiPage = wikiPage;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("name", name).toString();
	}
}
