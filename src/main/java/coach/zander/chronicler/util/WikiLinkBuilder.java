package coach.zander.chronicler.util;

import coach.zander.chronicler.model.ChroniclerActivity;
import coach.zander.chronicler.model.ChroniclerObject;

public class WikiLinkBuilder {
  public String buildFreeLinkFor(ChroniclerActivity activity) {
    return buildFreeLinkFor(activity == null ? null : activity.getTitle());
  }

  public String buildFreeLinkFor(ChroniclerObject object) {
    return buildFreeLinkFor(object == null ? null : object.getName());
  }

  public String buildFreeLinkFor(String string) {
    if (string == null || string.isEmpty()) {
      return "";
    }
    StringBuilder builder = new StringBuilder();
    builder.append("[[");
    builder.append(string);
    builder.append("]]");
    return builder.toString();
  }
}
