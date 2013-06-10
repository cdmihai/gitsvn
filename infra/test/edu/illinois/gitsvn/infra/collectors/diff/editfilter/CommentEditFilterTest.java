package edu.illinois.gitsvn.infra.collectors.diff.editfilter;

import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class CommentEditFilterTest extends AbstractEditFilterTest {

	@Override
	@Before
	public void setFilter() throws IOException {
		filter = new CommentEditFilter();
		filter.setRepository(Git.open(testRepo).getRepository());
	}
	
	public void testHeader(String header) throws Exception {
		add("f1", 
				header + "line one\n" +
				header + "line two\n" +
				"line three\n" +
				"line four");
		
		RevCommit commit = add("f1", 
				header + "line changed one\n" +
				header + "line changed two\n" +
				"line three\n" +
				"line changed four");
		
		gatherEdits(commit);
		
		assertEquals(2, edits.size());
		assertEquals(1, filteredEdits.size());
		assertTrue(filteredEdits.contains(edits.get(1)));
	}
	
	@Test
	public void testSlashAsterixDiezHeader() throws Exception {
		testHeader("//");
		testHeader("*");
		testHeader("#");
	}
	
	@Test
	public void testJavadocStart() throws Exception {
		add("f1", 
				" /**line one\n" +
				"* line two\n" +
				"line three\n" +
				"line four");
		
		RevCommit commit = add("f1", 
				"/* line changed one\n" +
				" *line changed two\n" +
				"line three\n" +
				"line changed four");
		
		gatherEdits(commit);
		
		assertEquals(2, edits.size());
		assertEquals(1, filteredEdits.size());
		assertTrue(filteredEdits.contains(edits.get(1)));
	}
}
