package com.swiftelan.tag.master;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.el.ValueExpression;
import javax.servlet.jsp.JspException;

import com.swiftelan.tag.ComponentTagSupport;

/**
 * Tag handler for rendering a single item in a list of items.
 * <p>
 * Render a list item for an item in a master-detail component. Items can be grouped under a heading by specifying a
 * {@link #setGroupExpression(ValueExpression) group expression}.
 * </p>
 */
public class ItemTag extends ComponentTagSupport {

	static final String PREVIOUS_GROUP_VALUE = "com.swiftelan.tag.master.previousGroupValue";
	private ValueExpression groupExpression;

	@Override
	public void doTag() throws JspException, IOException {
		MasterDetailTag parent = findAncestorTag(this, MasterDetailTag.class);
		if (parent.isRenderMaster() && !parent.isRenderHeader()) {
			if (groupExpression != null) {
				String groupValue = (String) groupExpression.getValue(getJspContext().getELContext());
				String previousGroupValue = (String) getJspContext().getAttribute(PREVIOUS_GROUP_VALUE);
				if (!ItemTag.equals(groupValue, previousGroupValue)) {
					Map<String, String> attributes = new HashMap<>();
					attributes.put("class", "nav-header");
					start("li", attributes);
					characters(groupValue);
					end("li");
					getJspContext().setAttribute(PREVIOUS_GROUP_VALUE, groupValue);
				}
			}
			start("li", getAttributes());
			Map<String, String> attrs = new HashMap<>();
			attrs.put("href", "#");
			attrs.put("data-master-detail-index", Integer.toString(parent.getLoopStatus().getIndex()));
			start("a", attrs);
			if (getJspBody() != null) {
				getJspBody().invoke(null);
			}
			end("a");
			end("li");
			if (parent.getLoopStatus().isLast()) {
				getJspContext().removeAttribute(PREVIOUS_GROUP_VALUE);
			}
		}
	}

	/**
	 * Group multiple items under a heading.
	 * <p>
	 * Expression is evaluated every invocation. The value is compared to the previous value and if there is a change, the
	 * value is rendered as a list item with the {@code class="nav-header"}.
	 * </p>
	 *
	 * @param groupExpression Expression to use for grouping items in this list
	 */
	public void setGroupExpression(ValueExpression groupExpression) {
		this.groupExpression = groupExpression;
	}

	private static boolean equals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		return o1.equals(o2);
	}
}
