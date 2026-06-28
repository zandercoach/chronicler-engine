package coach.zander.chronicler.util;

import coach.zander.chronicler.exception.IllegalBosparanDateException;
import coach.zander.chronicler.model.BosparanDate;

public final class SimpleBosparanDateFormat {

  public static String format(BosparanDate date) {
    StringBuilder b = new StringBuilder();
    b.append(date.getDay());
    b.append('.');
    b.append(date.getMonth());
    b.append(' ');
    b.append(date.getYear());
    b.append(' ');
    if (!date.isBf()) {
      b.append('v');
    }
    b.append("BF");
    return b.toString();
  }

  public static BosparanDate parse(String date) throws IllegalBosparanDateException {
    int pointIdx = date.indexOf('.');
    int spaceIdx = date.indexOf(' ');
    int space2Idx = spaceIdx + 1 + date.substring(spaceIdx + 1).indexOf(' ');
    int vIdx = date.indexOf('v');
    Integer day = Integer.parseInt(date.substring(0, pointIdx));
    BosparanDate.Month month = BosparanDate.Month.valueOf(date.substring(pointIdx + 1, spaceIdx));
    Boolean vbf = vIdx > 0;
    Integer year = Integer.parseInt(date.substring(spaceIdx + 1, space2Idx));

    return new BosparanDate(day, month, year, vbf);
  }

  private SimpleBosparanDateFormat() {
    // static class with private constructor
  }
}
