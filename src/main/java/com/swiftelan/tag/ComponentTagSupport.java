package com.swiftelan.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.swiftelan.tag.util.EscapeUtil;

public class ComponentTagSupport extends SimpleTagSupport implements DynamicAttributes {
	private Map<String, String> attributes = new HashMap<>();
	
	public void setCssClass(String cssClass) {
		attributes.put("class", cssClass);
	}
	
	public String getCssClass() {
		return attributes.get("class");
	}

	protected void startTag(Writer writer, String element, Map<String, String> attributes) throws IOException {
		writer.append("<");
		writer.append(element);
		writeAttributes(writer, attributes);
		writer.append(">");
	}

	protected void endTag(Writer writer, String element) throws IOException {
		writer.append("</");
		writer.append(element);
		writer.append(">");
	}

	protected void writeAttributes(Writer writer, Map<String, String> attributes) throws IOException {
		if (attributes == null) {
			return;
		}
		for (Entry<String, String> attr : attributes.entrySet()) {
			String value = attr.getValue();
			if (value == null){
				continue;
			}
			writer.append(" ");
			writer.append(attr.getKey());
			writer.append("=\"");
			writer.append(EscapeUtil.escape(value));
			writer.append("\"");
		}
	}
	
	protected Map<String, String> getAttributes() {
		return attributes;
	}

	@Override
	public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
		attributes.put(localName, String.valueOf(value));
	}
	
	protected static <T> T findAncestorTag(JspTag from, Class<T> klass) {
		return (T) findAncestorWithClass(from, klass);
	}
}
