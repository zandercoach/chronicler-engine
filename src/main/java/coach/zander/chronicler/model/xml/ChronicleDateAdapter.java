package coach.zander.chronicler.model.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import coach.zander.chronicler.model.ChronicleCalendar;
import coach.zander.chronicler.model.ChronicleDate;

/**
 * Reads and writes dates in the data format of the instance's calendar. The XML generator hands one
 * with its calendar to the unmarshaller; JAXB's own instance falls back to the current calendar.
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
    return calendar != null ? calendar : ChronicleCalendar.current();
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
