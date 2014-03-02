package com.swiftelan.tag.table;

import java.io.IOException;
import java.io.Writer;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
	public void tableData() throws JspException, IOException {
		JspFragment body = new JspFragment() {
			
			@Override
			public void invoke(Writer out) throws JspException, IOException {
				if (out== null) {
					getJspContext().getOut().append("table.data");
				} else {
					out.append("table.data");
				}
			}
			
			@Override
			public JspContext getJspContext() {
				return jspContext;
			}
		};
		tag.setJspBody(body);
		tag.doTag();
		Assert.assertEquals(expected.getString("table.data"), jspContext.getOut().getWriter().toString());
	}
}
