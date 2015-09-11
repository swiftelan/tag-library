package com.swiftelan.tag.master;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.swiftelan.tag.ComponentTagSupport;

/**
 * Tag handler for detail sections of a master-detail component.
 * <p>
 * Render a detail section the body is wrapped in an element containing information linking it to an item in iteration
 * controlled by the parent {@link MasterDetailTag}.
 * </p>
 *
 * @see ItemTag
 * @see MasterDetailTag
 */
public class DetailTag extends ComponentTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		MasterDetailTag parent = findAncestorTag(this, MasterDetailTag.class);
		if (!parent.isRenderMaster() && !parent.isRenderHeader()) {
			getAttributes().put("data-master-detail-index", Integer.toString(parent.getLoopStatus().getIndex()));
			if (getCssClass() == null) {
				setCssClass("hide");
			} else {
				setCssClass(getCssClass().concat(" hide"));
			}
			start("div", getAttributes());
			if (getJspBody() != null) {
				getJspBody().invoke(null);
			}
			end("div");
		}
	}
}
