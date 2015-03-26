package com.swiftelan.tag;

import java.util.Collections;
import java.util.Iterator;

import javax.servlet.jsp.jstl.core.LoopTagStatus;
import javax.servlet.jsp.tagext.SimpleTag;

/**
 * Base class for tag handlers that iterate over items.
 * <p>
 * Tag handler that mimics the functionality in {@link javax.servlet.jsp.jstl.core.LoopTagSupport} in a
 * {@link SimpleTag}. The class supports page context variables exposing the current object and {@link LoopTagStatus}
 * information.
 * </p>
 */
public class LoopTagSupport extends ComponentTagSupport {

	/**
	 * Items to iterate over
	 */
	private Iterable<?> items;
	/**
	 * Page context variable name of the last item returned by the iterator
	 */
	private String var;
	/**
	 * Page context variable name of the loop status
	 */
	private String varStatus;
	private LoopTagStatus loopStatus;

	/**
	 * The last item returned by the iterator.
	 */
	private Object current;
	/**
	 * The internal index of the current item.
	 */
	private int index;
	/**
	 * Internal iterator maintaining the state of the iteration
	 */
	private Iterator<Object> iterator;

	/**
	 * Initializes the tag with an empty iterator.
	 */
	public LoopTagSupport() {
		reset();
	}

	/**
	 * Get the iterator for the tag. Multiple invocations of this method will return the same iterator. This means the
	 * state of the iterator is preserved.
	 * 
	 * @return Current iterator associated with the tag.
	 * @see #reset()
	 */
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

	/**
	 * Place current object and loop status variables in the page context. This only exposes the variables if variable
	 * names have been specified.
	 * 
	 * @see #setVar(String)
	 * @see #setVarStatus(String)
	 */
	protected void exposeVariables() {
		if (var != null) {
			getJspContext().setAttribute(var, current);
		}

		if (varStatus != null && getLoopStatus().isFirst()) {
			getJspContext().setAttribute(varStatus, getLoopStatus());
		}
	}

	/**
	 * Remove the current object and loop status variables from the page context.
	 * 
	 * @see #setVar(String)
	 * @see #setVarStatus(String)
	 */
	protected void unexposeVariables() {
		if (var != null) {
			getJspContext().removeAttribute(var);
		}
		if (varStatus != null) {
			getJspContext().removeAttribute(varStatus);
		}
	}

	/**
	 * Reset the state of the tag to start at the first item.
	 */
	public void reset() {
		index = -1;
		current = null;
		iterator = null;
	}

	/**
	 * Provide the items to be iterated over.
	 * 
	 * @param items {@link Iterable} containing the items to iterate over.
	 */
	public void setItems(Iterable<?> items) {
		this.items = items;
	}

	/**
	 * Provide a variable name to store the current object in the iteration. The variable is placed in the page context.
	 * 
	 * @param var Name of the variable
	 */
	public void setVar(String var) {
		if (var != null && !var.trim().isEmpty()) {
			this.var = var;
		}
	}

	/**
	 * Provide a variable name to store the loop status. The variable is place in the page context.
	 * 
	 * @param varStatus Name of the variable
	 */
	public void setVarStatus(String varStatus) {
		if (varStatus != null && !varStatus.trim().isEmpty()) {
			this.varStatus = varStatus;
		}
	}

	/**
	 * Get the current status of the iteration.
	 * 
	 * @return Status of the iteration.
	 */
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
