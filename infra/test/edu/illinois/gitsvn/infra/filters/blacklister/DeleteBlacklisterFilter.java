package edu.illinois.gitsvn.infra.filters.blacklister;

import java.io.File;
import java.io.PrintWriter;

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
		RevCommit commitToFilter = add("test.txt", "line 1\nline 2\nline 3\n",
				"");

		boolean actual = deleteDiffFilter.include(revWalk, commitToFilter);
		assertTrue(actual);
	}

	@Test
	public void testDeleteFile() throws Exception {
		RevCommit commitToFilter = delete("test.txt");

		boolean actual = deleteDiffFilter.include(revWalk, commitToFilter);
		assertFalse(actual);
	}

	@Test
	public void testDeleteOneFileChangeAnother() throws Exception {
		String deletedFile = "test.txt";
		String modifiedFile = "modified.txt";
		
		add(deletedFile, "some content", "first");
		add(modifiedFile, "some content", "second");
		
		Git git = Git.open(testRepo);
		git.rm().addFilepattern(deletedFile).call();
		File file = new File(testRepo.getParent(), modifiedFile);
		PrintWriter writer = new PrintWriter(file);
		writer.write("Some other content");
		writer.close();
		git.add().addFilepattern(modifiedFile).call();
		RevCommit commit = git.commit().setOnly(deletedFile)
				.setOnly(modifiedFile).setMessage("third").setAuthor(author)
				.setCommitter(committer).call();
				
		boolean actual = deleteDiffFilter.include(revWalk, commit);
		assertTrue(actual);

	}
}
