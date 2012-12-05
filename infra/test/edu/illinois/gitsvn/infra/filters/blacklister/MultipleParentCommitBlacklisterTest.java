package edu.illinois.gitsvn.infra.filters.blacklister;

import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.CommitUtils;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

public class MultipleParentCommitBlacklisterTest extends GitTestCase{

	private static final String MASTER = "master";
	private static final String BR1 = "br1";
	
	private final MultipleParentCommitBlacklister filter = new MultipleParentCommitBlacklister();
	private RevWalk revWalk;
	
	@Before
	public void before() throws IOException{
		revWalk = new RevWalk(Git.open(testRepo).getRepository());
	}
	
	@Test
	public void testReject() throws Exception {
		add("f0", "contents");
		
		branch(BR1);
		add("f1", "contents");
		
		checkout(MASTER);
		add("f0", "contents\nnew line");
		
		checkout(BR1);
		MergeResult mergeResult = merge(MASTER);
		
		RevCommit cmit = CommitUtils.getHead(Git.open(testRepo).getRepository());
		
		assertEquals(2,cmit.getParentCount());
		assertFalse(filter.include(revWalk, cmit));
	}
	
	@Test
	public void testZeroParents() throws Exception {
		RevCommit cmit = add("a", "a");

		assertEquals(0, cmit.getParentCount());
		assertTrue(filter.include(revWalk, cmit));
	}
	
	@Test
	public void testOneParent() throws Exception {
		add("a", "a");
		RevCommit cmit = add("a", "aa");

		assertEquals(1, cmit.getParentCount());
		assertTrue(filter.include(revWalk, cmit));
		
	}
}
