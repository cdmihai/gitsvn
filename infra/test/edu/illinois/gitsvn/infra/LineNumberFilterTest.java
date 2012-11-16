package edu.illinois.gitsvn.infra;

import java.util.Arrays;
import java.util.List;

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

public class LineNumberFilterTest extends GitTestCase {

	private LineNumberFilter countFilter;
	private CommitFinder finder;
	private CSVCommitPrinter csvCommitPrinter;
	private AllCommitFilter allCommitFilter;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		countFilter = new LineNumberFilter(true);
		csvCommitPrinter = new CSVCommitPrinter();

		csvCommitPrinter.setRepository(Git.open(testRepo).getRepository());
		csvCommitPrinter.begin();

		allCommitFilter = new AllCommitFilter(countFilter, csvCommitPrinter);

		finder = new CommitFinder(testRepo);
		finder.setFilter(allCommitFilter);
	}

	@Test
	public void testAddition() throws Exception {
		add("test.java", "mumu\n", "first");
		finder.find();
		long actual = countFilter.getCount();
		assertEquals(1, actual);
	}

	@Test
	public void testDeletion() throws Exception {
		add("test.java", "mumu\n", "first");
		add("test.java", "", "second");
		finder.find();
		long actual = countFilter.getCount();
		assertEquals(1, actual);
	}

	@Test
	public void testChange() throws Exception {
		add("test.java", "mumu\n", "first");
		add("test.java", "bubu\n", "second");
		finder.find();
		long actual = countFilter.getCount();
		assertEquals(1, actual);
	}

	@Test
	public void testMultipleChange() throws Exception {
		add("test.java", "first line\nsecond line\nthird line\nfourth line", "c1");
		add("test.java", "first line\nsecond line2\nthird line\nfourth line2", "c2");
		finder.find();

		String lines = csvCommitPrinter.getCSVWriter().getRows().get(0).get(5);

		assertEquals(2, Integer.parseInt(lines));

	}

	@Test
	public void testMultipleFilesChange() throws Exception {
		List<String> paths = Arrays.asList("t1.java", "t2.java", "t3.xml");
		List<String> content = Arrays.asList("first line\nsecond line\nthird line\nfourth line", "first line\nsecond line\nthird line\nfourth line", "first line\nsecond line\nthird line\nfourth line");
		add(testRepo, paths, content, "c1");

		paths = Arrays.asList("t1.java", "t2.java", "t3.xml");
		content = Arrays.asList("first line\nsecond2 line\nthird line\nfourth2 line", "first line\nsecond2 line\nthird line\nfourth2 line", "first line\nsecond2 line\nthird line\nfourth2 line");
		add(testRepo, paths, content, "c2");

		finder.find();

		List<List<String>> rows = csvCommitPrinter.getCSVWriter().getRows();
		String lines = rows.get(0).get(5);

		assertEquals(4, Integer.parseInt(lines));

	}
}
