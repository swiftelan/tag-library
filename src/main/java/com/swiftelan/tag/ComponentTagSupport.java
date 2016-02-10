package com.swiftelan.tag;

import java.beans.BeanInfo;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.swiftelan.tag.util.EscapeUtil;

/**
 * Base class for tag handlers rendering HTML elements.
 * <p>
 * The ComponentTagSupport class defines utilities for generating HTML elements. The class adds convenience methods for
 * writing HTML elements to the response. The class also defines {@link BeanInfo} for overriding the
 * {@link Object#getClass()} method so the tag can use a 'class' attribute.
 * </p>
 */
public class ComponentTagSupport extends SimpleTagSupport implements DynamicAttributes {

	private final Map<String, String> attributes = new HashMap<>();

	/**
	 * Set the id attribute of the HTML element.
	 *
	 * @param id Identifier for the HTML element.
	 */
	public void setId(String id) {
		attributes.put("id", id);
	}

	/**
	 * Get the id attribute of the HTML element.
	 *
	 * @return Identifier for the HTML element or null if none exists.
	 */
	public String getId() {
		return attributes.get("id");
	}

	/**
	 * Set the class attribute of the HTML element.
	 *
	 * @param cssClass Space separated list of classes for the HTML element.
	 */
	public void setCssClass(String cssClass) {
		attributes.put("class", cssClass);
	}

	/**
	 * Get the class attribute of the HTML element.
	 *
	 * @return Value of the class attribute or null if none exists.
	 */
	public String getCssClass() {
		return attributes.get("class");
	}

	/**
	 * Set the tab navigation position for the element.
	 *
	 * @param tabindex Index of the element for tab navigation.
	 */
	public void setTabindex(String tabindex) {
		attributes.put("tabindex", tabindex);
	}

	/**
	 * Get the tab navigation position for the element.
	 *
	 * @return Index of the element for tab navigation.
	 */
	public String getTabindex() {
		return attributes.get("tabindex");
	}

	protected void start(String element, Map<String, String> attributes, Writer writer) throws IOException {
		writer.append("<");
		writer.append(element);
		writeAttributes(writer, attributes);
		writer.append(">");
	}

	/**
	 * Start a new element. Data is written to the client response using {@link JspContext#getOut()}.
	 * 
	 * @param element Name of the element.
	 * @param attributes Attribute data to include in the element.
	 * @throws IOException If an error occurs while writing the data to the response.
	 */
	public void start(String element, Map<String, String> attributes) throws IOException {
		start(element, attributes, getJspContext().getOut());
	}

	protected void end(String element, Writer writer) throws IOException {
		writer.append("</");
		writer.append(element);
		writer.append(">");
	}

	public void end(String element) throws IOException {
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

	public void characters(String value) throws IOException {
		getJspContext().getOut().append(EscapeUtil.escape(value));
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
		return (HttpServletRequest) getJspContext().findAttribute(PageContext.REQUEST);
	}
}
