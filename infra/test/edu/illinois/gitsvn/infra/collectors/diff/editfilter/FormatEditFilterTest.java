package edu.illinois.gitsvn.infra.collectors.diff.editfilter;

import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class FormatEditFilterTest extends AbstractEditFilterTest{
	
	@Override
	public void setFilter() throws IOException {
		filter = new FormatEditFilter();
		filter.setRepository(Git.open(testRepo).getRepository());
	}
	
	@Test
	public void testEdits() throws Exception {
		add("f1", "first line\n" 
				+ "second line\n" 
				+ "third line\n"
				+ "fourth line\n" 
				+ "fifth line\n"
				+ "sixth line\n");

		RevCommit commit = add("f1",
				"	\n	first line\n" 
				+ "second     \n \r \t \n           line\n\n\n" 
				+ "third line\n"
				+ "fourth line\n" 
				+ "fifth change line\n"
				+ "sixth line\n"
				);

		gatherEdits(commit);
		
		assertEquals(2, edits.size());
		
		assertEquals(1, filteredEdits.size());
		assertTrue(filteredEdits.contains(edits.get(1)));
	}
	
	@Test
	public void testWhiteSpaceInsert() throws Exception {
		add("f1", "first line\n" 
				+ "second line\n" 
				+ "third line\n"
				+ "fourth line\n" 
				+ "fifth line\n"
				+ "sixth line\n");

		RevCommit commit = add("f1",
				"first line\n" 
				+ "second line\n" 
				+ "third line\n" +
				"  \r\n\t\t\t  \n"
				+ "fourth line\n" 
				+ "fifth line\n"
				+ "sixth line\n"
				);

		gatherEdits(commit);
		
		assertEquals(1, edits.size());
		
		assertEquals(0, filteredEdits.size());
	}

	@Test
	public void testWhiteSpaceDelete() throws Exception {
		add("f1", "first line\n" 
				+ "second line\n" 
				+ "third line\n" 
				+ "  \r\n\t\t\t  \n"
				+ "fourth line\n" 
				+ "fifth line\n"
				+ "sixth line\n");

		RevCommit commit = add("f1",
				"first line\n" 
				+ "second line\n" 
				+ "third line\n" 
				+ "fourth line\n" 
				+ "fifth line\n"
				+ "sixth line\n"
				);

		gatherEdits(commit);
		
		assertEquals(1, edits.size());
		
		assertEquals(0, filteredEdits.size());
	}

}
