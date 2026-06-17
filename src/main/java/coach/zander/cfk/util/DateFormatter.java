package coach.zander.cfk.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
  private static final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy");

  public String format(Date date) {
    return date == null ? "" : SDF.format(date);
  }
}
