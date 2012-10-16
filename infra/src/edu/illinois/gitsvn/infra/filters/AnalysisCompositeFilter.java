package edu.illinois.gitsvn.infra.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.Repository;
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
public class AnalysisCompositeFilter extends AnalysisFilter implements
		AnalysisLifecycle {

	private List<AnalysisFilter> filters = new ArrayList<AnalysisFilter>();

	/**
	 * Adds a filter to the list.
	 * 
	 * @param filter
	 *            the filter to be added
	 */
	public void addFilter(AnalysisFilter filter) {
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

	public List<AnalysisFilter> getFilters() {
		return filters;
	}

	/**
	 * Applies this filter to the given commit. It always returns true, because
	 * we never want to stop the walk.
	 */
	@Override
	public boolean include(RevWalk walker, RevCommit cmit) throws StopWalkException, MissingObjectException, IncorrectObjectTypeException, IOException {

		for (CommitFilter filter : filters) {
			filter.include(walker, cmit);
		}

		return true;
	}

	@Override
	public CommitFilter setRepository(final Repository repository) {
		for (AnalysisFilter filter : filters)
			if (filter instanceof CommitFilter)
				filter.setRepository(repository);
		return super.setRepository(repository);
	}

	@Override
	public CommitFilter reset() {
		for (AnalysisFilter filter : filters)
			if (filter instanceof CommitFilter)
				filter.reset();
		return super.reset();
	}

	@Override
	public void begin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void end() {

	}
}
