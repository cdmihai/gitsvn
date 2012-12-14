package edu.illinois.gitsvn.infra.collectors.diff.editfilter;

import java.util.Collection;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

import edu.illinois.gitsvn.infra.collectors.diff.ModifyDiffCountFilter;

public class ModifyDiffCountFilterIntegrationTest extends GitTestCase {


	private Filter filter;
	private RevWalk revWalk;
	
	private static class Filter extends ModifyDiffCountFilter{

		private int diffCount;

		public Filter() {
			super(ModifyDiffCountFilter.getCommentEditFilter(), ModifyDiffCountFilter.getFormatEditFilter());
		}
		
		@Override
		protected boolean include(RevCommit commit, Collection<DiffEntry> diffs, int diffCount) {
			this.diffCount = diffCount;
			return true;
		}
		
		public int getCount(){
			return diffCount;
		}
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();

		Repository repository = Git.open(testRepo).getRepository();
		filter = new Filter();
		filter.setRepository(repository);
		
		revWalk = new RevWalk(repository);
	}
	
	@Test
	public void test() throws Exception {
		add("f1", "//first line\n" 
				+ "*second line\n" 
				+ "third line\n"
				+ "				fourth \n \t\t\t \n\n\n\n\n\n\n line\n" 
				+ "fifth line\n"
				+ "sixth line\n" 
				+ "seventh line");
		
		RevCommit commit = add("f1", "//first changed line\n" 
				+ "*second changed line\n" 
				+ "third line\n"
				+ "fourth line\n" 
				+ "fifth line\n"
				+ "sixth changed line\n"
				+ "seventh changed line");
		
		filter.include(revWalk, commit);
		
		assertEquals(2, filter.getCount());
	}
}
