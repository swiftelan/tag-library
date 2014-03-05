package com.swiftelan.tag.table;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.swiftelan.tag.ComponentTagSupport;

public class ColumnHeaderTag extends ComponentTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		TableTag table = findAncestorTag(this, TableTag.class);
		
		if (table.isRenderHeader()) {
			ColumnTag column = findAncestorTag(this, ColumnTag.class);
			column.setHeaderBody(getJspBody());
		}
	}
}
