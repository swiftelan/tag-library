package com.swiftelan.tag.input;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import com.swiftelan.tag.ComponentTagSupport;
import com.swiftelan.tag.MessageProvider;

/**
 * InputTag is the tag handler responsible for rendering {@code <input>} elements. <h3>Boolean Attributes</h3>
 * <p>
 * HTML defines <a
 * href="http://www.whatwg.org/specs/web-apps/current-work/multipage/infrastructure.html#boolean-attribute">boolean
 * attributes</a> that are by their presence indicates a true value. The value of the attribute is not considered. This
 * does not work well with JSP and JSTL. JSP requires all tag attributes to have a value and JSTL cannot be used to
 * generate the name of an attribute. All boolean attributes are handled by only including the attribute if the value of
 * the attribute is equal to the name of the attribute or "true".
 * </p>
 */
public class InputTag extends ComponentTagSupport {

	static final String MESSAGE_PROVIDER_ATTR = "com.swiftelan.tag.MessageProvider";
	static final String INPUT_TAG_DECORATOR = "com.swiftelan.tag.input.InputTagDecorator";

	@Override
	public void doTag() throws JspException, IOException {
		InputTagDecorator tagDecorator = getTagDecorator();

		if (tagDecorator != null) {
			tagDecorator.doBeforeTag();
		}
		start("input", getAttributes());
		if (tagDecorator != null) {
			tagDecorator.doAfterTag();
		}

	}

	/**
	 * Retrieve the {@link MessageProvider} for the current request. If a provider does not exist, create and initialize
	 * a new provider.
	 *
	 * @return A provider or {@code null} if no provider was configured.
	 * @throws JspTagException If an error occurs during the creation of a new provider.
	 */
	public MessageProvider getMessageProvider() throws JspTagException {
		String initParameter = getRequest().getServletContext().getInitParameter(MESSAGE_PROVIDER_ATTR);
		if (initParameter != null && getRequest().getAttribute(MESSAGE_PROVIDER_ATTR) == null) {
			try {
				Class<?> providerClass = Thread.currentThread().getContextClassLoader().loadClass(initParameter.trim());
				MessageProvider messageProvider = (MessageProvider) providerClass.newInstance();
				messageProvider.init(getRequest());
				getRequest().setAttribute(MESSAGE_PROVIDER_ATTR, messageProvider);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				throw new JspTagException(e);
			}
		}

		return (MessageProvider) getRequest().getAttribute(MESSAGE_PROVIDER_ATTR);
	}

	InputTagDecorator getTagDecorator() throws JspTagException {
		String initParameter = getRequest().getServletContext().getInitParameter(INPUT_TAG_DECORATOR);
		if (initParameter != null) {
			try {
				Class<?> decoratorClass = Thread.currentThread().getContextClassLoader()
						.loadClass(initParameter.trim());
				InputTagDecorator decorator = (InputTagDecorator) decoratorClass.newInstance();
				decorator.init(this);
				return decorator;
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				throw new JspTagException(e);
			}
		}
		return null;
	}

	@Override
	public JspContext getJspContext() {
		return super.getJspContext();
	}

	/**
	 * Provide the user agent with a list of acceptable file types.
	 *
	 * @param accept Comma separated list of MIME types the server will accept for this file input.
	 */
	public void setAccept(String accept) {
		getAttributes().put("accept", accept);
	}

	/**
	 * Specify the element allows the user agent to fill the value for the control for the user.
	 *
	 * @param autocomplete Value for the attribute, usually "yes" or "no".
	 */
	public void setAutocomplete(String autocomplete) {
		getAttributes().put("autocomplete", autocomplete);
	}

	/**
	 * Specify the element will have the focus when the document is loaded. Boolean attribute.
	 *
	 * @param autofocus "autofocus" and "true" values will enable the attribute.
	 */
	public void setAutofocus(String autofocus) {
		setBooleanAttribute("autofocus", autofocus);
	}

	/**
	 * Specify the element will be checked when the document is loaded. Boolean attribute.
	 *
	 * @param checked "checked" and "true" values will enable the attribute.
	 */
	public void setChecked(String checked) {
		setBooleanAttribute("checked", checked);
	}

	/**
	 * Specify the element will be disabled when the document is loaded. Boolean attribute.
	 *
	 * @param disabled "disabled" and "true" values will enable the attribute.
	 */
	public void setDisabled(String disabled) {
		setBooleanAttribute("disabled", disabled);
	}

	/**
	 * Value of the id attribute of the form the element belongs to.
	 *
	 * @param form id attribute of the form.
	 */
	public void setForm(String form) {
		getAttributes().put("form", form);
	}

	/**
	 * URL to use for form submission.
	 *
	 * @param formaction URL to submit the form to.
	 */
	public void setFormaction(String formaction) {
		getAttributes().put("formaction", formaction);
	}

	/**
	 * HTTP method to use for the request that submits the form.
	 *
	 * @param formmethod HTTP method. {@code GET} or {@code POST}
	 */
	public void setFormmethod(String formmethod) {
		getAttributes().put("formmethod", formmethod);
	}

	/**
	 * If the element is a submit button, this attribute specifies the form should not be validated on submission.
	 * Boolean attribute.
	 *
	 * @param formnovalidate "formnovalidate" and "true" values will enable the attribute.
	 */
	public void setFormnovalidate(String formnovalidate) {
		setBooleanAttribute("formnovalidate", formnovalidate);
	}

	/**
	 * If the element is a submit button this attribute controls where the response is loaded.
	 *
	 * @param formtarget Name or keyword that specifies the context to load the response.
	 */
	public void setFormtarget(String formtarget) {
		getAttributes().put("formtarget", formtarget);
	}

