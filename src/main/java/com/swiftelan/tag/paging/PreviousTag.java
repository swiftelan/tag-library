package com.swiftelan.tag.paging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.swiftelan.tag.ComponentTagSupport;

/**
 * Tag handler for rendering a previous page link in a paging control.
 * <p>
 * The body of the tag will be used as the body of the anchor element for the previous page link. If no body is provided
 * then a default arrow symbol will be used.
 * </p>
 * 
 * @see PagingTag
 */
public class PreviousTag extends ComponentTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		PagingTag paging = findAncestorTag(this, PagingTag.class);
		if (!paging.previous) {
			return;
		}
		Map<String, String> attrs = new HashMap<>();
		if (paging.isOnFirstPage()) {
			attrs.put("class", "disabled");
		}
		// Previous
		start("li", attrs);
		attrs.clear();

		if (!paging.isOnFirstPage()) {
			paging.addUrl(attrs, paging.getFirstResult() - paging.getPageSize(), paging.getPageSize());
		}

		start("a", attrs);
		if (getJspBody() == null) {
			characters("\u00AB");
		} else {
			getJspBody().invoke(null);
		}
		end("a");

		end("li");
	}
}
