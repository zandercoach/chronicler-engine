package coach.zander.cfk.cli.gen;

import java.io.File;

import coach.zander.cfk.util.UrlResolver;

public class StaticUrlResolver extends UrlResolver {
  private final String imagePath;

  public StaticUrlResolver(String imagePath) {
    this.imagePath = imagePath;
  }

  private String encode(String string) {
    return string.replace(' ', '_');
  }

  @Override
  public String resolveImageUrlFor(String string, String type) {
    String basename = buildBaseNameFor(string);
    String resource = imagePath + "/" + (type == null ? "" : type.toLowerCase() + "/") + basename;
    for (String extension : getImageExtensions()) {
      String filename = resource + extension;

      if (new File(filename).exists()) {
        return "../img/" + (type == null ? "" : type.toLowerCase() + "/") + basename + extension;
      }
    }
    String url = "../img/" + (type == null ? getDefaultImage() : type.toLowerCase() + "/default") + getJpgImageExtension();
    return url;
  }

  @Override
  public String resolveUrlFor(String name, String type) {
    String url = (type == null ? "" : type.toLowerCase() + "_") + encode(name) + ".html";
    return url;
  }
}
