package com.swiftelan.tag.input;

import java.io.IOException;

public class TestInputTagDecorator implements InputTagDecorator {

	private InputTag tag;

	@Override
	public void init(InputTag tag) {
		this.tag = tag;
	}

	@Override
	public void doBeforeTag() throws IOException {
		tag.getJspContext().getOut().append("before");
	}

	@Override
	public void doAfterTag() throws IOException {
		tag.getJspContext().getOut().append("after");
	}

}
