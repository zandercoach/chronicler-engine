package coach.zander.chronicler.links;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import be.devijver.wikipedia.SmartLinkResolver;
import coach.zander.chronicler.util.UrlResolver;

public class ChroniclerSmartLinkResolver implements SmartLinkResolver {
	private final Map<String, InternalLink> internalLinks;
	private final UrlResolver resolver;

	public ChroniclerSmartLinkResolver(List<InternalLink> internalLinks, UrlResolver resolver) {
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
