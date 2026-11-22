package coach.zander.chronicler.model;

import java.io.Serializable;

import coach.zander.chronicler.exception.IllegalDateException;

/** A date in an instance's calendar. Ordered by days since the start of the era; before it, negative. */
public class ChronicleDate implements Comparable<ChronicleDate>, Serializable {
  private static final long serialVersionUID = 1L;

  private final ChronicleCalendar calendar;
  private final int day;
  private final int month;
  private final int year;
  private final boolean beforeEra;
  private final int value;

  /** @param month the 1-based position of the month in the calendar */
  public ChronicleDate(ChronicleCalendar calendar, int day, int month, int year, boolean beforeEra)
      throws IllegalDateException {
    if (calendar == null) {
      throw new IllegalDateException("A date needs a calendar.");
    }
    if (month < 1 || month > calendar.getMonths().size()) {
      throw new IllegalDateException("Month must be within 1 and " + calendar.getMonths().size() + ".");
    }
    int days = calendar.month(month).getDays();
    if (day < 1 || day > days) {
      throw new IllegalDateException("Month '" + calendar.month(month).getName() + "' has days 1 to " + days + ", not "
          + day + ".");
    }
    if (year < 1) {
      throw new IllegalDateException("Year must not be less than 1.");
    }
    this.calendar = calendar;
    this.day = day;
    this.month = month;
    this.year = year;
    this.beforeEra = beforeEra;
    this.value = (beforeEra ? -1 : 1) * ((year - 1) * calendar.daysPerYear() + calendar.daysBefore(month) + day);
  }

  public int compareTo(ChronicleDate o) {
    return Integer.compare(value, o.value);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    return value == ((ChronicleDate) obj).value;
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(value);
  }

  public int getDay() {
    return day;
  }

  public ChronicleCalendar.Month getMonth() {
    return calendar.month(month);
  }

  public int getYear() {
    return year;
  }

  public boolean isBeforeEra() {
    return beforeEra;
  }

  public int getValue() {
    return value;
  }

  /** The page format, e.g. "1. Peraine 1001 BF". */
  @Override
  public String toString() {
    return calendar.display(this);
  }
}
