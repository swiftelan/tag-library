package com.swiftelan.tag.table;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.core.LoopTagStatus;
import javax.servlet.jsp.tagext.JspIdConsumer;

import com.swiftelan.tag.ComponentTagSupport;

public class TableTag extends ComponentTagSupport implements JspIdConsumer {
	private Collection<?> items;
	private String var;
	private String varStatus;
	private LoopTagStatus loopStatus;
	private boolean renderHeader;
	
	private Object current;
	private int index = 0;
	private ListIterator<?> iterator;
	
	@Override
	public void doTag() throws JspException, IOException {
		startTag(getJspContext().getOut(), "table", getAttributes());
		
		doTableHeader();
		
		startTag(getJspContext().getOut(), "tbody", null);
		if (items != null && !items.isEmpty()) {
			iterator = new LinkedList<>(items).listIterator();
			while (iterator.hasNext()) {
				doTableRow();
			}
			if (var != null) {
				getJspContext().removeAttribute(var);
			}
			if (varStatus != null) {
				getJspContext().removeAttribute(varStatus);
			}
		}
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
	
	private void doTableRow() throws IOException, JspException {
		current = iterator.next();
		startTag(getJspContext().getOut(), "tr", null);
		if (var != null) {
			getJspContext().setAttribute(var, current);
		}
		
		if (varStatus != null && getLoopStatus().isFirst()) {
			getJspContext().setAttribute(varStatus, getLoopStatus());
		}
		getJspBody().invoke(null);
		endTag(getJspContext().getOut(), "tr");
		index++;
	}
	
	public void setItems(Collection<?> items) {
		this.items = items;
	}
	
	public void setVar(String var) {
		if (var != null && !var.trim().isEmpty()) {
			this.var = var;
		}
	}
	
	public void setVarStatus(String varStatus) {
		if (varStatus != null && !varStatus.trim().isEmpty()) {
			this.varStatus = varStatus;
		}
	}
	
	private LoopTagStatus getLoopStatus() {
		class LoopStatus implements LoopTagStatus {

			@Override
			public Object getCurrent() {
				return current;
			}

			@Override
			public int getIndex() {
				return index;
			}

			@Override
			public int getCount() {
				return index + 1;
			}

			@Override
			public boolean isFirst() {
				return index == 0;
			}

			@Override
			public boolean isLast() {
				return !iterator.hasNext();
			}

			@Override
			public Integer getBegin() {
				return null;
			}

			@Override
			public Integer getEnd() {
				return null;
			}

			@Override
			public Integer getStep() {
				return null;
			}
			
		}
		if (loopStatus == null) {
			loopStatus = new LoopStatus();
		}
		return loopStatus;
	}

	public boolean isRenderHeader() {
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
