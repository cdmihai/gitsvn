package edu.illinois.gitsvn.infra;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.infra.filters.AnalysisFilter;

final class AnalysisFilterAdapter extends AnalysisFilter {
	private final CommitFilter filterInstance;

	AnalysisFilterAdapter(CommitFilter filterInstance) {
		this.filterInstance = filterInstance;
	}

	@Override
	public void end() {

	}

	@Override
	public void begin() {

	}

	@Override
	public boolean include(RevWalk walker, RevCommit cmit) throws StopWalkException, MissingObjectException, IncorrectObjectTypeException, IOException {
		return filterInstance.include(walker, cmit);
	}
	
	public CommitFilter getAdaptee(){
		return filterInstance;
	}
}