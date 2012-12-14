package edu.illinois.gitsvn.infra.collectors.diff.editfilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitDiffEditFilter;
import org.gitective.tests.GitTestCase;
import org.junit.Before;

public abstract class AbstractEditFilterTest extends GitTestCase{

	protected EditFilter filter;
	protected List<Edit> edits;
	protected Collection<Edit> filteredEdits;
	
	@Before
	public abstract void setFilter() throws IOException;
	
	protected void gatherEdits(RevCommit commit) throws Exception, IOException {
		filter.setRepository(Git.open(testRepo).getRepository());
		
		edits = new ArrayList<>();
		final DiffEntry[] diffs = new DiffEntry[1];
		
		CommitDiffEditFilter collectorFilter = new CommitDiffEditFilter(){
			@Override
			protected boolean include(RevCommit commit, DiffEntry diff, Collection<Edit> edit) {
				
				diffs[0] = diff;
				edits.addAll(edit);
				
				return true;
			}
		};
		
		Repository repository = Git.open(testRepo).getRepository();
		collectorFilter.include(new RevWalk(repository), commit);
		
		filteredEdits = filter.filterEdits(diffs[0], edits);
	}

}
