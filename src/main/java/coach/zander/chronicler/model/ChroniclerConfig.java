package coach.zander.chronicler.model;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/** What makes one chronicle instance differ from another: read from {@code data/chronicler.xml}. */
@XmlRootElement(name = "chronicler")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChroniclerConfig {

  @XmlAccessorType(XmlAccessType.FIELD)
  public static class Wiki {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String url;

    public String getName() {
      return name;
    }

    public String getUrl() {
      return url;
    }
  }

  /** Where the instance's imprint and privacy notice live; linked from every page's footer. */
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class Legal {
    @XmlAttribute
    private String imprint;
    @XmlAttribute
    private String privacy;

    public String getImprint() {
      return imprint;
    }

    public String getPrivacy() {
      return privacy;
    }
  }

  @XmlElement
  private String title;
  @XmlElement
  private String welcome;
  @XmlElement
  private String copyright;
  @XmlElement
  private Wiki wiki;
  @XmlElement
  private Legal legal;

  public static ChroniclerConfig load(File file) throws JAXBException {
    if (!file.isFile()) {
      throw new IllegalStateException("Instance configuration missing: " + file.getPath());
    }
    return (ChroniclerConfig) JAXBContext.newInstance(ChroniclerConfig.class).createUnmarshaller().unmarshal(file);
  }

  public String getTitle() {
    return title;
  }

  public String getWelcome() {
    return welcome;
  }

  public String getCopyright() {
    return copyright;
  }

  public Wiki getWiki() {
    return wiki;
  }

  public Legal getLegal() {
    return legal;
  }
}
