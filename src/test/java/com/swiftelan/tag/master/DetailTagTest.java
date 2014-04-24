package com.swiftelan.tag.master;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.swiftelan.tag.StringJspFragment;
import com.swiftelan.tag.TestJspContext;

public class DetailTagTest {
	private DetailTag tag;
	private TestJspContext context;

	@Before
	public void before() {
		context = new TestJspContext();
		tag = new DetailTag();
		tag.setJspContext(context);
	}

	@Test
	public void master() throws JspException, IOException {
		MasterDetailTag parent = new MasterDetailTag();
		parent.setJspContext(context);
		tag.setParent(parent);
		parent.setRenderMaster(true);
		tag.doTag();
		Assert.assertEquals(0, context.getOut().getWriter().getBuffer().length());
	}

	@Test
	public void detail() throws JspException, IOException {
		MasterDetailTag parent = new MasterDetailTag();
		Collection<String> items = new HashSet<>();
		String one = "one";
		items.add(one);
		parent.setItems(items);
		parent.setJspContext(context);
		tag.setParent(parent);
		parent.setRenderMaster(false);
		parent.getIterator().next();
		tag.doTag();
		Assert.assertTrue(hasAttribute("class", "hide"));
		Assert.assertTrue(hasAttribute("data-master-detail-index", "0"));
	}

	@Test
	public void detailWithClass() throws JspException, IOException {
		MasterDetailTag parent = new MasterDetailTag();
		Collection<String> items = new HashSet<>();
		String one = "one";
		items.add(one);
		parent.setItems(items);
		parent.setJspContext(context);
		tag.setParent(parent);
		parent.setRenderMaster(false);
		parent.getIterator().next();
		tag.setCssClass("foo");
		tag.doTag();
		Assert.assertTrue(hasAttribute("class", "foo hide"));
	}

	@Test
	public void detailWithBody() throws JspException, IOException {
		MasterDetailTag parent = new MasterDetailTag();
		Collection<String> items = new HashSet<>();
		String one = "one";
		items.add(one);
		parent.setItems(items);
		parent.setJspContext(context);
		tag.setParent(parent);
		parent.setRenderMaster(false);
		parent.getIterator().next();
		tag.setJspBody(new StringJspFragment(context, "foo"));
		tag.doTag();
		Assert.assertTrue(hasBody("foo"));
	}

	private boolean hasAttribute(String attribute, String value) {
		StringBuilder sb = new StringBuilder(100);
		sb.append("<div(.*)");
		sb.append(" ");
		sb.append(attribute);
		sb.append("=\"");
		sb.append(value);
		sb.append("\"(.*)>");
		return Pattern.matches(sb.toString(), context.getOut().getWriter().toString());
	}

	private boolean hasBody(String body) {
		StringBuilder sb = new StringBuilder(100);
		sb.append("<div(.*)>");
		sb.append(body);
		sb.append("</div>");
		return Pattern.matches(sb.toString(), context.getOut().getWriter().toString());
	}
}
