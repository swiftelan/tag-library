package com.swiftelan.tag.input;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;

import org.hamcrest.core.StringEndsWith;
import org.hamcrest.core.StringStartsWith;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.swiftelan.tag.TestJspContext;

@SuppressWarnings("javadoc")
public class InputTagTest {

	private InputTag tag;
	private TestJspContext context;

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
		Assert.assertEquals("A 'required' value should enable the attribute", "", tag.getRequired());
	}

	@Test
	public void requiredTrue() {
		tag.setRequired("true");
		Assert.assertEquals("A 'true' value should enable the attribute", "", tag.getRequired());
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
	public void autocomplete() {
		String autocomplete = "name";
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
		Assert.assertEquals("A 'disabled' value should enable the attribute", "", tag.getDisabled());
	}

	@Test
	public void disabledTrue() {
		tag.setDisabled("true");
		Assert.assertEquals("A 'true' value should enable the attribute", "", tag.getDisabled());
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
		Assert.assertEquals("A 'checked' value should enable the attribute", "", tag.getChecked());
	}

	@Test
	public void checkedTrue() {
		tag.setChecked("true");
		Assert.assertEquals("A 'true' value should enable the attribute", "", tag.getChecked());
	}

	@Test
	public void checkboxDefaultValue() throws JspException, IOException {
		tag.setType("checkbox");
		tag.doTag();
		Assert.assertTrue(hasAttribute("type", tag.getType()));
		Assert.assertTrue(hasAttribute("name", tag.getName()));
	}

	@Test
	public void checkboxDefaultValueChecked() throws JspException, IOException {
		tag.setType("checkbox");
		tag.setChecked("true");
		tag.doTag();
		Assert.assertTrue(hasAttribute("type", tag.getType()));
		Assert.assertTrue(hasAttribute("checked", ""));
		Assert.assertTrue(hasAttribute("name", tag.getName()));
	}

	@Test
	public void checkboxFooValue() throws JspException, IOException {
		tag.setType("checkbox");
		tag.setValue("foo");
		tag.doTag();
		Assert.assertTrue(hasAttribute("type", tag.getType()));
		Assert.assertTrue(hasAttribute("name", tag.getName()));
		Assert.assertTrue(hasAttribute("value", tag.getValue()));
	}

	@Test
	public void checkboxFooValueChecked() throws JspException, IOException {
		tag.setType("checkbox");
		tag.setChecked("true");
		tag.setValue("foo");
		tag.doTag();
		Assert.assertTrue(hasAttribute("type", "checkbox"));
		Assert.assertTrue(hasAttribute("checked", ""));
		Assert.assertTrue(hasAttribute("name", tag.getName()));
		Assert.assertTrue(hasAttribute("value", "foo"));
	}

	@Test
	public void accept() throws JspException, IOException {
		tag.setAccept(".jpg");
		tag.doTag();
		Assert.assertTrue(hasAttribute("accept", ".jpg"));
	}

	@Test
	public void autofocus() throws JspException, IOException {
		tag.setAutofocus("autofocus");
		tag.doTag();
		Assert.assertTrue(hasAttribute("autofocus", ""));
	}

	@Test
	public void autofocusTrue() throws JspException, IOException {
		tag.setAutofocus("true");
		tag.doTag();
		Assert.assertTrue(hasAttribute("autofocus", ""));
	}

	@Test
	public void form() throws JspException, IOException {
		tag.setForm("someform");
		tag.doTag();
		Assert.assertTrue(hasAttribute("form", "someform"));
	}

	@Test
	public void formaction() throws JspException, IOException {
		String value = "avalue";
		tag.setFormaction(value);
		tag.doTag();
		Assert.assertTrue(hasAttribute("formaction", value));
	}

	@Test
	public void formmethod() throws JspException, IOException {
		String value = "avalue";
		tag.setFormmethod(value);
		tag.doTag();
		Assert.assertTrue(hasAttribute("formmethod", value));
	}

	@Test
	public void tagDecorator() throws JspException, IOException {
		Mockito.when(context.getServletContext().getInitParameter(InputTag.INPUT_TAG_DECORATOR)).thenReturn(TestInputTagDecorator.class.getName());
		tag.doTag();
		Assert.assertThat(context.getOut().toString(), new StringStartsWith("before"));
		Assert.assertThat(context.getOut().toString(), new StringEndsWith("after"));
	}

	private boolean hasAttribute(String attribute, String value) {
		StringBuilder sb = new StringBuilder(100);
		sb.append("<input(.*)");
		sb.append(" ");
		sb.append(attribute);
		sb.append("=\"");
		sb.append(value);
		sb.append("\"(.*)>");
		return Pattern.matches(sb.toString(), context.getOut().getWriter().toString());
	}
}
