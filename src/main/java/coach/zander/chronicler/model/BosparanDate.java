package coach.zander.chronicler.model;

import java.io.Serializable;

import coach.zander.chronicler.exception.IllegalBosparanDateException;
import coach.zander.chronicler.util.SimpleBosparanDateFormat;

public class BosparanDate implements Comparable<BosparanDate>, Serializable {
  public enum Month {
    PRA(1, "Praios"), RON(2, "Rondra"), EFF(3, "Efferd"), TRA(4, "Travia"), BOR(5, "Boron"), HES(6, "Hesinde"), FIR(7,
        "Firun"), TSA(8, "Tsa"), PHE(9, "Phex"), PER(10, "Peraine"), ING(11, "Ingerimm"), RAH(12, "Rahja"), NAM(13,
        "Namenloser");

    public static Month valueOf(int i) {
      for (Month month : values()) {
        if (month.position == i) {
          return month;
        }
      }
      throw new IllegalArgumentException("No Bosparan month for '" + i + "'");
    }

    private final int position;

    private final String name;

    private Month(int position, String name) {
      this.position = position;
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public int getPosition() {
      return position;
    }
  }

  private static final long serialVersionUID = 1L;

  private static final int DAYS_PER_YEAR = 365;
  private static final int DAYS_PER_MONTH = 30;
  private static final int NAMENLOSER_DAYS = 5;
  private static final int MONTHS_PER_YEAR = 12;

  private final int value;

  public BosparanDate() {
    value = 1;
  }

  public BosparanDate(int day, Month month, int year) throws IllegalBosparanDateException {
    this(day, month, year, false);
  }

  public BosparanDate(int day, Month month, int year, boolean vbf) throws IllegalBosparanDateException {
    if (month == Month.NAM && day > 5) {
      throw new IllegalBosparanDateException("Bosparan Month '" + Month.NAM.name + "' only has " + NAMENLOSER_DAYS
          + " days.");
    }
    if (day < 1 || day > 30) {
      throw new IllegalBosparanDateException("Bosparan Day must be within 1 and 30.");
    }
    if (month == null) {
      throw new IllegalBosparanDateException("Bosparan Month must not be null.");
    }
    if (year < 1) {
      throw new IllegalBosparanDateException("Bosparan Year must not be less than 1.");
    }
    value = date2value(day, month, year, vbf);
  }

  public int compareTo(BosparanDate o) {
    return Integer.compare(value, o.value);
  }

  private int date2value(int day, Month month, int year, boolean vbf) {
    return (vbf ? -1 : 1)
        * ((year - 1) * (MONTHS_PER_YEAR * DAYS_PER_MONTH + NAMENLOSER_DAYS) + (month.getPosition() - 1)
            * DAYS_PER_MONTH + day);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    BosparanDate other = (BosparanDate) obj;
    if (value != other.value) {
      return false;
    }
    return true;
  }

  public int getDay() {
    return Math.abs((value - 1) % DAYS_PER_YEAR % DAYS_PER_MONTH + 1);
  }

  public Month getMonth() {
    return Month.valueOf((value - 1) % DAYS_PER_YEAR / DAYS_PER_MONTH + 1);
  }

  public int getValue() {
    return value;
  }

  public int getYear() {
    return Math.abs((value - 1) / DAYS_PER_YEAR + 1);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + value;
    return result;
  }

  public boolean isBf() {
    return value >= 0;
  }

  @Override
  public String toString() {
    return SimpleBosparanDateFormat.format(this);
  }
}
