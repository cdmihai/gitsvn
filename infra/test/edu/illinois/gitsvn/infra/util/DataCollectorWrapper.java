package edu.illinois.gitsvn.infra.util;

import java.io.IOException;
import java.security.InvalidParameterException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.infra.DataCollector;


public class DataCollectorWrapper extends CommitFilter {

	public String data = "";
	private DataCollector collector;

	public DataCollectorWrapper(DataCollector collector) {
		if (!(collector instanceof CommitFilter))
			throw new InvalidParameterException(
					"The collector must be a CommitFilter");

		this.collector = collector;
	}

	@Override
	public boolean include(RevWalk walker, RevCommit cmit)
			throws StopWalkException, MissingObjectException,
			IncorrectObjectTypeException, IOException {
		((CommitFilter) collector).include(walker, cmit);

		data += collector.getDataForCommit();
		data += "; ";

		return true;
	}
}