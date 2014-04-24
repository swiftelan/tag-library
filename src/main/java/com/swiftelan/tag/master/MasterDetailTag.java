package com.swiftelan.tag.master;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspIdConsumer;

import com.swiftelan.tag.LoopTagSupport;

public class MasterDetailTag extends LoopTagSupport implements JspIdConsumer {

	private String jspId;
	private String listClass;
	private String listContainerClass;
	private String detailContainerClass;
	private boolean renderMaster;

	@Override
	public void doTag() throws JspException, IOException {
		Map<String, String> attributes = new HashMap<>();
		attributes.put("class", listContainerClass);
		startTag(getJspContext().getOut(), "div", attributes);
		doMaster();
		endTag(getJspContext().getOut(), "div");

		reset();
		attributes.clear();
		attributes.put("data-master-detail-id", jspId);
		attributes.put("data-master-detail", "detail");
		attributes.put("class", detailContainerClass);
		startTag(getJspContext().getOut(), "div", attributes);
		doDetail();
		endTag(getJspContext().getOut(), "div");
	}

	private void doMaster() throws IOException, JspException {
		renderMaster = true;
		Map<String, String> attributes = new HashMap<>();
		attributes.put("data-master-detail-id", jspId);
		attributes.put("data-master-detail", "master");
		attributes.put("class", listClass);
		startTag(getJspContext().getOut(), "ul", attributes);
		if (getJspBody() != null) {
			while (getIterator().hasNext()) {
				getIterator().next();
				getJspBody().invoke(null);
			}
		}
		endTag(getJspContext().getOut(), "ul");
	}

	private void doDetail() throws JspException, IOException {
		renderMaster = false;
		if (getJspBody() != null) {
			while (getIterator().hasNext()) {
				getIterator().next();
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

	@Override
	public void setJspId(String id) {
		jspId = id;
	}

	public void setListClass(String listClass) {
		this.listClass = listClass;
	}

	public void setListContainerClass(String listContainerClass) {
		this.listContainerClass = listContainerClass;
	}

	public void setDetailContainerClass(String detailContainerClass) {
		this.detailContainerClass = detailContainerClass;
	}
}
