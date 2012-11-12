package edu.illinois.gitsvn.infra.filters;

import java.io.IOException;
import java.util.regex.Pattern;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.gitective.core.filter.commit.CommitMessageFindFilter;

/**
 * Detects whether a commit was imported from a previous SCM such as SVN
 * @author mihai
 *
 */

public class SVNCommitDetectorFilter extends CommitMessageFindFilter implements AbstractSVNDetector {

	private static String pattern = "git-svn-id";
	
	private String mode;
	
	public SVNCommitDetectorFilter() {
		super(pattern, Pattern.MULTILINE);
	}
	
	@Override
	public RevFilter clone() {
		return new SVNCommitDetectorFilter();
	}
	
	@Override
	public boolean include(RevWalk walker, RevCommit commit) throws IOException {
		boolean lastStatus = super.include(walker, commit);
		
		if(lastStatus)
			mode = SVN;
		else
			mode = GIT;
		
		return lastStatus;
	}
	
	/**
	 * Returns SVN if this commit was from SVN.
	 * Returns GIT otherwise. 
	 * @return
	 */
	@Override
	public String getMode(){
		return mode;
	}

}
