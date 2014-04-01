package com.swiftelan.tag.input;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.swiftelan.tag.ComponentTagSupport;

public class InputTag extends ComponentTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		startTag(getJspContext().getOut(), "input", getAttributes());
	}

	public void setValue(String value) {
		getAttributes().put("value", value);
	}

	public String getValue() {
		return getAttributes().get("value");
	}

	public void setName(String name) {
		getAttributes().put("name", name);
	}

	public String getName() {
		return getAttributes().get("name");
	}

	public void setRequired(String required) {
		setBooleanAttribute("required", required);
	}

	public String getRequired() {
		return getAttributes().get("required");
	}

	public void setType(String type) {
		getAttributes().put("type", type);
	}

	public String getType() {
		return getAttributes().get("type");
	}

	public void setAutocomplete(String autocomplete) {
		setBooleanAttribute("autocomplete", autocomplete);
	}

	public String getAutocomplete() {
		return getAttributes().get("autocomplete");
	}

	public void setDisabled(String disabled) {
		setBooleanAttribute("disabled", disabled);
	}

	public String getDisabled() {
		return getAttributes().get("disabled");
	}

	public void setChecked(String checked) {
		setBooleanAttribute("checked", checked);
	}

	public String getChecked() {
		return getAttributes().get("checked");
	}

	protected void setBooleanAttribute(String attributeName, String value) {
		if (value != null && (attributeName.equals(value) || "true".equals(value))) {
			getAttributes().put(attributeName, attributeName);
		} else {
			getAttributes().remove(attributeName);
		}
	}
}
