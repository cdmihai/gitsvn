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

/**
 * Composite Filter that allows us to call multiple filters in one walk through
 * the repository. This should make the whole thing more scalable and faster.
 * 
 * @author caius
 * 
 */
public class CompositeCommitFilter extends CommitFilter {

	private List<CommitFilter> filters = new ArrayList<CommitFilter>();

	/**
	 * Adds a filter to the list.
	 * 
	 * @param filter
	 *            the filter to be added
	 */
	public void addFilter(CommitFilter filter) {
		filters.add(filter);
	}

	/**
	 * Removes a filter from the list.
	 * 
	 * @param filter
	 *            the filter to be removed
	 */
	public void removeFilter(CommitFilter filter) {
		filters.remove(filter);
	}

	@Override
	public boolean include(RevWalk walker, RevCommit cmit)
			throws StopWalkException, MissingObjectException,
			IncorrectObjectTypeException, IOException {
		return false;
	}

}
