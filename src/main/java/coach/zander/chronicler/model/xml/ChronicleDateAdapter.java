package coach.zander.chronicler.model.xml;

import coach.zander.chronicler.model.ChronicleCalendar;
import coach.zander.chronicler.model.ChronicleDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Reads and writes dates in the data format of the instance's calendar. The generator hands one
 * with its calendar to the unmarshaller; an instance JAXB creates on its own has none and fails.
 */
public class ChronicleDateAdapter extends XmlAdapter<String, ChronicleDate> {
  private final ChronicleCalendar calendar;

  public ChronicleDateAdapter() {
    this(null);
  }

  public ChronicleDateAdapter(ChronicleCalendar calendar) {
    this.calendar = calendar;
  }

  private ChronicleCalendar calendar() {
    if (calendar == null) {
      throw new IllegalStateException(
          "No calendar: set this adapter with the instance's calendar on the unmarshaller");
    }
    return calendar;
  }

  @Override
  public String marshal(ChronicleDate date) throws Exception {
    return date == null ? null : calendar().format(date);
  }

  @Override
  public ChronicleDate unmarshal(String date) throws Exception {
    return date == null ? null : calendar().parse(date);
  }
}
