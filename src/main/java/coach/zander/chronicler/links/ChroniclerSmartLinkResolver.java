package coach.zander.cfk.links;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import be.devijver.wikipedia.SmartLinkResolver;
import coach.zander.cfk.util.UrlResolver;

public class CfkSmartLinkResolver implements SmartLinkResolver {
	private final Map<String, InternalLink> internalLinks;
	private final UrlResolver resolver;

	public CfkSmartLinkResolver(List<InternalLink> internalLinks, UrlResolver resolver) {
		Assert.notNull(resolver);
		this.internalLinks = new HashMap<String, InternalLink>();
		if (internalLinks != null) {
			for (InternalLink link : internalLinks) {
				this.internalLinks.put(link.getName(), link);
			}
		}
		this.resolver = resolver;
	}

	private boolean isInternal(String smartLink) {
		return internalLinks.containsKey(smartLink);
	}

	public String resolve(String smartLink) {
		if (isInternal(smartLink)) {
			return resolver.resolveUrlFor(smartLink, internalLinks.get(smartLink).getType());
		} else {
			return resolver.resolveWikiUrlFor(smartLink);
		}
	}
}
