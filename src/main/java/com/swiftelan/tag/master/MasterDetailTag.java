package com.swiftelan.tag.master;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspIdConsumer;

import com.swiftelan.tag.LoopTagSupport;

/**
 * Tag handler that controls the rendering of a master-detail component.
 * <p>
 * The master-detail component consists of a list of items rendered by the {@link ItemTag} and corresponding detail sections
 * rendered by the {@link DetailTag}. The list of items is typically a single attribute of the object such as a name. The
 * detail section contains more information about the object. The list of items and detail sections are wrapped in container
 * elements that can be styled by using {@link #setListContainerClass(String)} and {@link #setDetailContainerClass(String)}.
 * </p>
 *
 * @see DetailTag
 * @see ItemTag
 */
public class MasterDetailTag extends LoopTagSupport implements JspIdConsumer {

	private String jspId;
	private String listClass;
	private String listContainerClass;
	private String detailContainerClass;
	private boolean renderMaster;
	private boolean renderHeader;

	@Override
	public void doTag() throws JspException, IOException {
		Map<String, String> attributes = new HashMap<>();
		attributes.put("class", listContainerClass);
		start("div", attributes);
		doMaster();
		end("div");

		reset();
		attributes.clear();
		attributes.put("data-master-detail-id", jspId);
		attributes.put("data-master-detail", "detail");
		attributes.put("class", detailContainerClass);
		start("div", attributes);
		doDetail();
		end("div");
	}

	private void doMaster() throws IOException, JspException {
		renderMaster = true;
		renderHeader = true;

		if (getJspBody() != null) {
			getJspBody().invoke(null);
		}
		renderHeader = false;

		Map<String, String> attributes = new HashMap<>();
		attributes.put("data-master-detail-id", jspId);
		attributes.put("data-master-detail", "master");
		attributes.put("class", listClass);
		start("ul", attributes);
		while (getIterator().hasNext()) {
			getIterator().next();
			if (getJspBody() != null) {
				getJspBody().invoke(null);
			}
		}
		end("ul");
	}

	private void doDetail() throws JspException, IOException {
		renderMaster = false;
		renderHeader = true;
		if (getJspBody() != null) {
			getJspBody().invoke(null);
		}
		renderHeader = false;
		while (getIterator().hasNext()) {
			getIterator().next();
			if (getJspBody() != null) {
				getJspBody().invoke(null);
			}
		}
	}

	boolean isRenderMaster() {
		return renderMaster;
	}

	void setRenderMaster(boolean renderMaster) {
		this.renderMaster = renderMaster;
	}

	boolean isRenderHeader() {
		return renderHeader;
	}

	void setRenderHeader(boolean renderHeader) {
		this.renderHeader = renderHeader;
	}

	@Override
	public void setJspId(String id) {
		jspId = id;
	}

	/**
	 * Set the HTML {@code class} attribute of the {@code
	 *
	<ul>
	 * } element that is the parent of items in the master list.
	 *
	 * @param listClass HTML {@code class} attribute value
	 */
	public void setListClass(String listClass) {
		this.listClass = listClass;
	}

	/**
	 * Set the HTML {@code class} attribute of the {@code <div>} element that is the parent of the master list.
	 *
	 * @param listContainerClass HTML {@code class} attribute value
	 */
	public void setListContainerClass(String listContainerClass) {
		this.listContainerClass = listContainerClass;
	}

	/**
	 * Set the HTML {@code class} attribute of the {@code <div>} element that is the parent of the detail sections.
	 *
	 * @param detailContainerClass HTML {@code class} attribute value
	 */
	public void setDetailContainerClass(String detailContainerClass) {
		this.detailContainerClass = detailContainerClass;
	}
}
