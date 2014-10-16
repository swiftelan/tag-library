package com.swiftelan.tag;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * MessageProvider implementations resolve messages associated with a request for usage in the tag handlers.
 * <p>
 * A MessageProvider will be constructed for each request. Before being used {@link #init(HttpServletRequest)} will be invoked
 * with the request the provider will be handling. Implementations will typically be specific to a request processing framework
 * such as <a href="http://struts.apache.org">Struts</a>, <a href="http://www.stripesframework.org">Stripes</a> or <a
 * href="http://projects.spring.io/spring-webflow/">Spring Web Flow</a>.
 * </p>
 */
public interface MessageProvider {

	/**
	 * Initialize the provider with the current request. This will be called by the tag handler framework before any other method
	 * invocations.
	 * @param request Request the provider should use to resolve any messages.
	 */
	void init(HttpServletRequest request);

	/**
	 * Get error messages associated with specific a field. The key of the map is the name of the field the errors are associated
	 * with.
	 * @return Errors that are associated with fields.
	 */
	Map<String, Collection<String>> getFieldErrors();

	/**
	 * Get error messages that are not associated with a field.
	 * @return Errors that do not have an association with a field.
	 */
	Collection<String> getGlobalErrors();

	/**
	 * Get messages that are not associated with fields.
	 * @return Messages that do not have an association with a field.
	 */
	Collection<String> getMessages();
}
