package com.swiftelan.tag.table;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.LoopTagStatus;
import javax.servlet.jsp.tagext.JspFragment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.swiftelan.tag.MockJspFragment;
import com.swiftelan.tag.TestJspContext;

public class TableTagTest {
	private static ResourceBundle expected;
	private TableTag tag;
	private TestJspContext jspContext;
	private String jspId;

	@BeforeClass
	public static void beforeClass() {
		expected = ResourceBundle.getBundle("com.swiftelan.tag.table.table-test");
	}

	@Before
	public void before() {
		tag = new TableTag();
		jspContext = new TestJspContext();
		tag.setJspContext(jspContext);
		jspId = "simulated-container-supplied-id";
		JspFragment body = new MockJspFragment(jspContext);
		tag.setJspBody(body);
		tag.setJspId(jspId);
	}

	@Test
	public void nullItems() throws JspException, IOException {
		tag.doTag();
		Assert.assertEquals(expected.getString("no.items"), jspContext.getOut().getWriter().toString());
		Assert.assertFalse(jspContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
	}

	@Test
	public void emptyItems() throws JspException, IOException {
		tag.setItems(Collections.emptySet());
		tag.doTag();
		Assert.assertEquals(expected.getString("no.items"), jspContext.getOut().getWriter().toString());
		Assert.assertFalse(jspContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
	}

	@Test
	public void oneItem() throws JspException, IOException {
		Collection<Object> items = new ArrayList<>();
		items.add("foo");
		tag.setItems(items);
		tag.doTag();

		Assert.assertEquals(expected.getString("one.item"), jspContext.getOut().getWriter().toString());
		Assert.assertFalse(jspContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
	}

	@Test
	public void classAttribute() throws JspException, IOException {
		tag.setCssClass("css-class");
		tag.doTag();
		Assert.assertTrue(Pattern.matches("<table(.*) class=\"" + tag.getCssClass() + "\"(.*)>(.*)", jspContext.getOut()
				.getWriter().toString()));
		Assert.assertFalse(jspContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
	}

	@Test
	public void emptyVar() throws JspException, IOException {
		Collection<String> items = new ArrayList<>();
		items.add("foo");
		tag.setItems(items);
		tag.setVar(" ");
		JspFragment body = new JspFragment() {

			@Override
			public void invoke(Writer out) throws JspException, IOException {
				Assert.assertFalse("An empty 'var' attribute should not introduce a page scoped variable.", jspContext
						.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
			}

			@Override
			public JspContext getJspContext() {
				return jspContext;
			}
		};
		tag.setJspBody(body);
		tag.doTag();
		Assert.assertEquals(expected.getString("one.item"), jspContext.getOut().getWriter().toString());
		Assert.assertFalse(jspContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
	}

	@Test
	public void nullVar() throws JspException, IOException {
		Collection<String> items = new ArrayList<>();
		items.add("foo");
		tag.setItems(items);
		tag.setVar(null);
		JspFragment body = new JspFragment() {

			@Override
			public void invoke(Writer out) throws JspException, IOException {
				Assert.assertFalse("A null 'var' attribute should not introduce a page scoped variable.", jspContext
						.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
			}

			@Override
			public JspContext getJspContext() {
				return jspContext;
			}
		};
		tag.setJspBody(body);
		tag.doTag();
		Assert.assertEquals(expected.getString("one.item"), jspContext.getOut().getWriter().toString());
		Assert.assertFalse(jspContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
	}

	@Test
	public void oneItemWithVar() throws JspException, IOException {
		Collection<String> items = new ArrayList<>();
		final String item = "foo";
		final String var = "pageContextVariable";
		items.add(item);
		tag.setItems(items);
		tag.setVar(var);
		JspFragment body = new JspFragment() {

			@Override
			public void invoke(Writer out) throws JspException, IOException {
				if (tag.isRenderHeader()) {
					Assert.assertNull("The header should only be rendered once.",
							jspContext.getAttribute("header", PageContext.APPLICATION_SCOPE));
					jspContext.setAttribute("header", Boolean.TRUE, PageContext.APPLICATION_SCOPE);
				} else {
					Assert.assertEquals(item, jspContext.getAttribute(var));
					jspContext.setAttribute("invoked", Boolean.TRUE, PageContext.APPLICATION_SCOPE);
				}
			}

			@Override
			public JspContext getJspContext() {
				return jspContext;
			}
		};
		tag.setJspBody(body);
		tag.doTag();

		Assert.assertEquals(expected.getString("one.item"), jspContext.getOut().getWriter().toString());
		Assert.assertFalse(jspContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
		Boolean invoked = (Boolean) jspContext.getAttribute("invoked", PageContext.APPLICATION_SCOPE);
		Assert.assertNotNull(invoked);
		Assert.assertTrue(invoked.booleanValue());
		Boolean header = (Boolean) jspContext.getAttribute("header", PageContext.APPLICATION_SCOPE);
		Assert.assertNotNull(header);
		Assert.assertTrue(header.booleanValue());
	}

	@Test
	public void emptyVarStatus() throws JspException, IOException {
		Collection<String> items = new ArrayList<>();
		items.add("foo");
		tag.setItems(items);
		tag.setVarStatus(" ");
		JspFragment body = new JspFragment() {

			@Override
			public void invoke(Writer out) throws JspException, IOException {
				Assert.assertFalse("An empty 'varStatus' attribute should not introduce a page scoped variable.", jspContext
						.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
			}

			@Override
			public JspContext getJspContext() {
				return jspContext;
			}
		};
		tag.setJspBody(body);
		tag.doTag();
		Assert.assertEquals(expected.getString("one.item"), jspContext.getOut().getWriter().toString());
		Assert.assertFalse(jspContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
	}

	@Test
	public void nullVarStatus() throws JspException, IOException {
		Collection<String> items = new ArrayList<>();
		items.add("foo");
		tag.setItems(items);
		tag.setVarStatus(null);
		JspFragment body = new JspFragment() {

			@Override
			public void invoke(Writer out) throws JspException, IOException {
				Assert.assertFalse("A null 'varStatus' attribute should not introduce a page scoped variable.", jspContext
						.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
			}

			@Override
			public JspContext getJspContext() {
				return jspContext;
			}
		};
		tag.setJspBody(body);
		tag.doTag();
		Assert.assertEquals(expected.getString("one.item"), jspContext.getOut().getWriter().toString());
		Assert.assertFalse(jspContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
	}

	@Test
	public void oneItemWithVarStatus() throws JspException, IOException {
		Collection<String> items = new ArrayList<>();
		final String item = "foo";
		final String varStatus = "pageContextVariableStatus";

		items.add(item);
		tag.setItems(items);
		tag.setVarStatus(varStatus);
		JspFragment body = new JspFragment() {

			@Override
			public void invoke(Writer out) throws JspException, IOException {
				if (tag.isRenderHeader()) {
					Assert.assertNull("The header should only be rendered once.",
							jspContext.getAttribute("header", PageContext.APPLICATION_SCOPE));
					jspContext.setAttribute("header", Boolean.TRUE, PageContext.APPLICATION_SCOPE);
				} else {
					LoopTagStatus loopStatus = (LoopTagStatus) jspContext.getAttribute(varStatus);
					Assert.assertEquals(item, loopStatus.getCurrent());
					Assert.assertTrue(loopStatus.isFirst());
					Assert.assertTrue(loopStatus.isLast());
					Assert.assertEquals(0, loopStatus.getIndex());
					Assert.assertEquals(1, loopStatus.getCount());
					Assert.assertNull(loopStatus.getBegin());
					Assert.assertNull(loopStatus.getEnd());
					Assert.assertNull(loopStatus.getStep());
					jspContext.setAttribute("invoked", Boolean.TRUE, PageContext.APPLICATION_SCOPE);
				}

			}

			@Override
			public JspContext getJspContext() {
				return jspContext;
			}
		};
		tag.setJspBody(body);
		tag.doTag();
		Assert.assertEquals(expected.getString("one.item"), jspContext.getOut().getWriter().toString());
		Assert.assertFalse(jspContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
		Boolean invoked = (Boolean) jspContext.getAttribute("invoked", PageContext.APPLICATION_SCOPE);
		Assert.assertNotNull(invoked);
		Assert.assertTrue(invoked.booleanValue());
		Boolean header = (Boolean) jspContext.getAttribute("header", PageContext.APPLICATION_SCOPE);
		Assert.assertNotNull(header);
		Assert.assertTrue(header.booleanValue());
	}

	@Test
	public void threeItemsWithVar() throws JspException, IOException {
		final List<String> items = new ArrayList<>();
		final String one = "one";
		final String two = "two";
		final String three = "three";
		final String var = "pageContextVariable";
		items.add(one);
		items.add(two);
		items.add(three);
		tag.setItems(items);
		tag.setVar(var);
		JspFragment body = new JspFragment() {
			private int index = 0;

			@Override
			public void invoke(Writer out) throws JspException, IOException {
				if (tag.isRenderHeader()) {
					Assert.assertNull("The header should only be rendered once.",
							jspContext.getAttribute("header", PageContext.APPLICATION_SCOPE));
					jspContext.setAttribute("header", Boolean.TRUE, PageContext.APPLICATION_SCOPE);
				} else {
					Assert.assertEquals(items.get(index), jspContext.getAttribute(var));
					index++;
					jspContext.setAttribute("invoked", Integer.valueOf(index), PageContext.APPLICATION_SCOPE);
				}
			}

			@Override
			public JspContext getJspContext() {
				return jspContext;
			}
		};
		tag.setJspBody(body);
		tag.doTag();

		Assert.assertEquals(expected.getString("three.items"), jspContext.getOut().getWriter().toString());
		Assert.assertFalse(jspContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
		Integer invoked = (Integer) jspContext.getAttribute("invoked", PageContext.APPLICATION_SCOPE);
		Assert.assertNotNull(invoked);
		Assert.assertEquals(Integer.valueOf(3), invoked);
		Boolean header = (Boolean) jspContext.getAttribute("header", PageContext.APPLICATION_SCOPE);
		Assert.assertNotNull(header);
		Assert.assertTrue(header.booleanValue());
	}
}
