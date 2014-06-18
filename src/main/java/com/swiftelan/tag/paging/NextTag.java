package com.swiftelan.tag.paging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.swiftelan.tag.ComponentTagSupport;

public class NextTag extends ComponentTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		PagingTag paging = findAncestorTag(this, PagingTag.class);
		if (!paging.next) {
			return;
		}

		Map<String, String> attrs = new HashMap<>();
		if (paging.isOnLastPage()) {
			attrs.put("class", "disabled");
		}
		start("li", attrs);
		attrs.clear();

		if (!paging.isOnLastPage()) {
			paging.addUrl(attrs, paging.getFirstResult() + paging.getPageSize(), paging.getPageSize());
		}
		start("a", attrs);
		if (getJspBody() == null) {
			characters("\u00BB");
		} else {
			getJspBody().invoke(null);
		}
		end("a");

		end("li");
	}

}
