package com.swiftelan.tag;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.jsp.jstl.core.LoopTagStatus;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class LoopTagSupportTest {
	private LoopTagSupport tag;
	private TestJspContext jspContext;

	@Before
	public void before() {
		tag = new LoopTagSupport();
		jspContext = new TestJspContext();
		tag.setJspContext(jspContext);
	}

	@Test
	public void nullItems() {
		Assert.assertFalse(tag.getIterator().hasNext());
	}

	@Test
	public void emptyItems() {
		tag.setItems(Collections.emptySet());
		Assert.assertFalse(tag.getIterator().hasNext());
	}

	@Test
	public void oneItem() {
		String one = "one";
		String var = "var";
		String varStatus = "varStatus";

		Collection<Object> items = new HashSet<>();
		items.add(one);
		tag.setItems(items);
		tag.setVar(var);
		tag.setVarStatus(varStatus);

		Assert.assertEquals(one, tag.getIterator().next());
		Assert.assertEquals(one, jspContext.getAttribute(var));
		LoopTagStatus status = (LoopTagStatus) jspContext.getAttribute(varStatus);
		Assert.assertEquals(0, status.getIndex());
		Assert.assertEquals(1, status.getCount());
		Assert.assertTrue(status.isFirst());
		Assert.assertTrue(status.isLast());
		Assert.assertFalse(tag.getIterator().hasNext());
	}

	@Test
	public void manyItems() {
		String var = "var";
		String varStatus = "varStatus";

		Collection<String> items = new HashSet<>();
		for (int i = 0; i < 10; i++) {
			items.add(Integer.toString(i));
		}
		tag.setItems(items);
		tag.setVar(var);
		tag.setVarStatus(varStatus);

		Iterator<String> iterator = items.iterator();
		int index = 0;
		while (iterator.hasNext()) {
			Assert.assertTrue(tag.getIterator().hasNext());
			String next = iterator.next();
			Assert.assertEquals(next, tag.getIterator().next());
			LoopTagStatus status = (LoopTagStatus) jspContext.getAttribute(varStatus);
			Assert.assertEquals(next, jspContext.getAttribute(var));
			Assert.assertEquals(index, status.getIndex());
			Assert.assertEquals(index + 1, status.getCount());
			index++;
		}
	}

	@Test
	public void unexposeVariables() {
		String one = "one";
		String var = "var";
		String varStatus = "varStatus";

		Collection<Object> items = new HashSet<>();
		items.add(one);
		tag.setItems(items);
		tag.setVar(var);
		tag.setVarStatus(varStatus);
		tag.getIterator().next();

		Assert.assertNotNull(jspContext.getAttribute(var));
		Assert.assertNotNull(jspContext.getAttribute(varStatus));
		tag.unexposeVariables();
		Assert.assertNull(jspContext.getAttribute(var));
		Assert.assertNull(jspContext.getAttribute(varStatus));
	}

	@Test
	public void reset() {
		oneItem();
		tag.reset();
		oneItem();
	}
}
