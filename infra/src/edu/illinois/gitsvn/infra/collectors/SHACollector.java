package edu.illinois.gitsvn.infra.collectors;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.infra.DataCollector;

public class SHACollector extends CommitFilter implements DataCollector {
	
	private String sha1;

	@Override
	public String name() {
		return "SHA1";
	}

	@Override
	public String getDataForCommit() {
		return sha1;
	}

	@Override
	public boolean include(RevWalk walker, RevCommit cmit)
			throws StopWalkException, MissingObjectException,
			IncorrectObjectTypeException, IOException {
		
		sha1 = cmit.getName();
		
		return true;
	}

}
