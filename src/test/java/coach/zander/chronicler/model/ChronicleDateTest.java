package coach.zander.chronicler.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coach.zander.chronicler.exception.IllegalDateException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ChronicleDateTest {

  private static ChronicleCalendar aventurian() {
    String[][] months = {
      {"PRA", "Praios"},
      {"RON", "Rondra"},
      {"EFF", "Efferd"},
      {"TRA", "Travia"},
      {"BOR", "Boron"},
      {"HES", "Hesinde"},
      {"FIR", "Firun"},
      {"TSA", "Tsa"},
      {"PHE", "Phex"},
      {"PER", "Peraine"},
      {"ING", "Ingerimm"},
      {"RAH", "Rahja"}
    };
    List<ChronicleCalendar.Month> list = new ArrayList<>();
    for (String[] m : months) {
      list.add(new ChronicleCalendar.Month(m[0], m[1], 30));
    }
    list.add(new ChronicleCalendar.Month("NAM", "Namenloser Tag", 5));
    return new ChronicleCalendar("BF", "vBF", list);
  }

  private static ChronicleCalendar flood() {
    List<ChronicleCalendar.Month> list = new ArrayList<>();
    list.add(new ChronicleCalendar.Month("NEB", "Nebelmond", 30));
    list.add(new ChronicleCalendar.Month("FLU", "Flutnacht", 5));
    return new ChronicleCalendar("n. F.", null, list);
  }

  @Test
  void readsTheDataFormatAndShowsTheFullMonthName() throws IllegalDateException {
    ChronicleDate date = aventurian().parse("12.RAH 1003 BF");
    assertThat(date.getDay()).isEqualTo(12);
    assertThat(date.getMonth().getAbbr()).isEqualTo("RAH");
    assertThat(date.getYear()).isEqualTo(1003);
    assertThat(date.isBeforeEra()).isFalse();
    assertThat(date).hasToString("12. Rahja 1003 BF");
    assertThat(aventurian().format(date)).isEqualTo("12.RAH 1003 BF");
  }

  @Test
  void anEraMayHoldSpaces() throws IllegalDateException {
    ChronicleDate date = flood().parse("3.FLU 1003 n. F.");
    assertThat(date).hasToString("3. Flutnacht 1003 n. F.");
    assertThat(flood().format(date)).isEqualTo("3.FLU 1003 n. F.");
  }

  @Test
  void ordersByDayAcrossMonthsYearsAndTheEra() throws IllegalDateException {
    ChronicleCalendar c = aventurian();
    assertThat(c.parse("30.RAH 1003 BF")).isLessThan(c.parse("1.NAM 1003 BF"));
    assertThat(c.parse("5.NAM 1003 BF")).isLessThan(c.parse("1.PRA 1004 BF"));
    assertThat(c.parse("1.PRA 1 vBF")).isLessThan(c.parse("1.PRA 1 BF"));
    assertThat(c.parse("1.PRA 2 vBF")).isLessThan(c.parse("1.PRA 1 vBF"));
    assertThat(c.parse("1.PRA 1 vBF")).hasToString("1. Praios 1 vBF");
  }

  @Test
  void refusesWhatTheCalendarDoesNotHave() {
    assertThatThrownBy(() -> aventurian().parse("6.NAM 1003 BF"))
        .isInstanceOf(IllegalDateException.class)
        .hasMessageContaining("1 to 5");
    assertThatThrownBy(() -> aventurian().parse("1.NEB 1003 BF"))
        .isInstanceOf(IllegalDateException.class)
        .hasMessageContaining("Unknown month");
    assertThatThrownBy(() -> aventurian().parse("1.PRA 1003 n. F."))
        .isInstanceOf(IllegalDateException.class)
        .hasMessageContaining("Unknown era");
    assertThatThrownBy(() -> flood().parse("1.NEB 1003 v. F."))
        .isInstanceOf(IllegalDateException.class)
        .hasMessageContaining("Unknown era");
    assertThatThrownBy(() -> aventurian().parse("PRA 1003"))
        .isInstanceOf(IllegalDateException.class);
  }
}
