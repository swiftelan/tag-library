package com.swiftelan.tag;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.StandardELContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;

import org.mockito.Mockito;

/**
 * TestJspContext implements a JspContext for unit testing.
 * <p>
 * The class provides map based context lookups for page, request, session and application scopes. The {@link ELContext} has the
 * standard {@link ELResolver}s as well as one that resolves variables in the scopes.
 * </p>
 * @see StandardELContext
 * @see TestJspWriter
 * @see TestScopedAttributeELResolver
 */
@SuppressWarnings("deprecation")
public class TestJspContext extends JspContext {

	private Map<String, Object> pageScope;
	private Map<String, Object> requestScope;
	private Map<String, Object> sessionScope;
	private Map<String, Object> applicationScope;
	private Map<Integer, Map<String, Object>> scopes;
	private TestJspWriter writer;
	private StandardELContext elContext;
	private ServletContext servletContext;

	/**
	 * Initialize an empty JspContext
	 */
	public TestJspContext() {
		pageScope = new HashMap<>();
		requestScope = new HashMap<>();
		sessionScope = new HashMap<>();
		applicationScope = new HashMap<>();
		scopes = new TreeMap<>();

		scopes.put(Integer.valueOf(PageContext.PAGE_SCOPE), pageScope);
		scopes.put(Integer.valueOf(PageContext.REQUEST_SCOPE), requestScope);
		scopes.put(Integer.valueOf(PageContext.SESSION_SCOPE), sessionScope);
		scopes.put(Integer.valueOf(PageContext.APPLICATION_SCOPE), applicationScope);

		writer = new TestJspWriter();
		elContext = new StandardELContext(ExpressionFactory.newInstance());
		elContext.putContext(JspContext.class, this);
		elContext.addELResolver(new TestScopedAttributeELResolver());

		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		servletContext = Mockito.mock(ServletContext.class);
		Mockito.when(request.getServletContext()).thenReturn(servletContext);
		requestScope.put(PageContext.REQUEST, request);
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public void setAttribute(String name, Object value) {
		if (value == null) {
			removeAttribute(name);
		} else {
			pageScope.put(name, value);
		}
	}

	@Override
	public void setAttribute(String name, Object value, int scope) {
		if (value == null) {
			removeAttribute(name, scope);
		} else {
			scopes.get(Integer.valueOf(scope)).put(name, value);
		}
	}

	@Override
	public Object getAttribute(String name) {
		return pageScope.get(name);
	}

	@Override
	public Object getAttribute(String name, int scope) {
		return scopes.get(Integer.valueOf(scope)).get(name);
	}

	@Override
	public Object findAttribute(String name) {
		for (Map<String, Object> scope : scopes.values()) {
			if (scope.containsKey(name)) {
				return scope.get(name);
			}
		}
		return null;
	}

	@Override
	public void removeAttribute(String name) {
		pageScope.remove(name);
	}

	@Override
	public void removeAttribute(String name, int scope) {
		scopes.get(Integer.valueOf(scope)).remove(name);
	}

	@Override
	public int getAttributesScope(String name) {
		for (Entry<Integer, Map<String, Object>> entry : scopes.entrySet()) {
			if (entry.getValue().containsKey(name)) {
				return entry.getKey().intValue();
			}
		}
		return 0;
	}

	@Override
	public Enumeration<String> getAttributeNamesInScope(int scope) {
		return Collections.enumeration(scopes.get(Integer.valueOf(scope)).keySet());
	}

	@Override
	public TestJspWriter getOut() {
		return writer;
	}

	@Override
	public ExpressionEvaluator getExpressionEvaluator() {
		return null;
	}

	@Override
	public VariableResolver getVariableResolver() {
		return null;
	}

	@Override
	public ELContext getELContext() {
		return elContext;
	}

}
