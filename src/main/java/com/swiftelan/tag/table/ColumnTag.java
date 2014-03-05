package com.swiftelan.tag.table;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;

import com.swiftelan.tag.ComponentTagSupport;

public class ColumnTag extends ComponentTagSupport {
	private String header;
	private JspFragment headerBody;
	
	@Override
	public void doTag() throws JspException, IOException {
		TableTag table = findAncestorTag(this, TableTag.class);
		if (table.isRenderHeader()) {
			startTag(getJspContext().getOut(), "th", getAttributes());
			if (headerBody == null) {
				getJspContext().getOut().append(header);
			} else {
				headerBody.invoke(null);
			}
			
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
	
	public void setHeaderBody(JspFragment headerBody) {
		this.headerBody = headerBody;
	}
}
