package com.swiftelan.tag.input;

import java.io.IOException;

import javax.servlet.jsp.JspException;

public class CheckboxTag extends InputTagSupport {
	
	@Override
	public void doTag() throws JspException, IOException {
		setType("checkbox");
		startTag(getJspContext().getOut(), "input", getAttributes());
	}

	public void setChecked(String checked) {
		setBooleanAttribute("checked", checked);
	}
	
	public String getChecked() {
		return getAttributes().get("checked");
	}
}
