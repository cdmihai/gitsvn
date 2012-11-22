package edu.illinois.gitsvn.infra.collectors;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;


public class CutofDetectorFilter extends CommitFilter implements AbstractSVNDetector {
	
	private String mode = "";
	private int cutofTime = 1295380000;
	
	public CutofDetectorFilter(int cutofTime) {
		this.cutofTime = cutofTime;
	}
	
	@Override
	public String name() {
		return "SCM";
	}
	
	@Override
	public String getDataForCommit() {
		return mode;
	}

	@Override
	public boolean include(RevWalk walker, RevCommit cmit)
			throws StopWalkException, MissingObjectException,
			IncorrectObjectTypeException, IOException {
		
		int commitTime = cmit.getCommitTime();
		if (commitTime < cutofTime)
			mode = SVN;
		else
			mode = GIT;
		
		return true;
	}

	@Override
	public String getMode() {
		return getDataForCommit();
	}

}
