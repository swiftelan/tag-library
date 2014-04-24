package com.swiftelan.tag;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javax.servlet.jsp.jstl.core.LoopTagStatus;

public class LoopTagSupport extends ComponentTagSupport {
	private Collection<?> items;
	private String var;
	private String varStatus;
	private LoopTagStatus loopStatus;

	private Object current;
	private int index;
	private Iterator<Object> iterator;

	public LoopTagSupport() {
		reset();
	}

	public Iterator<Object> getIterator() {
		if (iterator == null) {
			iterator = new Iterator<Object>() {
				private Iterator<?> delegate = items == null ? Collections.emptyIterator() : items.iterator();

				@Override
				public boolean hasNext() {
					return delegate.hasNext();
				}

				@Override
				public Object next() {
					index++;
					current = delegate.next();
					exposeVariables();
					return current;
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
		return iterator;
	}

	protected void exposeVariables() {
		if (var != null) {
			getJspContext().setAttribute(var, current);
		}

		if (varStatus != null && getLoopStatus().isFirst()) {
			getJspContext().setAttribute(varStatus, getLoopStatus());
		}
	}

	protected void unexposeVariables() {
		if (var != null) {
			getJspContext().removeAttribute(var);
		}
		if (varStatus != null) {
			getJspContext().removeAttribute(varStatus);
		}
	}

	protected void reset() {
		index = -1;
		current = null;
		iterator = null;
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

	public LoopTagStatus getLoopStatus() {
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
				return !getIterator().hasNext();
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
}
