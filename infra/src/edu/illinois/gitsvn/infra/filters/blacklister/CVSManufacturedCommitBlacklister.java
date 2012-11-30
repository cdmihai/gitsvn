package edu.illinois.gitsvn.infra.filters.blacklister;

import java.io.IOException;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitMessageFindFilter;

public class CVSManufacturedCommitBlacklister extends CommitMessageFindFilter{

	private static final String CVS2SVN = "This commit was manufactured by cvs2svn";

	public CVSManufacturedCommitBlacklister() {
		super(CVS2SVN);
	}
	
	@Override
	public boolean include(RevWalk walker, RevCommit commit) throws IOException {
		return !super.include(walker, commit);
	}
}
