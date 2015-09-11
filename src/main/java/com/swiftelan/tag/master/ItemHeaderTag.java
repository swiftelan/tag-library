package com.swiftelan.tag.master;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.swiftelan.tag.ComponentTagSupport;

public class ItemHeaderTag extends ComponentTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		MasterDetailTag parent = findAncestorTag(this, MasterDetailTag.class);
		if (parent.isRenderMaster() && parent.isRenderHeader()) {
			getJspBody().invoke(null);
		}
	}
}
