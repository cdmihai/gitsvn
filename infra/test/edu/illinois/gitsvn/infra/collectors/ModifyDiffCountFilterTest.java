package edu.illinois.gitsvn.infra.collectors;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

public class ModifyDiffCountFilterTest extends GitTestCase {

	public class ModifyDiffTesterFilter extends ModifyDiffCountFilter {
		private int count;

		public int getCount() {
			return count;
		}

		@Override
		protected boolean include(RevCommit commit, DiffEntry diff, Collection<Edit> edits) {
			count++;

			return true;
		}
	}

	private ModifyDiffTesterFilter filter;
	private RevWalk revWalk;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		filter = new ModifyDiffTesterFilter();
		revWalk = new RevWalk(Git.open(testRepo).getRepository());
	}

	@Test
	public void testSimpleRejectAdd() throws Exception {
		RevCommit cmit = add("f", "content");

		filter.include(revWalk, cmit);
		assertEquals(0, filter.getCount());
	}

	@Test
	public void testSimpleRejectRemove() throws Exception {
		add("f", "content");

		RevCommit cmit = delete("f");

		filter.include(revWalk, cmit);
		assertEquals(0, filter.getCount());
	}
	
	@Test
	public void testSimpleRejectRename() throws Exception {
		add("f", "content");

		RevCommit cmit = mv("f", "f0");

		filter.include(revWalk, cmit);
		assertEquals(0, filter.getCount());
	}
	
	@Test
	public void testSimpleAccept() throws Exception {
		add("f", "content");

		RevCommit cmit = add("f", "new content");

		filter.include(revWalk, cmit);
		assertEquals(1, filter.getCount());
	}
	
	@Test
	public void testNoisyAccept() throws Exception {
		List<String> paths = Arrays.asList(new String[]{"f1", "f2"});
		List<String> contents = Arrays.asList(new String[]{"contents", "contents"});
		add(paths, contents);
		
		paths = Arrays.asList(new String[]{"f1", "f3", "f2", "f4"});
		contents = Arrays.asList(new String[]{"cont", "cont", "cont", "cont"});
		RevCommit cmit = add(paths, contents);
		
		filter.include(revWalk, cmit);
		assertEquals(2, filter.getCount());
	}
}
