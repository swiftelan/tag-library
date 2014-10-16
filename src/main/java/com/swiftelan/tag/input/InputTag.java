package com.swiftelan.tag.input;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import com.swiftelan.tag.ComponentTagSupport;
import com.swiftelan.tag.MessageProvider;

/**
 * InputTag is the tag handler responsible for rendering {@code <input>} elements. <h3>Boolean Attributes</h3>
 * <p>
 * HTML defines <a
 * href="http://www.whatwg.org/specs/web-apps/current-work/multipage/infrastructure.html#boolean-attribute">boolean attributes</a>
 * that are by their presence indicates a true value. The value of the attribute is not considered. This does not work well with
 * JSP and JSTL. JSP requires all tag attributes to have a value and JSTL cannot be used to generate the name of an attribute. All
 * boolean attributes are handled by only including the attribute if the value of the attribute is equal to the name of the
 * attribute or "true".
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
	 * Retrieve the {@link MessageProvider} for the current request. If a provider does not exist, create and initialize a new
	 * provider.
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
				Class<?> decoratorClass = Thread.currentThread().getContextClassLoader().loadClass(initParameter.trim());
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

	public void setAccept(String accept) {
		getAttributes().put("accept", accept);
	}

	public void setAutocomplete(String autocomplete) {
		getAttributes().put("autocomplete", autocomplete);
	}

	public void setAutofocus(String autofocus) {
		setBooleanAttribute("autofocus", autofocus);
	}

	public void setChecked(String checked) {
		setBooleanAttribute("checked", checked);
	}

	public void setDisabled(String disabled) {
		setBooleanAttribute("disabled", disabled);
	}

	public void setForm(String form) {
		getAttributes().put("form", form);
	}

	public void setFormaction(String formaction) {
		getAttributes().put("formaction", formaction);
	}

	public void setFormmethod(String formmethod) {
		getAttributes().put("formmethod", formmethod);
	}

	public void setFormnovalidate(String formnovalidate) {
		getAttributes().put("formnovalidate", formnovalidate);
	}

	public void setFormtarget(String formtarget) {
		getAttributes().put("formtarget", formtarget);
	}

	public void setInputmode(String inputmode) {
		getAttributes().put("inputmode", inputmode);
	}

	public void setList(String list) {
		getAttributes().put("list", list);
	}

	public void setMax(String max) {
		getAttributes().put("max", max);
	}

	public void setMaxlength(String maxlength) {
		getAttributes().put("maxlength", maxlength);
	}

	public void setMin(String min) {
		getAttributes().put("min", min);
	}

	public void setMultiple(String multiple) {
		setBooleanAttribute("multiple", multiple);
	}

	/**
	 * Set the name of the control that will be submitted with the value
	 * @param name Name of the control
	 */
	public void setName(String name) {
		getAttributes().put("name", name);
	}

	public void setPlaceholder(String placeholder) {
		getAttributes().put("placeholder", placeholder);
	}

	public void setPattern(String pattern) {
		getAttributes().put("pattern", pattern);
	}

	public void setReadonly(String readonly) {
		setBooleanAttribute("readonly", readonly);
	}

	/**
	 * Set the control to be required for form validation.
	 * <p>
	 * The required attribute is a boolean attribute. This method will add the attribute if the value is 'required' or 'true'
	 * </p>
	 * @param required If 'required' or 'true' the attribute will be added.
	 */
	public void setRequired(String required) {
		setBooleanAttribute("required", required);
	}

	public void setSize(String size) {
		getAttributes().put("size", size);
	}

	public void setSrc(String src) {
		getAttributes().put("src", src);
	}

	public void setStep(String step) {
		getAttributes().put("step", step);
	}

	/**
	 * The type of control
	 * @param type Type of the control
	 */
	public void setType(String type) {
		getAttributes().put("type", type);
	}

	/**
	 * Set the initial value of the control.
	 * @param value Value to populate the control when the document is loaded
	 */
	public void setValue(String value) {
		getAttributes().put("value", value);
	}

	String getAccept() {
		return getAttributes().get("accept");
	}

	String getAutocomplete() {
		return getAttributes().get("autocomplete");
	}

	String getAutofocus() {
		return getAttributes().get("autofocus");
	}

	String getChecked() {
		return getAttributes().get("checked");
	}

	String getDisabled() {
		return getAttributes().get("disabled");
	}

	String getForm() {
		return getAttributes().get("form");
	}

	String getFormaction() {
		return getAttributes().get("formaction");
	}

	String getFormmethod() {
		return getAttributes().get("formmethod");
	}

	String getFormnovalidate() {
		return getAttributes().get("formnovalidate");
	}

	String getFormtarget() {
		return getAttributes().get("formtarget");
	}

	String getInputmode() {
		return getAttributes().get("inputmode");
	}

	String getList() {
		return getAttributes().get("list");
	}

	String getMax() {
		return getAttributes().get("max");
	}

	String getMaxlength() {
		return getAttributes().get("maxlength");
	}

	String getMin() {
		return getAttributes().get("min");
	}

	String getMultiple() {
		return getAttributes().get("multiple");
	}

	String getName() {
		return getAttributes().get("name");
	}

	String getPlaceholder() {
		return getAttributes().get("placeholder");
	}

	String getPattern() {
		return getAttributes().get("pattern");
	}

	String getReadonly() {
		return getAttributes().get("readonly");
	}

	String getRequired() {
		return getAttributes().get("required");
	}

	String getSize() {
		return getAttributes().get("size");
	}

	String getSrc() {
		return getAttributes().get("src");
	}

	String getStep() {
		return getAttributes().get("step");
	}

	String getType() {
		return getAttributes().get("type");
	}

	String getValue() {
		return getAttributes().get("value");
	}

	protected void setBooleanAttribute(String attributeName, String value) {
		if (value != null && (attributeName.equals(value) || "true".equals(value))) {
			getAttributes().put(attributeName, "");
		} else {
			getAttributes().remove(attributeName);
		}
	}
}
