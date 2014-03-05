package com.swiftelan.tag.table;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.swiftelan.tag.StringJspFragment;
import com.swiftelan.tag.TestJspContext;

public class ColumnTagTest {
	private static ResourceBundle expected;
	
	private ColumnTag tag;
	private TestJspContext jspContext;
	private TableTag table;
	
	@BeforeClass
	public static void beforeClass() {
		expected = ResourceBundle.getBundle("com.swiftelan.tag.table.column-test");
	}
	
	@Before
	public void before() {
		tag = new ColumnTag();
		jspContext = new TestJspContext();
		table = new TableTag();
		table.setJspContext(jspContext);
		tag.setJspContext(jspContext);
		tag.setParent(table);
	}
	
	@Test
	public void header() throws JspException, IOException {
		table.setRenderHeader(true);
		tag.setHeader("header");
		tag.doTag();
		Assert.assertEquals(expected.getString("header"), jspContext.getOut().getWriter().toString());
	}
	
	@Test
	public void headerBody() throws JspException, IOException {
		table.setRenderHeader(true);
		JspFragment headerBody = new StringJspFragment(jspContext, "<span>header</span>");
		tag.setHeaderBody(headerBody);
		tag.doTag();
		Assert.assertEquals(expected.getString("header.body"), jspContext.getOut().getWriter().toString());
	}
	
	@Test
	public void tableData() throws JspException, IOException {
		JspFragment body = new StringJspFragment(jspContext, "table.data");
		tag.setJspBody(body);
		tag.doTag();
		Assert.assertEquals(expected.getString("table.data"), jspContext.getOut().getWriter().toString());
	}
}
