package edu.illinois.gitsvn.infra.filters;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;
import org.gitective.tests.GitTestCase;
import org.junit.Test;

import edu.illinois.gitsvn.infra.PipelineCommitFilter;

public class PipelineCommitFilterTest extends GitTestCase {

	@Test
	public void testFiltersExclusion() throws Exception {
		RevCommit cmit = add("test.txt","content","msg");
		PipelineCommitFilter filter = new PipelineCommitFilter();
		filter.addFilter(new CommitFilter() {
			
			@Override
			public boolean include(RevWalk walker, RevCommit cmit)
					throws StopWalkException, MissingObjectException,
					IncorrectObjectTypeException, IOException {
				if (cmit.getShortMessage().equals("msg"))
					return false;
				
				return true;
			}
		});
		
		assertFalse(filter.include(null, cmit));
	}
}
