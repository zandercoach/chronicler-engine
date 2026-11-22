package coach.zander.chronicler.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import coach.zander.chronicler.model.ChroniclerConfig;
import coach.zander.chronicler.model.Session;

/** Instance-wide values every page needs: the configuration plus the years the sessions span. */
public class SiteBean {
  private final ChroniclerConfig config;
  private final String years;

  public SiteBean(ChroniclerConfig config, List<Session> sessions) {
    this.config = config;
    this.years = yearsOf(sessions);
  }

  private static String yearsOf(List<Session> sessions) {
    Integer first = null;
    Integer last = null;
    if (sessions != null) {
      for (Session session : sessions) {
        int year = yearOf(session.getDate());
        first = first == null ? year : Math.min(first, year);
        last = last == null ? year : Math.max(last, year);
      }
    }
    if (first == null) {
      return "";
    }
    return first.equals(last) ? String.valueOf(first) : first + " - " + last;
  }

  private static int yearOf(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.YEAR);
  }

  public String getTitle() {
    return config.getTitle();
  }

  public String getWelcome() {
    return config.getWelcome();
  }

  public String getCopyright() {
    return config.getCopyright();
  }

  public String getWikiName() {
    return config.getWiki() == null ? null : config.getWiki().getName();
  }

  /** The instance's icon for wiki links, a file in its img/, or null for the engine's own. */
  public String getWikiIcon() {
    return config.getWiki() == null ? null : config.getWiki().getIcon();
  }

  /** "2011 - 2025", or a single year when all sessions fall into one. */
  public String getYears() {
    return years;
  }

  /** Whether the map page loads Google Maps; only when the instance enables it. */
  public boolean isMapEnabled() {
    return config.getMap() != null && config.getMap().isEnabled();
  }

  /** The text the map page shows instead of the map, or null. */
  public String getMapNote() {
    return config.getMap() == null ? null : config.getMap().getNote();
  }

  /** URL of the imprint, or null when the instance configures none. */
  public String getImprint() {
    return config.getLegal() == null ? null : config.getLegal().getImprint();
  }

  /** URL of the privacy notice, or null when the instance configures none. */
  public String getPrivacy() {
    return config.getLegal() == null ? null : config.getLegal().getPrivacy();
  }
}
