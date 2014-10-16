package com.swiftelan.tag.master;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.swiftelan.tag.TestJspContext;

@SuppressWarnings("javadoc")
public class MasterDetailTagTest {

	private MasterDetailTag tag;
	private TestJspContext context;

	@Before
	public void before() {
		tag = new MasterDetailTag();
		context = new TestJspContext();
		tag.setJspContext(context);
		tag.setJspId("master-detail-3894");
		tag.setListClass("list-unstyled");
	}

	@Test
	public void noItems() throws JspException, IOException {
		tag.doTag();
		Assert.assertTrue(listHasAttribute("data-master-detail", "master"));
		Assert.assertTrue(listHasAttribute("data-master-detail-id", "master-detail-3894"));
		Assert.assertTrue(listHasAttribute("class", "list-unstyled"));
	}

	@Test
	public void oneItem() throws JspException, IOException {
		Collection<String> items = new HashSet<>();
		String one = "one";
		items.add(one);
		tag.setItems(items);
		tag.setJspBody(new JspFragment() {
			private int i = 0;

			@Override
			public void invoke(Writer out) throws JspException, IOException {
				i++;
				context.setAttribute("items", Integer.valueOf(i));
			}

			@Override
			public JspContext getJspContext() {
				return null;
			}
		});
		tag.doTag();
		Assert.assertEquals(Integer.valueOf(items.size() * 2), context.getAttribute("items"));
	}

	private boolean listHasAttribute(String attribute, String value) {
		StringBuilder sb = new StringBuilder(100);
		sb.append("(.*)<ul(.*)");
		sb.append(" ");
		sb.append(attribute);
		sb.append("=\"");
		sb.append(value);
		sb.append("\"(.*)>(.*)");
		return Pattern.matches(sb.toString(), context.getOut().getWriter().toString());
	}
}
