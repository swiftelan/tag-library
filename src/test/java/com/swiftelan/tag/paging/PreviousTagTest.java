package com.swiftelan.tag.paging;

import java.io.IOException;

import javax.el.ExpressionFactory;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.swiftelan.tag.SimpleTagJspFragment;
import com.swiftelan.tag.StringJspFragment;
import com.swiftelan.tag.TestJspContext;

@SuppressWarnings("javadoc")
public class PreviousTagTest {
	private PreviousTag tag;
	private TestJspContext context;
	private PagingTag paging;
	private ExpressionFactory factory;

	@Before
	public void before() {
		paging = new PagingTag();
		tag = new PreviousTag();
		tag.setParent(paging);
		context = new TestJspContext();
		tag.setJspContext(context);
		paging.setJspContext(context);
		factory = ExpressionFactory.newInstance();
	}

	@Test
	public void noBodyFirstPage() throws JspException, IOException {
		paging.previous = true;
		paging.setCurrentPage(1);
		tag.doTag();
		Assert.assertEquals("<li class=\"disabled\"><a>«</a></li>", context.getOut().getWriter().toString());
	}

	@Test
	public void customBodyFirstPage() throws JspException, IOException {
		paging.previous = true;
		paging.setCurrentPage(1);
		JspFragment body = new StringJspFragment(context, "Next");
		tag.setJspBody(body);
		tag.doTag();
		Assert.assertEquals("<li class=\"disabled\"><a>Next</a></li>", context.getOut().getWriter().toString());
	}

	@Test
	public void noBodyNotFirstPage() throws JspException, IOException {
		TestJspContext pagingContext = new TestJspContext();
		pagingContext.setAttribute("firstResult", Integer.valueOf(5));
		pagingContext.setAttribute("totalNumberOfItems", Integer.valueOf(10));
		pagingContext.setAttribute("pageSize", Integer.valueOf(1));
		paging.setJspContext(pagingContext);
		paging.setFirstResult(factory.createValueExpression(pagingContext.getELContext(), "#{firstResult}", Integer.class));
		paging.setTotalNumberOfItems(factory.createValueExpression(pagingContext.getELContext(), "#{totalNumberOfItems}",
				Integer.class));
		paging.setPageSize(factory.createValueExpression(pagingContext.getELContext(), "#{pageSize}", Integer.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("/foo/bar.jsp");
		Mockito.when(request.getDispatcherType()).thenReturn(DispatcherType.REQUEST);
		pagingContext.setAttribute(PageContext.REQUEST, request, PageContext.REQUEST_SCOPE);

		paging.setJspBody(new SimpleTagJspFragment(context, tag));
		paging.doTag();
		Assert.assertEquals("<li><a href=\"/foo/bar.jsp?firstResult=4&amp;pageSize=1\">«</a></li>", context.getOut()
				.getWriter().toString());
	}

	@Test
	public void noBodyNotFirstPageInclude() throws JspException, IOException {
		TestJspContext pagingContext = new TestJspContext();
		paging.setJspContext(pagingContext);
		pagingContext.setAttribute("firstResult", Integer.valueOf(5));
		pagingContext.setAttribute("totalNumberOfItems", Integer.valueOf(10));
		pagingContext.setAttribute("pageSize", Integer.valueOf(1));
		paging.setFirstResult(factory.createValueExpression(context.getELContext(), "#{firstResult}", Integer.class));
		paging.setTotalNumberOfItems(factory.createValueExpression(context.getELContext(), "#{totalNumberOfItems}",
				Integer.class));
		paging.setPageSize(factory.createValueExpression(context.getELContext(), "#{pageSize}", Integer.class));
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("/foo/bar.jsp");
		Mockito.when(request.getDispatcherType()).thenReturn(DispatcherType.INCLUDE);
		pagingContext.setAttribute(PageContext.REQUEST, request, PageContext.REQUEST_SCOPE);
		pagingContext.setAttribute(RequestDispatcher.INCLUDE_REQUEST_URI, "/foo/bar.action", PageContext.REQUEST_SCOPE);
		pagingContext.setAttribute(RequestDispatcher.INCLUDE_QUERY_STRING, "firstResult=5", PageContext.REQUEST_SCOPE);
		paging.setJspBody(new SimpleTagJspFragment(context, tag));
		paging.doTag();
		Assert.assertEquals("<li><a href=\"/foo/bar.action?firstResult=4&amp;pageSize=1\">«</a></li>", context.getOut()
				.getWriter().toString());
	}

	@Test
	public void noBodyNotFirstPageForward() throws JspException, IOException {
		TestJspContext pagingContext = new TestJspContext();
		paging.setJspContext(pagingContext);
		pagingContext.setAttribute("firstResult", Integer.valueOf(5));
		pagingContext.setAttribute("totalNumberOfItems", Integer.valueOf(10));
		pagingContext.setAttribute("pageSize", Integer.valueOf(1));
		paging.setFirstResult(factory.createValueExpression(pagingContext.getELContext(), "#{firstResult}", Integer.class));
		paging.setTotalNumberOfItems(factory.createValueExpression(pagingContext.getELContext(), "#{totalNumberOfItems}",
				Integer.class));
		paging.setPageSize(factory.createValueExpression(pagingContext.getELContext(), "#{pageSize}", Integer.class));

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("/foo/bar.jsp");
		Mockito.when(request.getDispatcherType()).thenReturn(DispatcherType.FORWARD);
		pagingContext.setAttribute(PageContext.REQUEST, request, PageContext.REQUEST_SCOPE);
		pagingContext.setAttribute(RequestDispatcher.FORWARD_REQUEST_URI, "/foo/bar.action", PageContext.REQUEST_SCOPE);
		pagingContext.setAttribute(RequestDispatcher.FORWARD_QUERY_STRING, "test=true", PageContext.REQUEST_SCOPE);

		paging.setJspBody(new SimpleTagJspFragment(context, tag));
		paging.doTag();
		Assert.assertEquals("<li><a href=\"/foo/bar.action?test=true&amp;firstResult=4&amp;pageSize=1\">«</a></li>", context
				.getOut().getWriter().toString());
	}

	@Test
	public void notPrevious() throws JspException, IOException {
		tag.doTag();
		Assert.assertEquals("", context.getOut().getWriter().toString());
	}
}
