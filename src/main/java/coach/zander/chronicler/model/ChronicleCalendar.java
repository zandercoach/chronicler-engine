package coach.zander.chronicler.model;

import coach.zander.chronicler.exception.IllegalDateException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An instance's calendar, configured in {@code chronicler.xml}: its months in order, each with an
 * abbreviation, a name and a number of days, and the era written after the year. Data writes a date
 * as {@code D.ABBR YEAR ERA} ({@code 1.PER 1001 BF}); pages show {@code D. Name YEAR ERA} ({@code
 * 1. Peraine 1001 BF}).
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ChronicleCalendar implements Serializable {
  private static final long serialVersionUID = 1L;

  @XmlAccessorType(XmlAccessType.FIELD)
  public static class Month implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlAttribute private String abbr;
    @XmlAttribute private String name;
    @XmlAttribute private int days;

    public Month() {}

    public Month(String abbr, String name, int days) {
      this.abbr = abbr;
      this.name = name;
      this.days = days;
    }

    public String getAbbr() {
      return abbr;
    }

    public String getName() {
      return name;
    }

    public int getDays() {
      return days;
    }

    @Override
    public String toString() {
      return abbr;
    }
  }

  /** Written after the year, e.g. "BF" or "n. F.". */
  @XmlAttribute private String era;

  /** Written after the year for dates before the era, e.g. "vBF"; optional. */
  @XmlAttribute private String before;

  @XmlElement(name = "month")
  private List<Month> months = new ArrayList<Month>();

  public ChronicleCalendar() {}

  public ChronicleCalendar(String era, String before, List<Month> months) {
    this.era = era;
    this.before = before;
    this.months = new ArrayList<Month>(months);
  }

  public String getEra() {
    return era;
  }

  public String getBefore() {
    return before;
  }

  public List<Month> getMonths() {
    return months;
  }

  /** The month at a 1-based position. */
  public Month month(int position) {
    return months.get(position - 1);
  }

  int daysPerYear() {
    int sum = 0;
    for (Month month : months) {
      sum += month.days;
    }
    return sum;
  }

  /** Days in the year before the month at a 1-based position. */
  int daysBefore(int position) {
    int sum = 0;
    for (int i = 0; i < position - 1; i++) {
      sum += months.get(i).days;
    }
    return sum;
  }

  /**
   * Reads the data format {@code D.ABBR YEAR ERA}; the era is the rest after the year and may hold
   * spaces.
   */
  public ChronicleDate parse(String text) throws IllegalDateException {
    String s = text == null ? "" : text.trim();
    try {
      int dot = s.indexOf('.');
      int space = s.indexOf(' ', dot + 1);
      int space2 = s.indexOf(' ', space + 1);
      int day = Integer.parseInt(s.substring(0, dot).trim());
      String abbr = s.substring(dot + 1, space).trim();
      int year = Integer.parseInt(s.substring(space + 1, space2).trim());
      String token = s.substring(space2 + 1).trim();
      boolean beforeEra;
      if (token.equals(era)) {
        beforeEra = false;
      } else if (before != null && token.equals(before)) {
        beforeEra = true;
      } else {
        throw new IllegalDateException("Unknown era '" + token + "' in '" + text + "'");
      }
      return new ChronicleDate(this, day, positionOf(abbr, text), year, beforeEra);
    } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
      throw new IllegalDateException("Not a date in the form 'D.ABBR YEAR ERA': '" + text + "'");
    }
  }

  private int positionOf(String abbr, String text) throws IllegalDateException {
    for (int i = 0; i < months.size(); i++) {
      if (months.get(i).abbr.equals(abbr)) {
        return i + 1;
      }
    }
    throw new IllegalDateException("Unknown month '" + abbr + "' in '" + text + "'");
  }

  /** The data format, {@code 1.PER 1001 BF}. */
  public String format(ChronicleDate date) {
    return date.getDay() + "." + date.getMonth().abbr + " " + date.getYear() + " " + eraOf(date);
  }

  /** The page format, {@code 1. Peraine 1001 BF}. */
  public String display(ChronicleDate date) {
    return date.getDay() + ". " + date.getMonth().name + " " + date.getYear() + " " + eraOf(date);
  }

  private String eraOf(ChronicleDate date) {
    return date.isBeforeEra() ? before : era;
  }
}
