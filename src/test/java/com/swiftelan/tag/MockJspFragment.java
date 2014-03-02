package com.swiftelan.tag;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;

public class MockJspFragment extends JspFragment {
	private JspContext context;
	
	public MockJspFragment(JspContext context) {
		this.context = context;
	}

	@Override
	public void invoke(Writer out) throws JspException, IOException {
		// No-op
	}

	@Override
	public JspContext getJspContext() {
		return context;
	}

}
