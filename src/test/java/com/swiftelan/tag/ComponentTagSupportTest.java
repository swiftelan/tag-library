package com.swiftelan.tag;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class ComponentTagSupportTest {
	private static Map<String, PropertyDescriptor> propertyDescriptors;
	private ComponentTagSupport tag;

	@BeforeClass
	public static void beforeClass() throws IntrospectionException {
		propertyDescriptors = new HashMap<>();
		BeanInfo beanInfo = Introspector.getBeanInfo(ComponentTagSupport.class);
		PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : descriptors) {
			propertyDescriptors.put(descriptor.getName(), descriptor);
		}
	}

	@Before
	public void before() {
		tag = new ComponentTagSupport();
	}

	@Test
	public void classSetter() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		PropertyDescriptor descriptor = propertyDescriptors.get("class");
		Assert.assertNotNull(descriptor);
		Assert.assertNotNull(descriptor.getReadMethod());
		Assert.assertNotNull(descriptor.getWriteMethod());
		String string = "a-class";
		descriptor.getWriteMethod().invoke(tag, string);
		Assert.assertEquals(string, descriptor.getReadMethod().invoke(tag));
	}

	@Test
	public void dynamicAttribute() throws JspException {
		String name = "data-toggle";
		String value = "foo";
		tag.setDynamicAttribute(null, name, value);
		Assert.assertEquals(value, tag.getAttributes().get(name));
	}

	@Test
	public void writeAttribute() throws IOException {
		String key = "href";
		String value = "http://example.com";
		tag.getAttributes().put(key, value);
		StringWriter writer = new StringWriter();
		tag.writeAttributes(writer, tag.getAttributes());
		Assert.assertEquals(" href=\"http://example.com\"", writer.getBuffer().toString());
	}

	@Test
	public void writeNullAttribute() throws IOException {
		String key = "href";
		String value = null;
		tag.getAttributes().put(key, value);
		StringWriter writer = new StringWriter();
		tag.writeAttributes(writer, tag.getAttributes());
		Assert.assertTrue(writer.getBuffer().toString().isEmpty());
	}

	@Test
	public void id() {
		String id = "foo";
		tag.setId(id);
		Assert.assertEquals(id, tag.getId());
	}
}
