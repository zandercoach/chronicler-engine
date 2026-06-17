package be.devijver.wikipedia.html;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.lang.StringEscapeUtils;

import be.devijver.wikipedia.SmartLinkResolver;
import be.devijver.wikipedia.Visitor;
import be.devijver.wikipedia.parser.ast.Attribute;
import be.devijver.wikipedia.parser.ast.AttributeList;

public class HtmlVisitor implements Visitor {

	private class Output {

		private Writer writer;
		private final boolean flush;

		private Output(Writer writer, boolean flush) {
			this.writer = writer;
			this.flush = flush;
		}

		private void append(String s) {
			try {
				writer.append(s);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void finished() {
			writer = null;
		}

		private void flush() {
			if (!flush) {
				return;
			}
			try {
				writer.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static class SimpleCharacterEncoder implements HtmlEncoder {

		public String encode(String s) {
			String result = StringEscapeUtils.unescapeHtml(s);
			return StringEscapeUtils.escapeHtml(result);
		}

	}

	protected final Output output;
	protected final SmartLinkResolver smartLinkResolver;
	protected final HtmlEncoder characterEncoder;

	public HtmlVisitor(Writer writer, SmartLinkResolver smartLinkResolver) {
		this(writer, smartLinkResolver, false);
	}

	public HtmlVisitor(Writer writer, SmartLinkResolver smartLinkResolver, boolean flush) {
		this(writer, smartLinkResolver, new SimpleCharacterEncoder(), flush);
	}

	public HtmlVisitor(Writer writer, SmartLinkResolver smartLinkResolver, HtmlEncoder characterEncoder,
			boolean flush) {
		this.output = new Output(writer, flush);
		this.smartLinkResolver = smartLinkResolver;
		this.characterEncoder = characterEncoder;
	}

	public void append(String s) {
		output.append(s);
	}

	public void endBold() {
		output.append("</b>");
	}

	public void endCaption() {
		output.append("</caption>");
	}

	public void endDocument() {
		output.append("</div>");
		output.flush();
		output.finished();
	}

	public void endHeading1() {
		output.append("</h1>");
		output.flush();
	}

	public void endHeading2() {
		output.append("</h2>");
		output.flush();
	}

	public void endHeading3() {
		output.append("</h3>");
		output.flush();
	}

	public void endHeading4() {
		output.append("</h4>");
		output.flush();
	}

	public void endHeading5() {
		output.append("</h5>");
		output.flush();
	}

	public void endHeading6() {
		output.append("</h6>");
		output.flush();
	}

	public void endIndent() {
		output.append("</blockquote>");
		output.flush();
	}

	public void endItalics() {
		output.append("</i>");
	}

	public void endLiteral() {
		output.append("</pre>");
		output.flush();
	}

	public void endNormalLinkWithCaption() {
		output.append("</a>");
	}

	public void endOfLiteralLine() {
		output.append("\n");
	}

	public void endOrderedList() {
		output.append("</ol>");
		output.flush();
	}

	public void endOrderedListItem() {
		output.append("</li>");
	}

	public void endParagraph() {
		output.append("</p>");
		output.flush();
	}

	public void endPre() {
		output.append("</pre>");
	}

	public void endSmartLinkWithCaption() {
		output.append("</a>");
	}

	public void endTable() {
		output.append("</table>");
		output.flush();
	}

	public void endTableData() {
		output.append("</td>");
	}

	public void endTableHeader() {
		output.append("</th>");
	}

	public void endTableRecord() {
		output.append("</tr>");
		output.flush();
	}

	public void endUnorderedList() {
		output.append("</ul>");
		output.flush();
	}

	public void endUnorderedListItem() {
		output.append("</li>");
	}

	public void finished() {
		output.finished();
	}

	public void flush() {
		output.flush();
	}

	private void handleAttributeList(AttributeList tableOptions) {
		if (tableOptions != null) {
			Attribute[] attributes = tableOptions.getAttributes();
			for (Attribute attribute : attributes) {
				output.append(" ");
				output.append(attribute.getName());
				output.append("=\"");
				output.append(attribute.getValue() != null ? attribute.getValue() : "");
				output.append("\"");
			}
		}
	}

	public void handleNormalLinkWithoutCaption(String string) {
		output.append("<a href=\"" + string + "\">" + string + "</a>");
	}

	public void handleNowiki(String nowiki) {
		output.append(characterEncoder.encode(nowiki));
	}

	public void handleSmartLinkWithoutCaption(String string) {
		String resolvedLink = resolveSmartLink(string);
		output.append("<a href=\"" + resolvedLink + "\">" + string + "</a>");
	}

	public void handleString(String s) {
		output.append(characterEncoder.encode(s));
	}

	protected String resolveSmartLink(String s) {
		String resolvedLink = "";
		if (smartLinkResolver != null) {
			resolvedLink = smartLinkResolver.resolve(s);
		} else {
			throw new RuntimeException("Could not resolve smart link [" + s + "] because SmartLinkResolver is null!");
		}
		if (resolvedLink == null) {
			throw new RuntimeException(
					"SmartLinkResolver [" + smartLinkResolver + "] could not resolve smart link [" + s + "]!");
		}
		return resolvedLink;
	}

	public void startBold() {
		output.append("<b>");
	}

	public void startCaption(AttributeList captionOptions) {
		output.append("<caption");
		handleAttributeList(captionOptions);
		output.append(">");
	}

	public void startDocument() {
		output.append("<div>");
	}

	public void startHeading1() {
		output.append("<h1>");
	}

	public void startHeading2() {
		output.append("<h2>");
	}

	public void startHeading3() {
		output.append("<h3>");
	}

	public void startHeading4() {
		output.append("<h4>");
	}

	public void startHeading5() {
		output.append("<h5>");
	}

	public void startHeading6() {
		output.append("<h6>");
	}

	public void startIndent() {
		output.append("<blockquote>");
	}

	public void startItalics() {
		output.append("<i>");
	}

	public void startLiteral() {
		output.append("<pre>\n");
	}

	public void startNormalLinkWithCaption(String s) {
		output.append("<a href=\"" + s + "\">");
	}

	public void startOrderedList() {
		output.append("<ol>");
	}

	public void startOrderedListItem() {
		output.append("<li>");
	}

	public void startParagraph() {
		output.append("<p>");
	}

	public void startPre() {
		output.append("<pre>");
	}

	public void startSmartLinkWithCaption(String s) {
		String resolvedLink = resolveSmartLink(s);
		output.append("<a href=\"" + resolvedLink + "\">");
	}

	public void startTable(AttributeList tableOptions) {
		output.append("<table");
		handleAttributeList(tableOptions);
		output.append(">");
	}

	public void startTableData(AttributeList options) {
		output.append("<td");
		handleAttributeList(options);
		output.append(">");
	}

	public void startTableHeader(AttributeList list) {
		output.append("<th");
		handleAttributeList(list);
		output.append(">");
	}

	public void startTableRecord(AttributeList rowOptions) {
		output.append("<tr");
		handleAttributeList(rowOptions);
		output.append(">");
	}

	public void startUnorderedList() {
		output.append("<ul>");
	}

	public void startUnorderedListItem() {
		output.append("<li>");
	}
}
