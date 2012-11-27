package edu.illinois.gitsvn.infra.filters.blacklister;

import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

public class MergeMessageCommitBlackListerTest extends GitTestCase {

	private MergeMessageCommitBlackLister filter;
	private RevWalk revWalk;

	@Before
	public void before() throws IOException {
		filter = new MergeMessageCommitBlackLister();
		revWalk = new RevWalk(Git.open(testRepo).getRepository());
	}

	@Test
	public void testRejectOneLine() throws Exception {
		RevCommit cmit = add("file", "content", "this is a merge commit");

		assertFalse(filter.include(revWalk, cmit));
	}
	
	@Test
	public void testRejectMultipleLine() throws Exception {
		RevCommit cmit = add("file", "content", "this is a merge commit\nthis is new line");

		assertFalse(filter.include(revWalk, cmit));
	}
	
	@Test
	public void testRejectMixedCase() throws Exception {
		RevCommit cmit = add("file", "content", "this is a mErGe commit");

		assertFalse(filter.include(revWalk, cmit));
	}
	
	@Test
	public void testAccept() throws Exception {
		RevCommit cmit = add("file", "content", "this is a normal commit");

		assertTrue(filter.include(revWalk, cmit));
	}
	
	@Test
	public void testAcceptMultipleLines() throws Exception {
		RevCommit cmit = add("file", "content", "this is a normal commit\n\ntNew line with merge keyword");

		assertTrue(filter.include(revWalk, cmit));
	}
}
