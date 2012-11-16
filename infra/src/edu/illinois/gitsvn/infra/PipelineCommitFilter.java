package edu.illinois.gitsvn.infra;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;

public class PipelineCommitFilter extends CommitFilter {
	
	public List<CommitFilter> filters;
	public List<CommitFilter> collectors;
	public CommitFilter dataAgregator;

	@Override
	public boolean include(RevWalk walker, RevCommit cmit)
			throws StopWalkException, MissingObjectException,
			IncorrectObjectTypeException, IOException {
		
		for (CommitFilter filter : filters) {
			boolean result = filter.include(walker, cmit);
			if (result == false)
				return true;
		}
		
		for (CommitFilter collector : collectors) {
			collector.include(walker, cmit);
		}
		
		dataAgregator.include(walker, cmit);
		
		return true;
	}
}
