package com.swiftelan.tag;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.StandardELContext;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;

@SuppressWarnings("deprecation")
public class TestJspContext extends JspContext {
	private Map<String, Object> pageScope;
	private Map<String, Object> requestScope;
	private Map<String, Object> sessionScope;
	private Map<String, Object> applicationScope;
	private Map<Integer, Map<String, Object>> scopes;
	private TestJspWriter writer;
	private StandardELContext elContext;

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
