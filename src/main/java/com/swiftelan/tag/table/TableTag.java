package com.swiftelan.tag.table;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspIdConsumer;

import com.swiftelan.tag.LoopTagSupport;

public class TableTag extends LoopTagSupport implements JspIdConsumer {
	private boolean renderHeader;

	@Override
	public void doTag() throws JspException, IOException {
		startTag(getJspContext().getOut(), "table", getAttributes());

		doTableHeader();

		startTag(getJspContext().getOut(), "tbody", null);
		while (getIterator().hasNext()) {
			getIterator().next();
			startTag(getJspContext().getOut(), "tr", null);
			getJspBody().invoke(null);
			endTag(getJspContext().getOut(), "tr");
		}
		unexposeVariables();
		endTag(getJspContext().getOut(), "tbody");
		endTag(getJspContext().getOut(), "table");
	}

	private void doTableHeader() throws JspException, IOException {
		startTag(getJspContext().getOut(), "thead", null);
		startTag(getJspContext().getOut(), "tr", null);
		renderHeader = true;
		getJspBody().invoke(null);
		renderHeader = false;
		endTag(getJspContext().getOut(), "tr");
		endTag(getJspContext().getOut(), "thead");
	}

	boolean isRenderHeader() {
		return renderHeader;
	}

	void setRenderHeader(boolean renderHeader) {
		this.renderHeader = renderHeader;
	}

	@Override
	public void setJspId(String id) {
		getAttributes().put("data-jsp-id", id);
	}
}
