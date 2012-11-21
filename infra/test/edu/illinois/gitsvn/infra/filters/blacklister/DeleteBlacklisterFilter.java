package edu.illinois.gitsvn.infra.filters.blacklister;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

public class DeleteBlacklisterFilter extends GitTestCase {

	private FileOperationBlacklister deleteDiffFilter;
	private RevWalk revWalk;

	@Before
	public void before() throws Exception {
		add("test.txt", "line 1\nline 2\nline 3\nline4\nline5\n", "");
		deleteDiffFilter = FileOperationBlacklister.getDeleteDiffFilter();
		revWalk = new RevWalk(Git.open(testRepo).getRepository());
	}

	@Test
	public void testDeleteContents() throws Exception {
		RevCommit commitToFilter = add("test.txt", "", "");

		boolean actual = deleteDiffFilter.include(revWalk, commitToFilter);
		assertTrue(actual);
	}

	@Test
	public void testDeleteSomeContents() throws Exception {
		RevCommit commitToFilter = add("test.txt", "line 1\nline 2\nline 3\n", "");

		boolean actual = deleteDiffFilter.include(revWalk, commitToFilter);
		assertTrue(actual);
	}

	@Test
	public void testDeleteFile() throws Exception {
		RevCommit commitToFilter = delete("test.txt");
		
		boolean actual = deleteDiffFilter.include(revWalk, commitToFilter);
		assertFalse(actual);
	}
}
