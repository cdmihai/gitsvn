package edu.illinois.gitsvn.infra.collectors;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.infra.DataCollector;

public class AuthorCollector  extends CommitFilter implements DataCollector{

	private String name;

	@Override
	public String name() {
		return "Author";
	}

	@Override
	public String getDataForCommit() {
		return name;
	}

	@Override
	public boolean include(RevWalk walker, RevCommit cmit) throws StopWalkException, MissingObjectException, IncorrectObjectTypeException, IOException {
		name = cmit.getAuthorIdent().getName();
		
		return true;
	}
}
