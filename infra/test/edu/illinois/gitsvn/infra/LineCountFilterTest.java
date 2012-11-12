package edu.illinois.gitsvn.infra;

import org.eclipse.jgit.revwalk.RevCommit;
import org.gitective.core.CommitFinder;
import org.gitective.core.filter.commit.CommitCountFilter;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

import edu.illinois.gitsvn.infra.filters.LineNumberFilter;

public class LineCountFilterTest extends GitTestCase {
	
	private LineNumberFilter countFilter;
	private CommitFinder finder;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		countFilter = new LineNumberFilter();
		finder = new CommitFinder(testRepo);
		finder.setFilter(countFilter);
	}
	
	@Test
	public void testAddition() throws Exception {
		add("test.txt", "mumu\n", "first");
		finder.find();
		long actual = countFilter.getCount();
		assertEquals(1, actual);
	}
	
	@Test
	public void testDeletion() throws Exception {
		add("test.txt", "mumu\n", "first");
		add("test.txt","","second");
		finder.find();
		long actual = countFilter.getCount();
		assertEquals(1, actual);
	}
	
	@Test
	public void testChange() throws Exception {
		add("test.txt", "mumu\n", "first");
		add("test.txt","bubu\n","second");
		finder.find();
		long actual = countFilter.getCount();
		assertEquals(1, actual);
	}
}
