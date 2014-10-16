package com.swiftelan.tag.table;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspIdConsumer;

import com.swiftelan.tag.LoopTagSupport;

/**
 * Tag handler for rendering an HTML {@code <table>} element.
 *
 * @see ColumnTag
 * @see ColumnHeaderTag
 */
public class TableTag extends LoopTagSupport implements JspIdConsumer {
	private boolean renderHeader;

	@Override
	public void doTag() throws JspException, IOException {
		start("table", getAttributes());

		doTableHeader();

		start("tbody", null);
		while (getIterator().hasNext()) {
			getIterator().next();
			start("tr", null);
			getJspBody().invoke(null);
			end("tr");
		}
		unexposeVariables();
		end("tbody");
		end("table");
	}

	private void doTableHeader() throws JspException, IOException {
		start("thead", null);
		start("tr", null);
		renderHeader = true;
		getJspBody().invoke(null);
		renderHeader = false;
		end("tr");
		end("thead");
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