	/**
	 * Hint to the user agent for which keyboard to display.
	 *
	 * @param inputmode Type of keyboard to display while entering data.
	 */
	public void setInputmode(String inputmode) {
		getAttributes().put("inputmode", inputmode);
	}

	/**
	 * Pre-defined options displayed to the user.
	 *
	 * @param list Value of the id attribute of the {@code <datalist>} element with the options.
	 */
	public void setList(String list) {
		getAttributes().put("list", list);
	}

	/**
	 * Set the maximum value for the element.
	 *
	 * @param max Maximum value for the element.
	 */
	public void setMax(String max) {
		getAttributes().put("max", max);
	}

	/**
	 * Set the maximum number of characters the user can enter.
	 *
	 * @param maxlength Maximum number of characters for the element.
	 */
	public void setMaxlength(String maxlength) {
		getAttributes().put("maxlength", maxlength);
	}

	/**
	 * Set the minimum value of the element.
	 *
	 * @param min Minimum value of the element.
	 */
	public void setMin(String min) {
		getAttributes().put("min", min);
	}

	/**
	 * Set the minimum number of characters the user can enter.
	 *
	 * @param minlength minimum number of characters for the element.
	 */
	public void setMinlength(String minlength) {
		getAttributes().put("minlength", minlength);
	}

	/**
	 * Indicate that this element can have more than one value. Boolean attribute.
	 *
	 * @param multiple "multiple" or "true" to enable this attribute.
	 */
	public void setMultiple(String multiple) {
		setBooleanAttribute("multiple", multiple);
	}

	/**
	 * Set the name of the control that will be submitted with the value
	 *
	 * @param name Name of the control
	 */
	public void setName(String name) {
		getAttributes().put("name", name);
	}

	/**
	 * Regular expression that describes value values for the element.
	 *
	 * @param pattern Regular expression used to validate the element's value.
	 */
	public void setPattern(String pattern) {
		getAttributes().put("pattern", pattern);
	}

	/**
	 * Hint displayed to the user when the element has no value.
	 *
	 * @param placeholder Text to display when the element has no value.
	 */
	public void setPlaceholder(String placeholder) {
		getAttributes().put("placeholder", placeholder);
	}

	/**
	 * Specify that the control cannot be modified by the user. Boolean attribute.
	 *
	 * @param readonly "readonly" or "true" to enable this attribute.
	 */
	public void setReadonly(String readonly) {
		setBooleanAttribute("readonly", readonly);
	}

	/**
	 * Set the control to be required for form validation.
	 * <p>
	 * The required attribute is a boolean attribute. This method will add the attribute if the value is 'required' or
	 * 'true'
	 * </p>
	 *
	 * @param required If 'required' or 'true' the attribute will be added.
	 */
	public void setRequired(String required) {
		setBooleanAttribute("required", required);
	}

	/**
	 * Set the size of the element.
	 *
	 * @param size Size of the element.
	 */
	public void setSize(String size) {
		getAttributes().put("size", size);
	}

	/**
	 * URI for the location of the image to display.
	 *
	 * @param src URI of the image.
	 */
	public void setSrc(String src) {
		getAttributes().put("src", src);
	}

	/**
	 * Limit the values accepted by the control.
	 *
	 * @param step Value the control must be a multiple of.
	 */
	public void setStep(String step) {
		getAttributes().put("step", step);
	}

	/**
	 * The type of control
	 *
	 * @param type Type of the control
	 */
	public void setType(String type) {
		getAttributes().put("type", type);
	}

	/**
	 * Set the initial value of the control.
	 *
	 * @param value Value to populate the control when the document is loaded
	 */
	public void setValue(String value) {
		getAttributes().put("value", value);
	}

	public String getAccept() {
		return getAttributes().get("accept");
	}

	public String getAutocomplete() {
		return getAttributes().get("autocomplete");
	}

	public String getAutofocus() {
		return getAttributes().get("autofocus");
	}

	public String getChecked() {
		return getAttributes().get("checked");
	}

	public String getDisabled() {
		return getAttributes().get("disabled");
	}

	public String getForm() {
		return getAttributes().get("form");
	}

	public String getFormaction() {
		return getAttributes().get("formaction");
	}

	public String getFormmethod() {
		return getAttributes().get("formmethod");
	}

	public String getFormnovalidate() {
		return getAttributes().get("formnovalidate");
	}

	public String getFormtarget() {
		return getAttributes().get("formtarget");
	}

	public String getInputmode() {
		return getAttributes().get("inputmode");
	}

	public String getList() {
		return getAttributes().get("list");
	}

	public String getMax() {
		return getAttributes().get("max");
	}

	public String getMaxlength() {
		return getAttributes().get("maxlength");
	}

	public String getMin() {
		return getAttributes().get("min");
	}

	public String getMultiple() {
		return getAttributes().get("multiple");
	}

	public String getName() {
		return getAttributes().get("name");
	}

	public String getPlaceholder() {
		return getAttributes().get("placeholder");
	}

	public String getPattern() {
		return getAttributes().get("pattern");
	}

	public String getReadonly() {
		return getAttributes().get("readonly");
	}

	public String getRequired() {
		return getAttributes().get("required");
	}

	public String getSize() {
		return getAttributes().get("size");
	}

	public String getSrc() {
		return getAttributes().get("src");
	}

	public String getStep() {
		return getAttributes().get("step");
	}

	public String getType() {
		return getAttributes().get("type");
	}

	public String getValue() {
		return getAttributes().get("value");
	}

	@Override
	public Map<String, String> getAttributes() {
		return super.getAttributes();
	}

	protected void setBooleanAttribute(String attributeName, String value) {
		if (value != null && (attributeName.equals(value) || "true".equals(value))) {
			getAttributes().put(attributeName, "");
		} else {
			getAttributes().remove(attributeName);
		}
	}

}
