package com.swiftelan.tag.util;

/**
 * Utility methods for strings
 */
public class Strings {

	private Strings() {}

	/**
	 * Replace the value of the named query parameter with the specified value. If multiple parameters exist for the name then
	 * they will be removed and one parameter will replace them.
	 * @param query Query string containing the parameters
	 * @param name Name of the parameter to replace
	 * @param value New value for the parameter
	 * @return Query string with the replaced parameter value.
	 */
	public static String replaceParameter(String query, String name, String value) {
		StringBuilder sb = new StringBuilder(query == null ? "" : query);
		String parameterName = name + "=";
		int start = sb.indexOf(parameterName);

		while (start != -1) {
			int end = sb.indexOf("&", start);
			if (end == -1) {
				end = sb.length();
			} else {
				end++;
			}

			sb.delete(start, end);
			start = sb.indexOf(parameterName);
		}

		if (sb.lastIndexOf("&") != sb.length() - 1) {
			sb.append("&");
		}
		sb.append(name).append("=").append(value);

		return sb.toString();
	}

	/**
	 * Find the string between '{' and '}'
	 * @param value String to search
	 * @return String between the characters or null if there is not an open and close brace
	 */
	public static String stringBetween(String value) {
		int indexOf = value.indexOf("{");
		if (indexOf == -1) {
			return null;
		}
		int lastIndexOf = value.lastIndexOf("}");
		if (lastIndexOf == -1) {
			return null;
		}
		return value.substring(indexOf + 1, lastIndexOf);
	}
}
