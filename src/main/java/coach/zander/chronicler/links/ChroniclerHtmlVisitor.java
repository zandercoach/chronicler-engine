package coach.zander.chronicler.links;

import java.io.Writer;

import be.devijver.wikipedia.SmartLinkResolver;
import be.devijver.wikipedia.html.HtmlVisitor;

public class ChroniclerHtmlVisitor extends HtmlVisitor {

  public ChroniclerHtmlVisitor(Writer writer, SmartLinkResolver smartLinkResolver) {
    super(writer, smartLinkResolver);
  }

  @Override
  public void endDocument() {
    flush();
    finished();
  }

  @Override
  public void handleSmartLinkWithoutCaption(String s) {
    startSmartLinkWithCaption(s);
    append(s);
    append("</a>");
  }

  @Override
  public void startDocument() {
  }

  @Override
  public void startSmartLinkWithCaption(String s) {
    String resolvedLink = resolveSmartLink(s);
    append("<a href=\"");
    append(resolvedLink);
    append("\"");
    if (resolvedLink.startsWith("http")) {
      append(" target=\"wiki-aventurica\"");
      append(" class=\"chronicler-wiki-aventurica-link\"");
    }
    append(">");
  }
}
