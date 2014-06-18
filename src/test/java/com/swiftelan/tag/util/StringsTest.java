package com.swiftelan.tag.util;

import org.junit.Assert;
import org.junit.Test;

public class StringsTest {

	@Test
	public void replaceParameterNullQuery() {
		Assert.assertEquals("foo=bar", Strings.replaceParameter(null, "foo", "bar"));
	}

	@Test
	public void replaceParameterEmptyQuery() {
		Assert.assertEquals("foo=bar", Strings.replaceParameter("", "foo", "bar"));
	}

	@Test
	public void replaceParameterExistingValue() {
		Assert.assertEquals("foo=bar", Strings.replaceParameter("foo=1", "foo", "bar"));
	}

	@Test
	public void replaceParameterMultipleExistingValues() {
		Assert.assertEquals("foo=bar", Strings.replaceParameter("foo=1&foo=2", "foo", "bar"));
		Assert.assertEquals("name=foo&foo=bar", Strings.replaceParameter("foo=1&name=foo&foo=2", "foo", "bar"));
		Assert.assertEquals("name=foo&foo=bar", Strings.replaceParameter("foo=1&name=foo&foo=foo&foo=2", "foo", "bar"));
	}

	@Test
	public void replaceParameterNoExistingValue() {
		Assert.assertEquals("page=9&foo=bar", Strings.replaceParameter("page=9", "foo", "bar"));
	}
}
