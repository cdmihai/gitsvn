package edu.illinois.gitsvn.infra.filters;

import org.eclipse.jgit.revwalk.RevCommit;
import org.gitective.core.filter.commit.PatternFindCommitFilter;

/**
 * Base filter that includes commits where a pattern can be found in a commit's
 * short message.
 * @author mihai
 *
 */
public class ShortCommitMessageFindFilter extends PatternFindCommitFilter {

	public ShortCommitMessageFindFilter(String pattern, int flags) {
		super(pattern, flags);
	}

	public ShortCommitMessageFindFilter(String pattern) {
		super(pattern);
	}

	@Override
	protected CharSequence getText(RevCommit commit) {
		return commit.getShortMessage();
	}
}
