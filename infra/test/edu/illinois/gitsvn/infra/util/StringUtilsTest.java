package edu.illinois.gitsvn.infra.util;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

public class StringUtilsTest {

	String s = "line 1\n" +
			"line 2\n" +
			"line 3\n" +
			"line 4\n" +
			"line 5\n" +
			"line 6";

	@Test
	public void testNull() throws Exception {
		assertEquals(null, StringUtils.getLineDelimitedRegion(null, 1, 2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOutOfLeftBounds1() throws Exception {
		StringUtils.getLineDelimitedRegion(s, -1, 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOutOfLeftBounds2() throws Exception {
		StringUtils.getLineDelimitedRegion(s, 7, 6);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOutOfRightBounds1() throws Exception {
		StringUtils.getLineDelimitedRegion(s, 0, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOutOfRightBounds2() throws Exception {
		StringUtils.getLineDelimitedRegion(s, 0, 7);
	}

	@Test
	public void testFull() throws Exception {
		assertEquals(s, StringUtils.getLineDelimitedRegion(s, 0, 6));
	}

	@Test
	public void testEmpty() throws Exception {
		assertEquals("", StringUtils.getLineDelimitedRegion(s, 0, 0));
		assertEquals("", StringUtils.getLineDelimitedRegion(s, 3, 3));
		assertEquals("", StringUtils.getLineDelimitedRegion(s, 6, 6));
	}

	@Test
	public void testFirstLine() throws Exception {
		assertEquals("line 1\n", StringUtils.getLineDelimitedRegion(s, 0, 1));
	}

	@Test
	public void testLastLine() throws Exception {
		assertEquals("line 6", StringUtils.getLineDelimitedRegion(s, 5, 6));
	}

	@Test
	public void testMiddle2() throws Exception {
		assertEquals("line 3\nline 4\n", StringUtils.getLineDelimitedRegion(s, 2, 4));
	}

	@Test
	public void testDeleteLimits() throws Exception {
		assertEquals("line 2\n" +
				"line 3\n" +
				"line 4\n" +
				"line 5\n", StringUtils.getLineDelimitedRegion(s, 1, 5));
	}

	@Test
	public void testOneLine() throws Exception {
		String s = "line";
		assertEquals(s, StringUtils.getLineDelimitedRegion(s, 0, 1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOneLineFail() throws Exception {
		String s = "line";
		assertEquals(s, StringUtils.getLineDelimitedRegion(s, 1, 2));
	}

	@Test
	public void testTwoLine() throws Exception {
		String s = "line 1\nline 2";
		assertEquals(s, StringUtils.getLineDelimitedRegion(s, 0, 2));
	}

	@Test
	public void testTwoLineFirst() throws Exception {
		String s = "line 1\nline 2";
		assertEquals("line 1\n", StringUtils.getLineDelimitedRegion(s, 0, 1));
	}

	@Test
	public void testTwoLineSecond() throws Exception {
		String s = "line 1\nline 2";
		assertEquals("line 2", StringUtils.getLineDelimitedRegion(s, 1, 2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTwoLineFail() throws Exception {
		String s = "line 1\nline 2";
		assertEquals(s, StringUtils.getLineDelimitedRegion(s, 0, 3));
	}
}
