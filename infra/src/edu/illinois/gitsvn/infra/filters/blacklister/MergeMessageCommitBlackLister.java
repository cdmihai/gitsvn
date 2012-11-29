package edu.illinois.gitsvn.infra.filters.blacklister;

import java.io.IOException;
import java.util.regex.Pattern;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import edu.illinois.gitsvn.infra.filters.ShortCommitMessageFindFilter;

/**
 * Filter that rejects commits with the text "merge" in the first line
 * 
 * @author mihai
 */
public class MergeMessageCommitBlackLister extends ShortCommitMessageFindFilter {

	private static final String mergeString = "merge";

	public MergeMessageCommitBlackLister() {
		super(mergeString, Pattern.CASE_INSENSITIVE);
	}

	@Override
	public boolean include(RevWalk walker, RevCommit commit) throws IOException {
		return !super.include(walker, commit);
	}

}
