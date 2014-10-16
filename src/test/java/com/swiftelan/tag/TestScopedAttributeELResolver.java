package com.swiftelan.tag;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;

/**
 * ELResolver that accesses the page, request, session and application scopes in a {@link JspContext}.
 *
 * @see JspContext
 */
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
		if (context == null) {
			throw new NullPointerException();
		}

		if (base == null) {
			context.setPropertyResolved(true);
			return Object.class;
		}
		return null;
	}

	@Override
	public void setValue(ELContext context, Object base, Object property, Object value) {
		if (context == null) {
			throw new NullPointerException();
		}

		if (base == null) {
			context.setPropertyResolved(true);
			if (property instanceof String) {
				JspContext ctxt = (JspContext) context.getContext(JspContext.class);
				String attr = (String) property;
				if (ctxt.getAttribute(attr, PageContext.REQUEST_SCOPE) != null) {
					ctxt.setAttribute(attr, value, PageContext.REQUEST_SCOPE);
				} else if (ctxt.getAttribute(attr, PageContext.SESSION_SCOPE) != null) {
					ctxt.setAttribute(attr, value, PageContext.SESSION_SCOPE);
				} else if (ctxt.getAttribute(attr, PageContext.APPLICATION_SCOPE) != null) {
					ctxt.setAttribute(attr, value, PageContext.APPLICATION_SCOPE);
				} else {
					ctxt.setAttribute(attr, value, PageContext.PAGE_SCOPE);
				}
			}
		}
	}

	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property) {
		if (context == null) {
			throw new NullPointerException();
		}

		if (base == null) {
			context.setPropertyResolved(true);
		}
		return false;
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
		Enumeration<String> attrs;
		ArrayList<FeatureDescriptor> list = new ArrayList<>();
		JspContext ctxt = (JspContext) context.getContext(JspContext.class);

		attrs = ctxt.getAttributeNamesInScope(PageContext.PAGE_SCOPE);
		while (attrs.hasMoreElements()) {
			String name = attrs.nextElement();
			Object value = ctxt.getAttribute(name, PageContext.PAGE_SCOPE);
			FeatureDescriptor descriptor = new FeatureDescriptor();
			descriptor.setName(name);
			descriptor.setDisplayName(name);
			descriptor.setShortDescription("page scope attribute");
			descriptor.setExpert(false);
			descriptor.setHidden(false);
			descriptor.setPreferred(true);
			descriptor.setValue("type", value.getClass());
			descriptor.setValue("resolvableAtDesignTime", Boolean.TRUE);
			list.add(descriptor);
		}

		attrs = ctxt.getAttributeNamesInScope(PageContext.REQUEST_SCOPE);
		while (attrs.hasMoreElements()) {
			String name = attrs.nextElement();
			Object value = ctxt.getAttribute(name, PageContext.REQUEST_SCOPE);
			FeatureDescriptor descriptor = new FeatureDescriptor();
			descriptor.setName(name);
			descriptor.setDisplayName(name);
			descriptor.setShortDescription("request scope attribute");
			descriptor.setExpert(false);
			descriptor.setHidden(false);
			descriptor.setPreferred(true);
			descriptor.setValue("type", value.getClass());
			descriptor.setValue("resolvableAtDesignTime", Boolean.TRUE);
			list.add(descriptor);
		}

		attrs = ctxt.getAttributeNamesInScope(PageContext.SESSION_SCOPE);
		while (attrs.hasMoreElements()) {
			String name = attrs.nextElement();
			Object value = ctxt.getAttribute(name, PageContext.SESSION_SCOPE);
			FeatureDescriptor descriptor = new FeatureDescriptor();
			descriptor.setName(name);
			descriptor.setDisplayName(name);
			descriptor.setShortDescription("session scope attribute");
			descriptor.setExpert(false);
			descriptor.setHidden(false);
			descriptor.setPreferred(true);
			descriptor.setValue("type", value.getClass());
			descriptor.setValue("resolvableAtDesignTime", Boolean.TRUE);
			list.add(descriptor);
		}

		attrs = ctxt.getAttributeNamesInScope(PageContext.APPLICATION_SCOPE);
		while (attrs.hasMoreElements()) {
			String name = attrs.nextElement();
			Object value = ctxt.getAttribute(name, PageContext.APPLICATION_SCOPE);
			FeatureDescriptor descriptor = new FeatureDescriptor();
			descriptor.setName(name);
			descriptor.setDisplayName(name);
			descriptor.setShortDescription("application scope attribute");
			descriptor.setExpert(false);
			descriptor.setHidden(false);
			descriptor.setPreferred(true);
			descriptor.setValue("type", value.getClass());
			descriptor.setValue("resolvableAtDesignTime", Boolean.TRUE);
			list.add(descriptor);
		}
		return list.iterator();
	}

	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		if (base == null) {
			return String.class;
		}
		return null;
	}

}
