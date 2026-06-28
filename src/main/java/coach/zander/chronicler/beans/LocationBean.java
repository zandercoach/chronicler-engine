package coach.zander.chronicler.beans;

import coach.zander.chronicler.model.ChroniclerObject;
import coach.zander.chronicler.model.Location;

public class LocationBean extends ChroniclerObjectBean {
  private Location location;

  @Override
  public ChroniclerObject getObject() {
    return getLocation();
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }
}
