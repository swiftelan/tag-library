package com.swiftelan.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.swiftelan.tag.util.EscapeUtil;

public class ComponentTagSupport extends SimpleTagSupport implements DynamicAttributes {
	private Map<String, String> attributes = new HashMap<>();

	public void setId(String id) {
		attributes.put("id", id);
	}

	public String getId() {
		return attributes.get("id");
	}

	public void setCssClass(String cssClass) {
		attributes.put("class", cssClass);
	}

	public String getCssClass() {
		return attributes.get("class");
	}

	protected void start(String element, Map<String, String> attributes, Writer writer) throws IOException {
		writer.append("<");
		writer.append(element);
		writeAttributes(writer, attributes);
		writer.append(">");
	}

	protected void start(String element, Map<String, String> attributes) throws IOException {
		start(element, attributes, getJspContext().getOut());
	}

	protected void end(String element, Writer writer) throws IOException {
		writer.append("</");
		writer.append(element);
		writer.append(">");
	}

	protected void end(String element) throws IOException {
		end(element, getJspContext().getOut());
	}

	protected void writeAttributes(Writer writer, Map<String, String> attributes) throws IOException {
		if (attributes == null) {
			return;
		}
		for (Entry<String, String> attr : attributes.entrySet()) {
			String value = attr.getValue();
			if (value == null) {
				continue;
			}
			writer.append(" ");
			writer.append(attr.getKey());
			writer.append("=\"");
			writer.append(EscapeUtil.escape(value));
			writer.append("\"");
		}
	}

	protected void characters(String value, boolean escape, Writer writer) throws IOException {
		writer.append(escape ? EscapeUtil.escape(value) : value);
	}

	protected void characters(String value, boolean escape) throws IOException {
		characters(value, escape, getJspContext().getOut());
	}

	protected void characters(String value) throws IOException {
		characters(value, true, getJspContext().getOut());
	}

	protected Map<String, String> getAttributes() {
		return attributes;
	}

	@Override
	public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
		attributes.put(localName, String.valueOf(value));
	}

	@SuppressWarnings("unchecked")
	protected static <T> T findAncestorTag(JspTag from, Class<T> klass) {
		return (T) findAncestorWithClass(from, klass);
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) getJspContext().getAttribute(PageContext.REQUEST, PageContext.REQUEST_SCOPE);
	}
}
