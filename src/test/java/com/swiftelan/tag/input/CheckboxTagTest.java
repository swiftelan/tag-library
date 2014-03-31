package com.swiftelan.tag.input;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.swiftelan.tag.TestJspContext;

public class CheckboxTagTest {
	private static ResourceBundle expected;
	
	private CheckboxTag tag;
	private TestJspContext context;
	
	@BeforeClass
	public static void beforeClass() {
		expected = ResourceBundle.getBundle("com.swiftelan.tag.input.checkbox-tag-test");
	}
	
	@Before
	public void before() {
		tag = new CheckboxTag();
		context = new TestJspContext();
		tag.setJspContext(context);
		tag.setName("test1");
	}
	
	@Test
	public void checkedNull() {
		// Set the tag to the checked state
		tag.setChecked("checked");
		tag.setChecked(null);
		// Verify that the attribute is no longer checked
		Assert.assertNull("A null value should remove the attribute", tag.getChecked());
	}
	
	@Test
	public void checkedFalse() {
		// Set the tag to the checked state
		tag.setChecked("required");
		tag.setChecked("false");
		// Verify that the attribute is no longer checked
		Assert.assertNull("A 'false' value should remove the attribute", tag.getChecked());
	}
	
	@Test
	public void checkedGiberish() {
		// Set the tag to the checked state
		tag.setChecked("required");
		tag.setChecked("93jjske");
		// Verify that the attribute is no longer checked
		Assert.assertNull("A 'false' value should remove the attribute", tag.getChecked());
	}
	
	@Test
	public void checked() {
		String checked = "checked";
		tag.setChecked(checked);
		Assert.assertEquals("A 'checked' value should enable the attribute", checked, tag.getChecked());
	}
	
	@Test
	public void checkedTrue() {
		tag.setChecked("true");
		Assert.assertEquals("A 'true' value should enable the attribute", "checked", tag.getChecked());
	}
	
	@Test
	public void defaultValue() throws JspException, IOException {
		tag.doTag();
		Assert.assertEquals(expected.getString("default.value"), context.getOut().getWriter().toString());
	}
	
	@Test
	public void defaultValueChecked() throws JspException, IOException {
		tag.setChecked("true");
		tag.doTag();
		Assert.assertEquals(expected.getString("default.value.checked"), context.getOut().getWriter().toString());
	}
	
	@Test
	public void fooValue() throws JspException, IOException {
		tag.setValue("foo");
		tag.doTag();
		Assert.assertEquals(expected.getString("foo.value"), context.getOut().getWriter().toString());
	}
	
	@Test
	public void fooValueChecked() throws JspException, IOException {
		tag.setChecked("true");
		tag.setValue("foo");
		tag.doTag();
		Assert.assertEquals(expected.getString("foo.value.checked"), context.getOut().getWriter().toString());
	}
}
