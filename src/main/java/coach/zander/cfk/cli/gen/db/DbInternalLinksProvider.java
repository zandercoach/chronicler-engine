package coach.zander.cfk.cli.gen.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coach.zander.cfk.dao.AbenteuerDao;
import coach.zander.cfk.dao.HeldDao;
import coach.zander.cfk.dao.KreaturDao;
import coach.zander.cfk.dao.NscDao;
import coach.zander.cfk.dao.OrtDao;
import coach.zander.cfk.links.InternalLink;
import coach.zander.cfk.links.InternalLinksProvider;
import coach.zander.cfk.model.Abenteuer;
import coach.zander.cfk.model.Held;
import coach.zander.cfk.model.Kreatur;
import coach.zander.cfk.model.Nsc;
import coach.zander.cfk.model.Ort;

@Service
public class DbInternalLinksProvider implements InternalLinksProvider {
  @Autowired
  private AbenteuerDao abenteuerDao;
  @Autowired
  private HeldDao heldDao;
  @Autowired
  private NscDao nscDao;
  @Autowired
  private KreaturDao kreaturDao;
  @Autowired
  private OrtDao ortDao;

  public List<InternalLink> getInternalLinks() {
    List<InternalLink> internalLinks = new ArrayList<InternalLink>();
    for (Abenteuer abenteuer : abenteuerDao.readAll()) {
      internalLinks.add(new InternalLink(abenteuer));
    }
    for (Held held : heldDao.readAll()) {
      internalLinks.add(new InternalLink(held));
    }
    for (Nsc nsc : nscDao.readAll()) {
      internalLinks.add(new InternalLink(nsc));
    }
    for (Ort ort : ortDao.readAll()) {
      internalLinks.add(new InternalLink(ort));
    }
    for (Kreatur kreatur : kreaturDao.readAll()) {
      internalLinks.add(new InternalLink(kreatur));
    }
    return internalLinks;
  }
}
