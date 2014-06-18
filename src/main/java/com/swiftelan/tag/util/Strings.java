package com.swiftelan.tag.util;

public class Strings {
	private Strings() {
	}

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
