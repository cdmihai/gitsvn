package edu.illinois.gitsvn.infra.filters;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.illinois.gitsvn.infra.collectors.DataCollectorTestCase;
import edu.illinois.gitsvn.infra.collectors.diff.JavaLineNumberFilter;

//TODO test more line diff cases. Just to be sure.
//TODO test how it handles renames

public class JavaLineNumberFilterTest extends DataCollectorTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		initTest(new JavaLineNumberFilter());
	}

	@Test
	public void testAddition() throws Exception {
		add("test.java", "mumu\n", "first");
		finder.find();
		String actual = collector.data;
		assertEquals("1; ", actual);
	}

	@Test
	public void testDeletion() throws Exception {
		add("test.java", "mumu\n", "first");
		add("test.java", "", "second");
		finder.find();
		String actual = collector.data ;
		assertEquals("1; 1; ", actual);
	}

	@Test
	public void testChange() throws Exception {
		add("test.java", "mumu\n", "first");
		add("test.java", "bubu\n", "second");
		finder.find();
		String actual = collector.data;
		assertEquals("1; 1; ", actual);
	}

	@Test
	public void testMultipleChange() throws Exception {
		add("test.java", "first line\nsecond line\nthird line\nfourth line", "c1");
		add("test.java", "first line\nsecond line2\nthird line\nfourth line2", "c2");
		finder.find();
		String actual = collector.data;

		assertEquals("2; 4; ", actual);

	}

	@Test
	public void testMultipleFilesChange() throws Exception {
		List<String> paths = Arrays.asList("a/b/c/t1.java", "t2.java", "t3.xml");
		List<String> content = Arrays.asList("first line\n" +
				"second line\n" +
				"third line\n" +
				"fourth line",
				
				"first line\n" +
				"second line\n" +
				"third line\n" +
				"fourth line",
				
				"first line\n" +
				"second line\n" +
				"third line\n" +
				"fourth line");
		add(testRepo, paths, content, "c1");

		paths = Arrays.asList("a/b/c/t1.java", "t2.java", "t3.xml");
		content = Arrays.asList("first line\n" +
				"second2 line\n" +
				"third line\n" +
				"fourth2 line", 
				
				"first line\n" +
				"second2 line\n" +
				"third line\n" +
				"fourth2 line",
				
				"first line\n" +
				"second2 line\n" +
				"third line\n" +
				"fourth2 line");
		add(testRepo, paths, content, "c2");

		finder.find();
		String actual = collector.data;

		assertEquals("4; 8; ", actual);

	}
}
