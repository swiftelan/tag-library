package com.swiftelan.tag.paging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.el.ValueExpression;
import javax.servlet.RequestDispatcher;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspIdConsumer;

import com.swiftelan.tag.ComponentTagSupport;
import com.swiftelan.tag.util.EscapeUtil;
import com.swiftelan.tag.util.Strings;

public class PagingTag extends ComponentTagSupport implements JspIdConsumer {
	private ValueExpression _totalNumberOfItems;
	private ValueExpression _firstResult;
	private ValueExpression _pageSize;
	private int totalNumberOfItems;
	private int pageSize;
	private int firstResult;
	private int currentPage;
	boolean next;
	boolean previous;
	boolean alwaysDisplayFirstPageLink = true;
	boolean alwaysDisplayLastPageLink = true;

	@Override
	public void doTag() throws JspException, IOException {
		totalNumberOfItems = (Integer) _totalNumberOfItems.getValue(getJspContext().getELContext());
		pageSize = (Integer) _pageSize.getValue(getJspContext().getELContext());
		firstResult = (Integer) _firstResult.getValue(getJspContext().getELContext());
		if (pageSize == 0) {
			currentPage = 1;
		} else {
			currentPage = firstResult / pageSize + 1;
		}

		if (currentPage > getTotalNumberOfPages()) {
			currentPage = getTotalNumberOfPages();
		} else if (currentPage < 1) {
			currentPage = 1;
		}

		if (getTotalNumberOfPages() > 0) {
			start("ul", getAttributes());
			previous = true;
			if (getJspBody() != null) {
				getJspBody().invoke(null);
			}
			previous = false;
			renderFirstPageLink();
			renderPageRangeLinks();
			renderLastPageLink();
			next = true;
			if (getJspBody() != null) {
				getJspBody().invoke(null);
			}
			next = false;
			end("ul");
		}
	}

	void renderFirstPageLink() throws IOException, JspTagException {
		// Only render page 1 if not in the page range
		if (alwaysDisplayFirstPageLink && getFirstPageInRange() > 1) {
			start("li", null);
			start("a", addUrl(new HashMap<String, String>(), 0, pageSize));
			characters("1");
			end("a");
			end("li");

			if (getFirstPageInRange() > 2) {
				Map<String, String> attrs = new HashMap<>();
				attrs.put("class", "disabled");
				start("li", attrs);
				start("a", null);
				characters("&hellip;", false);
				end("a");
				end("li");
			}
		}
	}

	void renderLastPageLink() throws IOException, JspTagException {
		if (alwaysDisplayLastPageLink && getLastPageInRange() < getTotalNumberOfPages()) {
			if (getLastPageInRange() < getTotalNumberOfPages() - 1) {
				Map<String, String> attrs = new HashMap<>();
				attrs.put("class", "disabled");
				start("li", attrs);
				start("a", null);
				characters("&hellip;", false);
				end("a");
				end("li");
			}
			start("li", null);
			int firstResultOnLastPage = (getTotalNumberOfPages() - 1) * pageSize;
			Map<String, String> attrs = new HashMap<>();
			addUrl(attrs, firstResultOnLastPage, pageSize);
			start("a", attrs);
			characters(Integer.toString(getTotalNumberOfPages()));
			end("a");
			end("li");
		}
	}

	void renderPageRangeLinks() throws IOException, JspTagException {
		for (int i = getFirstPageInRange(); i <= getLastPageInRange(); i++) {
			Map<String, String> attrs = new HashMap<>();
			if (i == currentPage) {
				attrs.put("class", "active");
			}
			start("li", attrs);
			attrs.clear();
			if (i != currentPage) {
				addUrl(attrs, (i - 1) * pageSize, pageSize);
			}
			start("a", attrs);
			characters(Integer.toString(i));
			end("a");

			end("li");
		}
	}

	Map<String, String> addUrl(Map<String, String> attrs, int firstResult, int pageSize) throws JspTagException {
		String uri;
		String query;
		switch (getRequest().getDispatcherType()) {
		case FORWARD:
			uri = (String) getJspContext().getAttribute(RequestDispatcher.FORWARD_REQUEST_URI, PageContext.REQUEST_SCOPE);
			query = (String) getJspContext().getAttribute(RequestDispatcher.FORWARD_QUERY_STRING, PageContext.REQUEST_SCOPE);
			break;
		case INCLUDE:
			uri = (String) getJspContext().getAttribute(RequestDispatcher.INCLUDE_REQUEST_URI, PageContext.REQUEST_SCOPE);
			query = (String) getJspContext().getAttribute(RequestDispatcher.INCLUDE_QUERY_STRING, PageContext.REQUEST_SCOPE);
			break;
		default:
			uri = getRequest().getRequestURI();
			query = getRequest().getQueryString();
		}

		StringBuilder sb = new StringBuilder(uri);
		sb.append("?");
		query = Strings.replaceParameter(query, getFirstResultParameterName(), Integer.toString(firstResult));
		query = Strings.replaceParameter(query, getPageSizeParameterName(), Integer.toString(pageSize));
		sb.append(query);
		attrs.put("href", sb.toString());

		return attrs;
	}

	private String getFirstResultParameterName() {
		String paramName = Strings.stringBetween(_firstResult.getExpressionString());
		return EscapeUtil.escape(paramName);
	}

	private String getPageSizeParameterName() {
		String paramName = Strings.stringBetween(_pageSize.getExpressionString());
		return EscapeUtil.escape(paramName);
	}

	boolean isOnFirstPage() {
		return currentPage == 1;
	}

	void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	boolean isOnLastPage() {
		return currentPage * pageSize >= totalNumberOfItems;
	}

	int getTotalNumberOfPages() {
		if (totalNumberOfItems == 0) {
			return 0;
		}
		return (int) Math.ceil((double) totalNumberOfItems / (double) pageSize);
	}

	int getFirstPageInRange() {
		return Math.max(1, currentPage - 3);
	}

	int getLastPageInRange() {
		return Math.min(currentPage + 3, getTotalNumberOfPages());
	}

	int getPageSize() {
		return pageSize;
	}

	int getFirstResult() {
		return firstResult;
	}

	public void setPageSize(ValueExpression pageSize) {
		_pageSize = pageSize;
	}

	public void setTotalNumberOfItems(ValueExpression totalNumberOfItems) {
		_totalNumberOfItems = totalNumberOfItems;
	}

	public void setFirstResult(ValueExpression firstResult) {
		_firstResult = firstResult;
	}

	@Override
	public void setJspId(String id) {
		getAttributes().put("data-jsp-id", id);
	}

}
