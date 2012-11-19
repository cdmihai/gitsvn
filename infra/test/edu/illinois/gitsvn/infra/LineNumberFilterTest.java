package edu.illinois.gitsvn.infra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.CommitFinder;
import org.gitective.core.filter.commit.AllCommitFilter;
import org.gitective.core.filter.commit.CommitFilter;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

import edu.illinois.gitsvn.infra.collectors.CSVCommitPrinter;
import edu.illinois.gitsvn.infra.collectors.AllLineNumberFilter;
import edu.illinois.gitsvn.infra.filters.AnalysisFilter;

//TODO test more line diff cases. Just to be sure.
//TODO test how it handles renames

public class LineNumberFilterTest extends GitTestCase {

	private TestFilter countFilter;
	private CommitFinder finder;
	
	private class TestFilter extends AllLineNumberFilter {
		
		public String bla = "";
		
		@Override
		protected boolean include(RevCommit commit, Collection<DiffEntry> diffs, int diffCount) {
			super.include(commit,diffs,diffCount);
			
			bla += getDataForCommit();
			bla += " ";
			
			return true;
		}
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		countFilter = new TestFilter();
		finder = new CommitFinder(testRepo);
		finder.setFilter(countFilter);
	}

	@Test
	public void testAddition() throws Exception {
		add("test.java", "mumu\n", "first");
		finder.find();
		String actual = countFilter.bla;
		assertEquals("1 ", actual);
	}

	@Test
	public void testDeletion() throws Exception {
		add("test.java", "mumu\n", "first");
		add("test.java", "", "second");
		finder.find();
		String actual = countFilter.bla ;
		assertEquals("1 1 ", actual);
	}

	@Test
	public void testChange() throws Exception {
		add("test.java", "mumu\n", "first");
		add("test.java", "bubu\n", "second");
		finder.find();
		String actual = countFilter.bla;
		assertEquals("1 1 ", actual);
	}

	@Test
	public void testMultipleChange() throws Exception {
		add("test.java", "first line\nsecond line\nthird line\nfourth line", "c1");
		add("test.java", "first line\nsecond line2\nthird line\nfourth line2", "c2");
		finder.find();
		String actual = countFilter.bla;

		assertEquals("2 4 ", actual);

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
		String actual = countFilter.bla;

		assertEquals("6 12 ", actual);

	}
}
