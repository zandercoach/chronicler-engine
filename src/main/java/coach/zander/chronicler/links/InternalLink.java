package coach.zander.chronicler.links;

import coach.zander.chronicler.model.ChroniclerActivity;
import coach.zander.chronicler.model.ChroniclerObject;
import java.util.Objects;

public final class InternalLink {
  private String name;
  private String type;

  public InternalLink(ChroniclerActivity activity) {
    Objects.requireNonNull(activity, "activity must not be null");
    init(activity.getTitle(), getTypeFor(activity));
  }

  public InternalLink(ChroniclerObject object) {
    Objects.requireNonNull(object, "object must not be null");
    init(object.getName(), getTypeFor(object));
  }

  public InternalLink(String name, String type) {
    init(name, type);
  }

  protected String getName() {
    return name;
  }

  protected String getType() {
    return type;
  }

  private String getTypeFor(Object object) {
    return object.getClass().getSimpleName().toLowerCase();
  }

  private void init(String name, String type) {
    Objects.requireNonNull(name, "name must not be null");
    Objects.requireNonNull(type, "type must not be null");
    this.name = name;
    this.type = type;
  }
}
