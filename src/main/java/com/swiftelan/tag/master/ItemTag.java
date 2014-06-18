package com.swiftelan.tag.master;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.el.ValueExpression;
import javax.servlet.jsp.JspException;

import com.swiftelan.tag.ComponentTagSupport;

public class ItemTag extends ComponentTagSupport {
	static final String PREVIOUS_GROUP_VALUE = "com.swiftelan.tag.master.previousGroupValue";
	private ValueExpression groupExpression;

	@Override
	public void doTag() throws JspException, IOException {
		MasterDetailTag parent = findAncestorTag(this, MasterDetailTag.class);
		if (parent.isRenderMaster()) {
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
