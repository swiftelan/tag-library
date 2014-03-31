package com.swiftelan.tag.input;

import com.swiftelan.tag.ComponentTagSupport;

public class InputTagSupport extends ComponentTagSupport {

	public void setValue(String value) {
		getAttributes().put("value", value);
	}
	
	public String getValue() {
		return getAttributes().get("value");
	}
	
	public void setName(String name) {
		getAttributes().put("name", name);
	}
	
	public String getName(){
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
	
	protected void setBooleanAttribute(String attributeName, String value) {
		if (value != null && (attributeName.equals(value) || "true" == value)) {
			getAttributes().put(attributeName, attributeName);
		} else {
			getAttributes().remove(attributeName);
		}
	}
}
