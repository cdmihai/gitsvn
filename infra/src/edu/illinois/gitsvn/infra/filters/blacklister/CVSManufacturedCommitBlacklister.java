package edu.illinois.gitsvn.infra.filters.blacklister;

import org.gitective.core.filter.commit.CommitMessageFindFilter;

public class CVSManufacturedCommitBlacklister extends CommitMessageFindFilter{

	private static final String CVS2SVN = "This commit was manufactured by cvs2svn";

	public CVSManufacturedCommitBlacklister() {
		super(CVS2SVN);
		
		System.out.println("CVS2SVN Manufactured commit filter active");
	}
}
