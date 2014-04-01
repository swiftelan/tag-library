package com.swiftelan.tag.input;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.swiftelan.tag.TestJspContext;

public class InputTagTest {
	private static ResourceBundle expected;

	private InputTag tag;
	private TestJspContext context;

	@BeforeClass
	public static void beforeClass() {
		expected = ResourceBundle.getBundle("com.swiftelan.tag.input.input-tag-test");
	}

	@Before
	public void before() {
		tag = new InputTag();
		context = new TestJspContext();
		tag.setJspContext(context);
		tag.setName("test1");
	}

	@Test
	public void requiredNull() {
		// Set the tag to the required state
		tag.setRequired("required");
		tag.setRequired(null);
		// Verify that the attribute is no longer required
		Assert.assertNull("A null value should remove the attribute", tag.getRequired());
	}

	@Test
	public void requiredFalse() {
		// Set the tag to the required state
		tag.setRequired("required");
		tag.setRequired("false");
		// Verify that the attribute is no longer required
		Assert.assertNull("A 'false' value should remove the attribute", tag.getRequired());
	}

	@Test
	public void requiredGiberish() {
		// Set the tag to the required state
		tag.setRequired("required");
		tag.setRequired("93jjske");
		// Verify that the attribute is no longer required
		Assert.assertNull("A 'false' value should remove the attribute", tag.getRequired());
	}

	@Test
	public void required() {
		String required = "required";
		tag.setRequired(required);
		Assert.assertEquals("A 'required' value should enable the attribute", required, tag.getRequired());
	}

	@Test
	public void requiredTrue() {
		tag.setRequired("true");
		Assert.assertEquals("A 'true' value should enable the attribute", "required", tag.getRequired());
	}

	@Test
	public void type() {
		String text = "text";
		tag.setType(text);
		Assert.assertEquals(text, tag.getType());
	}

	@Test
	public void autocompleteNull() {
		// Set the tag to the required state
		tag.setAutocomplete("autocomplete");
		tag.setAutocomplete(null);
		// Verify that the attribute is no longer required
		Assert.assertNull("A null value should remove the attribute", tag.getAutocomplete());
	}

	@Test
	public void autocompleteFalse() {
		// Set the tag to the required state
		tag.setAutocomplete("autocomplete");
		tag.setAutocomplete("false");
		// Verify that the attribute is no longer required
		Assert.assertNull("A 'false' value should remove the attribute", tag.getAutocomplete());
	}

	@Test
	public void autocompleteGiberish() {
		// Set the tag to the required state
		tag.setAutocomplete("autocomplete");
		tag.setAutocomplete("93jjske");
		// Verify that the attribute is no longer required
		Assert.assertNull("A 'false' value should remove the attribute", tag.getAutocomplete());
	}

	@Test
	public void autocomplete() {
		String autocomplete = "autocomplete";
		tag.setAutocomplete(autocomplete);
		Assert.assertEquals("A 'autocomplete' value should enable the attribute", autocomplete, tag.getAutocomplete());
	}

	@Test
	public void name() {
		String name = "test1";
		tag.setName(name);
		Assert.assertEquals(name, tag.getName());
	}

	@Test
	public void disabledNull() {
		// Set the tag to the required state
		tag.setDisabled("disabled");
		tag.setDisabled(null);
		// Verify that the attribute is no longer required
		Assert.assertNull("A null value should remove the attribute", tag.getDisabled());
	}

	@Test
	public void disabledFalse() {
		// Set the tag to the required state
		tag.setDisabled("disabled");
		tag.setDisabled("false");
		// Verify that the attribute is no longer required
		Assert.assertNull("A 'false' value should remove the attribute", tag.getDisabled());
	}

	@Test
	public void disabledGiberish() {
		// Set the tag to the required state
		tag.setDisabled("disabled");
		tag.setDisabled("93jjske");
		// Verify that the attribute is no longer required
		Assert.assertNull("A 'false' value should remove the attribute", tag.getDisabled());
	}

	@Test
	public void disabled() {
		String disabled = "disabled";
		tag.setDisabled(disabled);
		Assert.assertEquals("A 'disabled' value should enable the attribute", disabled, tag.getDisabled());
	}

	@Test
	public void disabledTrue() {
		tag.setDisabled("true");
		Assert.assertEquals("A 'true' value should enable the attribute", "disabled", tag.getDisabled());
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
	public void checkboxDefaultValue() throws JspException, IOException {
		tag.setType("checkbox");
		tag.doTag();
		Assert.assertEquals(expected.getString("default.value"), context.getOut().getWriter().toString());
	}

	@Test
	public void checkboxDefaultValueChecked() throws JspException, IOException {
		tag.setType("checkbox");
		tag.setChecked("true");
		tag.doTag();
		Assert.assertEquals(expected.getString("default.value.checked"), context.getOut().getWriter().toString());
	}

	@Test
	public void checkboxFooValue() throws JspException, IOException {
		tag.setType("checkbox");
		tag.setValue("foo");
		tag.doTag();
		Assert.assertEquals(expected.getString("foo.value"), context.getOut().getWriter().toString());
	}

	@Test
	public void checkboxfooValueChecked() throws JspException, IOException {
		tag.setType("checkbox");
		tag.setChecked("true");
		tag.setValue("foo");
		tag.doTag();
		Assert.assertEquals(expected.getString("foo.value.checked"), context.getOut().getWriter().toString());
	}
}
