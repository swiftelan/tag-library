package com.swiftelan.tag;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class ComponentTagSupportBeanInfo extends SimpleBeanInfo {
	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			Collection<PropertyDescriptor> descriptors = new ArrayList<>();

			// Redefine the 'class' getter so that Class<?> getClass() does not get called
			Method getter = ComponentTagSupport.class.getMethod("getCssClass");
			Method setter = ComponentTagSupport.class.getMethod("setCssClass", String.class);
			descriptors.add(new PropertyDescriptor("class", getter, setter));

//			descriptors.add(new PropertyDescriptor("id", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("title", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("style", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("dir", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("lang", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("tabindex", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("accesskey", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("onfocus", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("onblur", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("onselect", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("onchange", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("onclick", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("ondblclick", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("onmousedown", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("onmouseup", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("onmouseover", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("onmousemove", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("onmouseout", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("onkeypress", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("onkeydown", ComponentTagSupport.class));
//			descriptors.add(new PropertyDescriptor("onkeyup", ComponentTagSupport.class));

			PropertyDescriptor[] array = new PropertyDescriptor[descriptors.size()];
			return descriptors.toArray(array);
		} catch (Exception e) {
			// This shouldn't happen
			return null;
		}
	}
}
