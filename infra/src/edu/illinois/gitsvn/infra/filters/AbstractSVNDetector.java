package edu.illinois.gitsvn.infra.filters;

import org.gitective.core.filter.commit.CommitMessageFindFilter;

public abstract class AbstractSVNDetector extends CommitMessageFindFilter {

	public static String SVN = "SVN";
	public static String GIT = "GIT";

	public AbstractSVNDetector(String pattern) {
		super(pattern);
	}

	public AbstractSVNDetector(String pattern, int flags) {
		super(pattern, flags);
	}
	
	public abstract String getMode();
}