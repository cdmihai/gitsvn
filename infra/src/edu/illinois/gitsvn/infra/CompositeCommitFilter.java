package edu.illinois.gitsvn.infra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;

public class CompositeCommitFilter extends CommitFilter {

	private List<CommitFilter> filters = new ArrayList<CommitFilter>();

	public void addFilter(CommitFilter filter) {
		filters.add(filter);
	}

	public void removeFilter(CommitFilter filter) {
		filters.remove(filter);
	}

	@Override
	public boolean include(RevWalk walker, RevCommit cmit)
			throws StopWalkException, MissingObjectException,
			IncorrectObjectTypeException, IOException {
		// TODO Auto-generated method stub
		return false;
	}

}
