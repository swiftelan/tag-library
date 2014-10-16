package com.swiftelan.tag;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTag;

/**
 * A JspFragment that invokes a SimpleTag.
 *
 * @see SimpleTag#doTag()
 */
public class SimpleTagJspFragment extends JspFragment {
	private SimpleTag tag;
	private JspContext context;

	/**
	 * Initialize the fragment with a context and tag.
	 * 
	 * @param context Context for the fragment
	 * @param tag The tag to invoke
	 */
	public SimpleTagJspFragment(JspContext context, SimpleTag tag) {
		this.tag = tag;
	}

	@Override
	public void invoke(Writer out) throws JspException, IOException {
		tag.doTag();
	}

	@Override
	public JspContext getJspContext() {
		return context;
	}

}
