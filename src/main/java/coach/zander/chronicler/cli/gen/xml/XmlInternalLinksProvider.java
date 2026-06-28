package coach.zander.chronicler.cli.gen.xml;

import java.util.ArrayList;
import java.util.List;

import coach.zander.chronicler.links.InternalLink;
import coach.zander.chronicler.links.InternalLinksProvider;
import coach.zander.chronicler.model.ChroniclerData;
import coach.zander.chronicler.model.Creature;
import coach.zander.chronicler.model.Endeavor;
import coach.zander.chronicler.model.Location;
import coach.zander.chronicler.model.Member;
import coach.zander.chronicler.model.Stakeholder;

public class XmlInternalLinksProvider implements InternalLinksProvider {
	private List<InternalLink> internalLinks = new ArrayList<InternalLink>();

	public XmlInternalLinksProvider(ChroniclerData data) {
		for (Endeavor endeavor : data.getEndeavors()) {
			internalLinks.add(new InternalLink(endeavor));
		}
		for (Member member : data.getMembers()) {
			internalLinks.add(new InternalLink(member));
		}
		for (Stakeholder stakeholder : data.getStakeholders()) {
			internalLinks.add(new InternalLink(stakeholder));
		}
		for (Location location : data.getLocations()) {
			internalLinks.add(new InternalLink(location));
		}
		for (Creature creature : data.getCreatures()) {
			internalLinks.add(new InternalLink(creature));
		}
	}

	public List<InternalLink> getInternalLinks() {
		return internalLinks;
	}
}
