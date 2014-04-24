package com.swiftelan.tag.master;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Pattern;

import javax.el.ExpressionFactory;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.swiftelan.tag.StringJspFragment;
import com.swiftelan.tag.TestJspContext;

public class ItemTagTest {
	private ItemTag tag;
	private TestJspContext context;

	@Before
	public void before() {
		context = new TestJspContext();
		tag = new ItemTag();
		tag.setJspContext(context);
	}

	@Test
	public void master() throws JspException, IOException {
		MasterDetailTag parent = new MasterDetailTag();
		Collection<String> items = new HashSet<>();
		items.add("one");
		parent.setItems(items);
		parent.setRenderMaster(true);
		tag.setParent(parent);
		parent.getIterator().next();
		tag.doTag();
		Assert.assertTrue(hasAttribute("data-master-detail-index", "0"));
		Assert.assertFalse(context.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
	}

	@Test
	public void masterWithBody() throws JspException, IOException {
		MasterDetailTag parent = new MasterDetailTag();
		Collection<String> items = new HashSet<>();
		items.add("one");
		parent.setItems(items);
		parent.setRenderMaster(true);
		tag.setParent(parent);
		parent.getIterator().next();
		tag.setJspBody(new StringJspFragment(context, "foo"));
		tag.doTag();
		Assert.assertTrue(hasAttribute("data-master-detail-index", "0"));
		Assert.assertTrue(hasBody("foo"));
		Assert.assertFalse(context.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
	}

	@Test
	public void masterWithGroup() throws JspException, IOException {
		MasterDetailTag parent = new MasterDetailTag();
		Collection<String> items = new HashSet<>();
		String one = "one";
		items.add(one);
		parent.setItems(items);
		parent.setRenderMaster(true);
		tag.setParent(parent);
		parent.getIterator().next();
		tag.setGroupExpression(ExpressionFactory.newInstance().createValueExpression(one, String.class));

		tag.doTag();
		Assert.assertTrue(Pattern.matches("<li class=\"nav-header\">one</li><li><a(.*)></a></li>", context.getOut()
				.getWriter().toString()));
		Assert.assertFalse(context.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
	}

	@Test
	public void masterWithGroupAndSameItem() throws JspException, IOException {
		MasterDetailTag parent = new MasterDetailTag();
		Collection<String> items = new LinkedList<>();
		String one = "one";
		items.add(one);
		items.add(one);
		parent.setItems(items);
		parent.setRenderMaster(true);
		tag.setParent(parent);
		parent.getIterator().next();
		tag.setGroupExpression(ExpressionFactory.newInstance().createValueExpression(one, String.class));
		tag.doTag();
		Assert.assertTrue(Pattern.matches("<li class=\"nav-header\">one</li><li><a(.*)></a></li>", context.getOut()
				.getWriter().toString()));
		Assert.assertEquals(one, context.getAttribute(ItemTag.PREVIOUS_GROUP_VALUE));
		parent.getIterator().next();
		tag.doTag();
		Assert.assertTrue(Pattern.matches("<li class=\"nav-header\">one</li><li><a(.*)></a></li><li><a(.*)></a></li>",
				context.getOut().getWriter().toString()));
		Assert.assertFalse(context.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
	}

	@Test
	public void detail() throws JspException, IOException {
		MasterDetailTag parent = new MasterDetailTag();
		Collection<String> items = new HashSet<>();
		items.add("one");
		parent.setItems(items);
		parent.setRenderMaster(false);
		tag.setParent(parent);
		parent.getIterator().next();
		tag.doTag();
		Assert.assertEquals(0, context.getOut().getWriter().getBuffer().length());
		Assert.assertFalse(context.getAttributeNamesInScope(PageContext.PAGE_SCOPE).hasMoreElements());
	}

	private boolean hasAttribute(String attribute, String value) {
		StringBuilder sb = new StringBuilder(100);
		sb.append("<li><a(.*)");
		sb.append(" ");
		sb.append(attribute);
		sb.append("=\"");
		sb.append(value);
		sb.append("\"(.*)>(.*)</a></li>");
		return Pattern.matches(sb.toString(), context.getOut().getWriter().toString());
	}

	private boolean hasBody(String body) {
		StringBuilder sb = new StringBuilder(100);
		sb.append("<li><a(.*)>");
		sb.append(body);
		sb.append("</a></li>");
		return Pattern.matches(sb.toString(), context.getOut().getWriter().toString());
	}
}
