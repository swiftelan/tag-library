package com.swiftelan.tag.table;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.swiftelan.tag.ComponentTagSupport;

public class ColumnTag extends ComponentTagSupport {
	private String header;
	
	@Override
	public void doTag() throws JspException, IOException {
		TableTag table = findAncestorTag(this, TableTag.class);
		if (table.isRenderHeader()) {
			startTag(getJspContext().getOut(), "th", getAttributes());
			getJspContext().getOut().append(header);
			endTag(getJspContext().getOut(), "th");
		} else {
			startTag(getJspContext().getOut(), "td", getAttributes());
			getJspBody().invoke(null);
			endTag(getJspContext().getOut(), "td");
		}
	}
	
	public void setHeader(String header) {
		this.header = header;
	}
}
