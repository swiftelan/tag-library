package com.swiftelan.tag;

import java.io.IOException;
import java.io.Writer;

/**
 * Writer that ignores content passed to it.
 * <p>
 * All methods do nothing.
 * </p>
 */
public class NullWriter extends Writer {

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		// Do nothing
	}

	@Override
	public void flush() throws IOException {
		// Do nothing
	}

	@Override
	public void close() throws IOException {
		// Do nothing
	}

}
