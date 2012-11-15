package edu.illinois.gitsvn.infra.filters;

import java.util.Collection;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.gitective.core.filter.commit.DiffCountFilter;

public class LineNumberFilter extends DiffCountFilter {

	private int count;

	public LineNumberFilter() {
		super(true);
	}

	@Override
	protected TreeWalk createTreeWalk(RevWalk walker, RevCommit commit) {
		TreeWalk walk = super.createTreeWalk(walker, commit);
		TreeFilter previousFilter = walk.getFilter();
		
		TreeFilter newFilter = AndTreeFilter.create(new FileExtensionBlacklister(), previousFilter);
		
		walk.setFilter(newFilter);
		
		return walk;
	}

	@Override
	protected boolean include(RevCommit commit, Collection<DiffEntry> diffs, int diffCount) {
		count = diffCount;

		return true;
	}

	public int getCount() {
		return count;
	}
}