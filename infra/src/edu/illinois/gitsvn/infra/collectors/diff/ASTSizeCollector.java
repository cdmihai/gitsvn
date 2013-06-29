package edu.illinois.gitsvn.infra.collectors.diff;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.gitective.core.BlobUtils;
import org.gitective.core.filter.commit.CommitDiffFilter;

import edu.illinois.gitsvn.infra.DataCollector;
import edu.illinois.gitsvn.infra.filters.blacklister.NonSourceCodeFileExtensionBlacklister;

/**
 * Collector that retrieves the size of the commit as the number of AST operations
 * @author mihai
 *
 */
public class ASTSizeCollector extends CommitDiffFilter implements DataCollector{
	
	private int astChanges;

	@Override
	public boolean include(RevCommit commit, Collection<DiffEntry> diffs) throws IOException {
		return super.include(commit, diffs);
	}

	//TODO this is duplicated code with ModifyFileJavaLineNumberFilter
	@Override
	protected TreeWalk createTreeWalk(RevWalk walker, RevCommit commit) {
		TreeWalk walk = super.createTreeWalk(walker, commit);

		TreeFilter previousFilter = walk.getFilter();
		TreeFilter newFilter = AndTreeFilter.create(new NonSourceCodeFileExtensionBlacklister(), previousFilter);
		walk.setFilter(newFilter);

		return walk;
	}
	
	@Override
	public boolean include(RevWalk walker, RevCommit commit, Collection<DiffEntry> diffs) throws IOException {
		int astChanges = 0;
		
		for (DiffEntry diff : diffs) {
			String oldContent = BlobUtils.getContent(repository, diff.getOldId().toObjectId());
			String newContent = BlobUtils.getContent(repository, diff.getNewId().toObjectId());
		}
		
		this.astChanges = astChanges;

		return true;
	}

	@Override
	public String name() {
		return "ASTSize";
	}

	@Override
	public String getDataForCommit() {
		return astChanges + "";
	}
}
