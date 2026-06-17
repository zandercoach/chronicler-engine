package coach.zander.cfk.links;

import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import be.devijver.wikipedia.Parser;
import be.devijver.wikipedia.SmartLinkResolver;
import be.devijver.wikipedia.Visitor;
import coach.zander.cfk.util.UrlResolver;

@Service
public class CfkReferenceInsertionEventHandler implements ReferenceInsertionEventHandler {
  private static final String TAG_DESCRIPTION = "description";
  private String[] internalReferences = new String[0];
  private String[] internalReferenceTags = new String[0];
  private final SmartLinkResolver smartLinkResolver;

  @Autowired
  public CfkReferenceInsertionEventHandler(InternalLinksProvider provider, UrlResolver resolver) {
    Assert.notNull(provider);
    Assert.notNull(resolver);
    List<InternalLink> internalLinks = provider.getInternalLinks();
    if (internalLinks != null) {
      internalReferences = new String[internalLinks.size()];
      internalReferenceTags = new String[internalLinks.size()];
      int i = 0;
      for (InternalLink internalLink : internalLinks) {
        internalReferences[i] = " " + internalLink.getName();
        internalReferenceTags[i] = " [[" + internalLink.getName() + "]]";
        i++;
      }
    }
    this.smartLinkResolver = new CfkSmartLinkResolver(internalLinks, resolver);
  }

  private String injectInternalReferenceTags(String string) {
    return StringUtils.replaceEach(string, internalReferences, internalReferenceTags);
  }

  private String parse(String contentString) {
    StringWriter writer = new StringWriter();
    Visitor visitor = new CfkHtmlVisitor(writer, smartLinkResolver);
    Parser.withVisitor(contentString, visitor);
    writer.flush();
    return writer.toString();
  }

  public Object referenceInsert(String reference, Object content) {
    String markup = "";
    if (content != null) {
      String contentString = content.toString();
      if (reference != null && reference.contains(TAG_DESCRIPTION)) {
        contentString = injectInternalReferenceTags(contentString);
      }
      markup = parse(contentString);
      if (!contentString.isEmpty() && reference != null && !reference.contains(TAG_DESCRIPTION)) {
        markup = markup.substring(markup.indexOf('>') + 1, markup.lastIndexOf('<'));
      }
    }
    return markup;
  }
}
