package com.swiftelan.tag;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;

/**
 * JspFragment that writes a string when invoked
 *
 */
public class StringJspFragment extends JspFragment {
	private JspContext context;
	private String bodyContent;

	/**
	 * Initialize fragment that with a context and string
	 * 
	 * @param context Context to use for writing content
	 * @param bodyContent Content to include when invoked
	 */
	public StringJspFragment(JspContext context, String bodyContent) {
		this.context = context;
		this.bodyContent = bodyContent;
	}

	@Override
	public void invoke(Writer out) throws JspException, IOException {
		if (out == null) {
			context.getOut().append(bodyContent);
		} else {
			out.append(bodyContent);
		}
	}

	@Override
	public JspContext getJspContext() {
		return context;
	}

}
