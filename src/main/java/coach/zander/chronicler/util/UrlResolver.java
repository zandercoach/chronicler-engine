package coach.zander.chronicler.util;

import java.net.URLEncoder;

import coach.zander.chronicler.model.ChroniclerActivity;
import coach.zander.chronicler.model.ChroniclerObject;

public abstract class UrlResolver {

	private static final String EXT_IMG_PNG = ".png";
	private static final String EXT_IMG_JPG = ".jpg";
	private static final String[] IMG_EXTENSIONS = { EXT_IMG_PNG, EXT_IMG_JPG };
	private static final String DEFAULT_IMAGE = "not_available";

	public UrlResolver() {
		super();
	}

	protected String buildBaseNameFor(String string) {
		return string == null ? null : string.toLowerCase().replace(' ', '_');
	}

	protected String getDefaultImage() {
		return DEFAULT_IMAGE;
	}

	protected String[] getImageExtensions() {
		return IMG_EXTENSIONS;
	}

	protected String getJpgImageExtension() {
		return EXT_IMG_JPG;
	}

	protected String getPngImageExtension() {
		return EXT_IMG_PNG;
	}

	public String resolveImageUrlFor(ChroniclerActivity activity) {
		return resolveImageUrlFor(activity.getTitle(), activity.getClass().getSimpleName());
	}

	public String resolveImageUrlFor(ChroniclerObject object) {
		return resolveImageUrlFor(object.getName(), object.getClass().getSimpleName());
	}

	public String resolveImageUrlFor(String string) {
		return resolveImageUrlFor(string, null);
	}

	public abstract String resolveImageUrlFor(String title, String type);

	public String resolveUrlFor(ChroniclerActivity activity) {
		return resolveUrlFor(activity.getTitle(), activity.getClass().getSimpleName());
	}

	public String resolveUrlFor(ChroniclerObject object) {
		return resolveUrlFor(object.getName(), object.getClass().getSimpleName());
	}

	public String resolveUrlFor(String string) {
		return resolveUrlFor(string, null);
	}

	public abstract String resolveUrlFor(String title, String type);

	public String resolveWikiUrlFor(String smartLink) {
		return "http://www.wiki-aventurica.de/wiki/" + URLEncoder.encode(smartLink.replace(' ', '_'));
	}
}