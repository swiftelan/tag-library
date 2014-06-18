package com.swiftelan.tag;

import java.beans.FeatureDescriptor;
import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.servlet.jsp.JspContext;

public class TestScopedAttributeELResolver extends ELResolver {

	@Override
	public Object getValue(ELContext context, Object base, Object property) {
		if (context == null) {
			throw new NullPointerException();
		}

		if (base == null) {
			context.setPropertyResolved(true);
			if (property instanceof String) {
				String attribute = (String) property;
				JspContext ctxt = (JspContext) context.getContext(JspContext.class);
				return ctxt.findAttribute(attribute);
			}
		}
		return null;
	}

	@Override
	public Class<?> getType(ELContext context, Object base, Object property) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(ELContext context, Object base, Object property, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		// TODO Auto-generated method stub
		return null;
	}

}
