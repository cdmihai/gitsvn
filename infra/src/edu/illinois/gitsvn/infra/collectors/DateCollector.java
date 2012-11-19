package edu.illinois.gitsvn.infra.collectors;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.infra.DataCollector;

public class DateCollector extends CommitFilter implements DataCollector {

	private String date;

	@Override
	public String name() {
		return "Commit date";
	}

	@Override
	public String getDataForCommit() {
		return date;
	}

	@Override
	public boolean include(RevWalk walker, RevCommit cmit)
			throws StopWalkException, MissingObjectException,
			IncorrectObjectTypeException, IOException {
		date = cmit.getCommitTime() + "";
		
		return true;
	}

}
