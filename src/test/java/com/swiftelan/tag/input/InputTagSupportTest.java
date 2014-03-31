package com.swiftelan.tag.input;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InputTagSupportTest {

	private InputTagSupport tag;
	
	@Before
	public void before() {
		tag = new InputTagSupport();
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
}
