package coach.zander.chronicler.model.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import coach.zander.chronicler.model.BosparanDate;
import coach.zander.chronicler.util.SimpleBosparanDateFormat;

public class BosparanDateAdapter extends XmlAdapter<String, BosparanDate> {

  @Override
  public String marshal(BosparanDate date) throws Exception {
    return SimpleBosparanDateFormat.format(date);
  }

  @Override
  public BosparanDate unmarshal(String date) throws Exception {
    return SimpleBosparanDateFormat.parse(date);
  }
}