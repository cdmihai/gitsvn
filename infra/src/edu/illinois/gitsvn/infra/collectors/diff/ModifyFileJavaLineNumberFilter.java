package edu.illinois.gitsvn.infra.collectors.diff;

import java.util.Collection;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

import edu.illinois.gitsvn.infra.DataCollector;
import edu.illinois.gitsvn.infra.collectors.diff.editfilter.EditFilter;
import edu.illinois.gitsvn.infra.filters.blacklister.NonSourceCodeFileExtensionBlacklister;

/**
 * Code duplication of {@link JavaLineNumberFilter}
 * @author mihai
 *
 */
public class ModifyFileJavaLineNumberFilter extends ModifyDiffCountFilter implements
		DataCollector {

	private int count;

	public ModifyFileJavaLineNumberFilter(EditFilter ... filters) {
		super(filters);
	}

	@Override
	protected TreeWalk createTreeWalk(RevWalk walker, RevCommit commit) {
		TreeWalk walk = super.createTreeWalk(walker, commit);

		TreeFilter previousFilter = walk.getFilter();
		TreeFilter newFilter = AndTreeFilter.create(
				new NonSourceCodeFileExtensionBlacklister(), previousFilter);
		walk.setFilter(newFilter);

		return walk;
	}
	
	@Override
	protected boolean include(RevCommit commit, Collection<DiffEntry> diffs, int diffCount) {
		count = diffCount;

		return true;
	}

	@Override
	public String name() {
		return "SLOC";
	}

	@Override
	public String getDataForCommit() {
		return "" + count;
	}

}
