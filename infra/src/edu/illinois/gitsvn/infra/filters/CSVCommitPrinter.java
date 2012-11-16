package edu.illinois.gitsvn.infra.filters;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.infra.util.CSVWriter;

public class CSVCommitPrinter extends AnalysisFilter {

	private CSVWriter csv;
	private LineNumberFilter lineFilter = new LineNumberFilter(true);
	private SVNCommitDetectorFilter svnDetector = new SVNCommitDetectorFilter();

	@Override
	public void begin() {
		csv = new CSVWriter();
		csv.addHeader(Arrays.asList(new String[] { "id", "msg", "SCM", "author", "time", "lines" }));
	}

	@Override
	public void end() {
	}

	public CSVWriter getCSVWriter() {
		return csv;
	}

	@Override
	public boolean include(RevWalk walker, RevCommit cmit) throws StopWalkException, MissingObjectException, IncorrectObjectTypeException, IOException {

		ObjectId id = cmit.getId();
		Integer commitTime = cmit.getCommitTime();
		PersonIdent author = cmit.getAuthorIdent();

		lineFilter.include(walker, cmit);
		svnDetector.include(walker, cmit);

		csv.addRow(Arrays.asList(new String[] { id.getName(), cmit.getShortMessage(), svnDetector.getMode(), author.getName(), commitTime.toString(), lineFilter.getCount() + "" }));

		return true;
	}

	@Override
	public CommitFilter setRepository(Repository repository) {
		CommitFilter ret = super.setRepository(repository);

		lineFilter.setRepository(repository);
		svnDetector.setRepository(repository);

		return ret;

	}
}
