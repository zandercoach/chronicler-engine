package coach.zander.cfk.cli.gen.xml;

import java.util.ArrayList;
import java.util.List;

import coach.zander.cfk.links.InternalLink;
import coach.zander.cfk.links.InternalLinksProvider;
import coach.zander.cfk.model.Abenteuer;
import coach.zander.cfk.model.CFKData;
import coach.zander.cfk.model.Held;
import coach.zander.cfk.model.Kreatur;
import coach.zander.cfk.model.Nsc;
import coach.zander.cfk.model.Ort;

public class XmlInternalLinksProvider implements InternalLinksProvider {
	private List<InternalLink> internalLinks = new ArrayList<InternalLink>();

	public XmlInternalLinksProvider(CFKData data) {
		for (Abenteuer abenteuer : data.getAbenteuers()) {
			internalLinks.add(new InternalLink(abenteuer));
		}
		for (Held held : data.getHelden()) {
			internalLinks.add(new InternalLink(held));
		}
		for (Nsc nsc : data.getNscs()) {
			internalLinks.add(new InternalLink(nsc));
		}
		for (Ort ort : data.getOrte()) {
			internalLinks.add(new InternalLink(ort));
		}
		for (Kreatur kreatur : data.getKreaturen()) {
			internalLinks.add(new InternalLink(kreatur));
		}
	}

	public List<InternalLink> getInternalLinks() {
		return internalLinks;
	}
}
