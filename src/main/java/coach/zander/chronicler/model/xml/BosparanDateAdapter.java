package coach.zander.cfk.model.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import coach.zander.cfk.model.BosparanDate;
import coach.zander.cfk.util.SimpleBosparanDateFormat;

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