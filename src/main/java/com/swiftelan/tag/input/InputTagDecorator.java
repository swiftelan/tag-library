package com.swiftelan.tag.input;

import java.io.IOException;

public interface InputTagDecorator {

	void init(InputTag tag);

	void doBeforeTag() throws IOException;

	void doAfterTag() throws IOException;
}
