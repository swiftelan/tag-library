package com.swiftelan.tag;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Define HTML element attributes as Java methods.
 *
 */
public class ComponentTagSupportBeanInfo extends SimpleBeanInfo {
	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			Collection<PropertyDescriptor> descriptors = new ArrayList<>();

			// Redefine the 'class' getter so that Class<?> getClass() does not get called
			Method getter = ComponentTagSupport.class.getMethod("getCssClass");
			Method setter = ComponentTagSupport.class.getMethod("setCssClass", String.class);
			descriptors.add(new PropertyDescriptor("class", getter, setter));
			descriptors.add(new PropertyDescriptor("id", ComponentTagSupport.class));
			descriptors.add(new PropertyDescriptor("tabindex", ComponentTagSupport.class));

			PropertyDescriptor[] array = new PropertyDescriptor[descriptors.size()];
			return descriptors.toArray(array);
		} catch (Exception e) {
			// This shouldn't happen
			return null;
		}
	}
}
