package coach.zander.chronicler.links;

import org.springframework.util.Assert;

import coach.zander.chronicler.model.ChroniclerActivity;
import coach.zander.chronicler.model.ChroniclerObject;

public final class InternalLink {
  private String name;
  private String type;

  public InternalLink(ChroniclerActivity activity) {
    Assert.notNull(activity);
    init(activity.getTitle(), getTypeFor(activity));
  }

  public InternalLink(ChroniclerObject object) {
    Assert.notNull(object);
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
    Assert.notNull(name);
    Assert.notNull(type);
    this.name = name;
    this.type = type;
  }
}
