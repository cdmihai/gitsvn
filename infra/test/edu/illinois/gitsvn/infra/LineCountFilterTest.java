package edu.illinois.gitsvn.infra;

import org.eclipse.jgit.api.Git;
import org.gitective.core.CommitFinder;
import org.gitective.core.filter.commit.AllCommitFilter;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

import edu.illinois.gitsvn.infra.filters.CSVCommitPrinter;
import edu.illinois.gitsvn.infra.filters.LineNumberFilter;

//TODO test more line diff cases. Just to be sure.
//TODO test how it handles renames

public class LineCountFilterTest extends GitTestCase {
	
	private LineNumberFilter countFilter;
	private CommitFinder finder;
	private CSVCommitPrinter csvCommitPrinter;
	private AllCommitFilter allCommitFilter;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		countFilter = new LineNumberFilter();
		csvCommitPrinter = new CSVCommitPrinter();
		
		csvCommitPrinter.setRepository(Git.open(testRepo).getRepository());
		csvCommitPrinter.begin();
		
		allCommitFilter = new AllCommitFilter(countFilter, csvCommitPrinter);
		
		finder = new CommitFinder(testRepo);
		finder.setFilter(allCommitFilter);
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
	
	@Test
	public void testMultipleChange() throws Exception {
		add("test.txt", "first line\nsecond line\nthird line\nfourth line", "first");
		add("test.txt", "first line\nsecond line\nthird line\nfourth line2", "second");
		finder.find();
		
		String lines = csvCommitPrinter.getCSVWriter().getRows().get(1).get(4);
		
		assertEquals(4, Integer.parseInt(lines));
		
	}
}
