package coach.zander.chronicler.cli.gen.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coach.zander.chronicler.dao.CreatureDao;
import coach.zander.chronicler.dao.EndeavorDao;
import coach.zander.chronicler.dao.LocationDao;
import coach.zander.chronicler.dao.MemberDao;
import coach.zander.chronicler.dao.StakeholderDao;
import coach.zander.chronicler.links.InternalLink;
import coach.zander.chronicler.links.InternalLinksProvider;
import coach.zander.chronicler.model.Creature;
import coach.zander.chronicler.model.Endeavor;
import coach.zander.chronicler.model.Location;
import coach.zander.chronicler.model.Member;
import coach.zander.chronicler.model.Stakeholder;

@Service
public class DbInternalLinksProvider implements InternalLinksProvider {
  @Autowired
  private EndeavorDao endeavorDao;
  @Autowired
  private MemberDao memberDao;
  @Autowired
  private StakeholderDao stakeholderDao;
  @Autowired
  private CreatureDao creatureDao;
  @Autowired
  private LocationDao locationDao;

  public List<InternalLink> getInternalLinks() {
    List<InternalLink> internalLinks = new ArrayList<InternalLink>();
    for (Endeavor endeavor : endeavorDao.readAll()) {
      internalLinks.add(new InternalLink(endeavor));
    }
    for (Member member : memberDao.readAll()) {
      internalLinks.add(new InternalLink(member));
    }
    for (Stakeholder stakeholder : stakeholderDao.readAll()) {
      internalLinks.add(new InternalLink(stakeholder));
    }
    for (Location location : locationDao.readAll()) {
      internalLinks.add(new InternalLink(location));
    }
    for (Creature creature : creatureDao.readAll()) {
      internalLinks.add(new InternalLink(creature));
    }
    return internalLinks;
  }
}
