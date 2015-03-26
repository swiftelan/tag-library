package com.swiftelan.tag.table;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;

import com.swiftelan.tag.ComponentTagSupport;
import com.swiftelan.tag.NullWriter;

/**
 * Tag handler for rendering table {@code <th>} and {@code <td>} elements
 */
public class ColumnTag extends ComponentTagSupport {

	private String header;
	private JspFragment headerBody;

	@Override
	public void doTag() throws JspException, IOException {
		TableTag table = findAncestorTag(this, TableTag.class);
		if (table.isRenderHeader()) {
			if (getJspBody() != null) {
				try (Writer writer = new NullWriter()) {
					getJspBody().invoke(writer);
				}
			}
			start("th", getAttributes());
			if (headerBody == null && header != null) {
				getJspContext().getOut().append(header);
			} else if (headerBody != null) {
				headerBody.invoke(null);
			}

			end("th");
		} else {
			start("td", getAttributes());
			getJspBody().invoke(null);
			end("td");
		}
	}

	/**
	 * Set the column header's content
	 * 
	 * @param header Value to use as the header for the column
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * Set the column header's content as a {@link JspFragment}
	 * 
	 * @param jspBody Fragment containing the header content
	 * @see ColumnHeaderTag
	 */
	void setHeaderBody(JspFragment jspBody) {
		headerBody = jspBody;
	}
}
