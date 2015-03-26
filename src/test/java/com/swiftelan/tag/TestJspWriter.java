package com.swiftelan.tag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspWriter;

/**
 * This class implements a JspWriter that collects the data passed to it in a string.
 * <p>
 * This class uses a {@link StringWriter} to collect the data as a string.
 * </p>
 * 
 * @see StringBuffer
 * @see StringWriter
 */
public class TestJspWriter extends JspWriter {

	private StringWriter delegate;

	/**
	 * Initialize an empty writer
	 */
	public TestJspWriter() {
		this(200, false);
	}

	protected TestJspWriter(int bufferSize, boolean autoFlush) {
		super(bufferSize, autoFlush);
		delegate = new StringWriter(bufferSize);
	}

	/**
	 * Get the writer containing the content
	 * 
	 * @return Writer with the content.
	 */
	public StringWriter getWriter() {
		return delegate;
	}

	@Override
	public void newLine() throws IOException {
		delegate.append(System.lineSeparator());
	}

	@Override
	public void print(boolean b) throws IOException {
		delegate.append(String.valueOf(b));
	}

	@Override
	public void print(char c) throws IOException {
		delegate.append(String.valueOf(c));
	}

	@Override
	public void print(int i) throws IOException {
		delegate.append(String.valueOf(i));
	}

	@Override
	public void print(long l) throws IOException {
		delegate.append(String.valueOf(l));
	}

	@Override
	public void print(float f) throws IOException {
		delegate.append(String.valueOf(f));
	}

	@Override
	public void print(double d) throws IOException {
		delegate.append(String.valueOf(d));
	}

	@Override
	public void print(char[] s) throws IOException {
		delegate.write(s);
	}

	@Override
	public void print(String s) throws IOException {
		delegate.append(String.valueOf(s));
	}

	@Override
	public void print(Object obj) throws IOException {
		delegate.append(String.valueOf(obj));
	}

	@Override
	public void println() throws IOException {
		delegate.append(System.lineSeparator());
	}

	@Override
	public void println(boolean x) throws IOException {
		print(x);
		println();
	}

	@Override
	public void println(char x) throws IOException {
		print(x);
		println();
	}

	@Override
	public void println(int x) throws IOException {
		print(x);
		println();
	}

	@Override
	public void println(long x) throws IOException {
		print(x);
		println();
	}

	@Override
	public void println(float x) throws IOException {
		print(x);
		println();
	}

	@Override
	public void println(double x) throws IOException {
		print(x);
		println();
	}

	@Override
	public void println(char[] x) throws IOException {
		print(x);
		println();
	}

	@Override
	public void println(String x) throws IOException {
		print(x);
		println();
	}

	@Override
	public void println(Object x) throws IOException {
		print(x);
		println();
	}

	@Override
	public void clear() throws IOException {
		delegate = new StringWriter();
	}

	@Override
	public void clearBuffer() throws IOException {
		delegate = new StringWriter();
	}

	@Override
	public void flush() throws IOException {
		delegate.flush();
	}

	@Override
	public void close() throws IOException {
		delegate.close();
	}

	@Override
	public int getRemaining() {
		return delegate.getBuffer().capacity();
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		delegate.write(cbuf, off, len);
	}

	@Override
	public String toString() {
		return delegate.toString();
	}
}
