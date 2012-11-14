package edu.illinois.gitsvn.infra.filters.blacklister;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.revwalk.RevCommit;
import org.gitective.core.filter.commit.CommitDiffFilter;

/**
 * Excludes a commit for which all Diffs are of change type DELETE.
 * 
 * @author mihai
 *
 */
public class AllDeletionDiffFilter extends CommitDiffFilter {
	
	private ChangeType changeType;
	
	private AllDeletionDiffFilter(ChangeType ct){
		this.changeType = ct;
	}
	
	private AllDeletionDiffFilter(){
	}
	
	public static AllDeletionDiffFilter getDeleteDiffFilter(){
		return new AllDeletionDiffFilter(ChangeType.DELETE);
	}
	
	public static AllDeletionDiffFilter getRenameDiffFilter(){
		return new AllDeletionDiffFilter(ChangeType.RENAME);
	}

	@Override
	public boolean include(RevCommit commit, Collection<DiffEntry> diffs) throws IOException {
		for (DiffEntry diffEntry : diffs) {
			if (!diffEntry.getChangeType().equals(changeType))
				return true;
		}

		return false;
	}
}
