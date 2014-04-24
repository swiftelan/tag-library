package com.swiftelan.tag.master;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.swiftelan.tag.ComponentTagSupport;

public class DetailTag extends ComponentTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		MasterDetailTag parent = findAncestorTag(this, MasterDetailTag.class);
		if (!parent.isRenderMaster()) {
			getAttributes().put("data-master-detail-index", Integer.toString(parent.getLoopStatus().getIndex()));
			if (getCssClass() == null) {
				setCssClass("hide");
			} else {
				setCssClass(getCssClass().concat(" hide"));
			}
			startTag(getJspContext().getOut(), "div", getAttributes());
			if (getJspBody() != null) {
				getJspBody().invoke(null);
			}
			endTag(getJspContext().getOut(), "div");
		}
	}
}
