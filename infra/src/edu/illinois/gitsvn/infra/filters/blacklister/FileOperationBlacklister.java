package edu.illinois.gitsvn.infra.filters.blacklister;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.revwalk.RevCommit;
import org.gitective.core.filter.commit.CommitDiffFilter;

/**
 * Excludes a commit for which all Diffs are of change type DELETE or RENAME.
 * 
 * @author mihai
 * 
 */
public class FileOperationBlacklister extends CommitDiffFilter {

	private ChangeType changeType;

	private FileOperationBlacklister(ChangeType ct, boolean detectRenamings) {
		super(detectRenamings);
		this.changeType = ct;
	}

	private FileOperationBlacklister(ChangeType ct) {
		this(ct, false);
	}

	private FileOperationBlacklister() {
		super(false);
	}

	public static FileOperationBlacklister getDeleteDiffFilter() {
		return new FileOperationBlacklister(ChangeType.DELETE, true);
	}

	//TODO write tests
	public static FileOperationBlacklister getRenameDiffFilter() {
		return new FileOperationBlacklister(ChangeType.RENAME, true);
	}
	
	//TODO write tests
	public static FileOperationBlacklister getAddDiffFilter(){
		return new FileOperationBlacklister(ChangeType.ADD, true);
	}

	@Override
	public boolean include(RevCommit commit, Collection<DiffEntry> diffs)
			throws IOException {
		for (DiffEntry diffEntry : diffs) {
			if (!diffEntry.getChangeType().equals(changeType))
				return true;
		}

		return false;
	}
}
