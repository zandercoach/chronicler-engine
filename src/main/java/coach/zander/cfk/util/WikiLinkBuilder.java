package coach.zander.cfk.util;

import coach.zander.cfk.model.CFKActivity;
import coach.zander.cfk.model.CFKObject;

public class WikiLinkBuilder {
  public String buildFreeLinkFor(CFKActivity activity) {
    return buildFreeLinkFor(activity == null ? null : activity.getTitle());
  }

  public String buildFreeLinkFor(CFKObject object) {
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
