package coach.zander.cfk.model;

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
public abstract class CFKObject implements Comparable<CFKObject> {
	@Id
	@XmlAttribute
	@XmlID
	private String name;
	@Column(columnDefinition = "LONGTEXT")
	@XmlAttribute
	private String description;
	@XmlAttribute
	private String wikiPage;

	public int compareTo(CFKObject o) {
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
		CFKObject other = (CFKObject) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public List<Abenteuer> getAbenteuers() {
		SortedSet<Abenteuer> abenteuers = new TreeSet<Abenteuer>();
		for (Ereignis e : getEreignisse()) {
			if (e.getAbenteuer() != null) {
				abenteuers.add(e.getAbenteuer());
			}
		}
		return new ArrayList<Abenteuer>(abenteuers);
	}

	public String getDescription() {
		return description;
	}

	public String getWikiPage() {
		return wikiPage;
	}

	public abstract List<Ereignis> getEreignisse();

	public List<Held> getHelden() {
		SortedSet<Held> helden = new TreeSet<Held>();
		for (Ereignis e : getEreignisse()) {
			helden.addAll(e.getHelden());
		}
		return new ArrayList<Held>(helden);
	}

	public List<Kreatur> getKreaturen() {
		SortedSet<Kreatur> kreaturen = new TreeSet<Kreatur>();
		for (Ereignis e : getEreignisse()) {
			kreaturen.addAll(e.getKreaturen());
		}
		return new ArrayList<Kreatur>(kreaturen);
	}

	public String getName() {
		return name;
	}

	public List<Nsc> getNscs() {
		SortedSet<Nsc> nscs = new TreeSet<Nsc>();
		for (Ereignis e : getEreignisse()) {
			nscs.addAll(e.getNscs());
		}
		return new ArrayList<Nsc>(nscs);
	}

	public List<Ort> getOrte() {
		SortedSet<Ort> orte = new TreeSet<Ort>();
		Ort last = null;
		for (Ereignis e : getEreignisse()) {
			Ort o = e.getOrt();
			if (o != null && !o.equals(last)) {
				orte.add(o);
				last = o;
			}
		}
		return new ArrayList<Ort>(orte);
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
