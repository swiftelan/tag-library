package com.swiftelan.tag.util;

public class EscapeUtil {
	private static final String[] ESCAPES;

	static {
		int size = '>' + 1; // '>' is the largest escaped value
		ESCAPES = new String[size];
		ESCAPES['<'] = "&lt;";
		ESCAPES['>'] = "&gt;";
		ESCAPES['&'] = "&amp;";
		ESCAPES['\''] = "&#039;";
		ESCAPES['"'] = "&#034;";
	}

	private static String getEscape(char c) {
		if (c < ESCAPES.length) {
			return ESCAPES[c];
		}
		return null;
	}

	/**
	 * Escape a string.
	 *
	 * @param src
	 *            the string to escape; must not be null
	 * @return the escaped string
	 */
	public static String escape(String src) {
		// first pass to determine the length of the buffer so we only allocate once
		int length = 0;
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			String escape = getEscape(c);
			if (escape != null) {
				length += escape.length();
			} else {
				length += 1;
			}
		}

		// skip copy if no escaping is needed
		if (length == src.length()) {
			return src;
		}

		// second pass to build the escaped string
		StringBuilder buf = new StringBuilder(length);
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			String escape = getEscape(c);
			if (escape != null) {
				buf.append(escape);
			} else {
				buf.append(c);
			}
		}
		return buf.toString();
	}
}
