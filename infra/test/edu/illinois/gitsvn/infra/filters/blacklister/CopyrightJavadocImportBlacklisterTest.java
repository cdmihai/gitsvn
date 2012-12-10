package edu.illinois.gitsvn.infra.filters.blacklister;

import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

public class CopyrightJavadocImportBlacklisterTest extends GitTestCase{

	private CopyrightJavadocImportBlacklister filter;
	private RevWalk revWalk;

	@Before
	public void before() throws IOException {
		filter = new CopyrightJavadocImportBlacklister();
		revWalk = new RevWalk(Git.open(testRepo).getRepository());
	}
	
	@Test
	public void testAccept() throws Exception {
		RevCommit cmit = add("f1", "", "Should pass");
		
		assertTrue(filter.include(revWalk, cmit));
	}
	
	@Test
	public void testAcceptLong() throws Exception {
		RevCommit cmit = add("f1", "", "Should pass\n\nJavadoc Copyright");
		
		assertTrue(filter.include(revWalk, cmit));
	}
	
	@Test
	public void testAcceptForSufficientWords() throws Exception {
		RevCommit cmit = add("f1", "", "New feature that changes the JavaDoc");
		
		assertTrue(filter.include(revWalk, cmit));
	}
	
	@Test
	public void testRejectJV() throws Exception {
		RevCommit cmit = add("f1", "", "cleanup JavaDoc");
		
		assertFalse(filter.include(revWalk, cmit));
	}
	
	@Test
	public void testRejectCP() throws Exception {
		RevCommit cmit = add("f1", "", "modify Copyright");
		
		assertFalse(filter.include(revWalk, cmit));
	}
	
	@Test
	public void testRejectIMP() throws Exception {
		RevCommit cmit = add("f1", "", "Fixed Unused Imports");
		
		assertFalse(filter.include(revWalk, cmit));
	}
	

}
