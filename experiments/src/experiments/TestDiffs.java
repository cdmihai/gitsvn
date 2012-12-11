package experiments;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.revwalk.RevCommit;
import org.gitective.core.CommitFinder;
import org.gitective.core.filter.commit.CommitDiffEditFilter;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

public class TestDiffs extends GitTestCase {

	private static final String CMIT = "cmit";
	private CommitFinder finder;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		finder = new CommitFinder(testRepo);
	}

	private void repo1() throws Exception {
		List<String> files = Arrays.asList(new String[]{"f1"});
		List<String> content = Arrays.asList(new String[]{
				"first line\n" 
				+ "second line\n" 
				+ "third line\n"
				+ "fourth line\n" 
				+ "fifth line\n"
				+ "sixth line\n"
				});

		add(files, content);
		
		files = Arrays.asList(new String[]{"f1", "f2"});
		content = Arrays.asList(new String[]{
				"second line\n" 
				+ "third changed line\n"
				+ "fourth line\n" 
				+ "replaced content\n"
				+ "sixth line\n"
				+ "seventh line",
				"content"});

		add(testRepo, files, content, CMIT);
	}

	@Test
	public void testDiffGranularity() throws Exception {
		repo1();
		
		finder.setFilter(new CommitDiffEditFilter(true) {
			@Override
			protected boolean include(RevCommit commit, DiffEntry diff, Collection<Edit> edits) {
				if(!commit.getFullMessage().equals(CMIT))
					return true;
				
				System.out.println(diff.getNewPath());
				
				for (Edit edit : edits) {
					System.out.println(edit.getType());
				}
				
				System.out.println();
				
				return true;
			}
		});
		
		finder.find();
	}

}
