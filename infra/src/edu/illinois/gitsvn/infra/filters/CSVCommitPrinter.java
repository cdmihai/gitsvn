package edu.illinois.gitsvn.infra.filters;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.infra.util.CSVWriter;

//TODO refactor this class to be a filter composite
public class CSVCommitPrinter extends AnalysisFilter {

	private CSVWriter csv;
	private CutofDetectorFilter cuttofSvnDetector = new CutofDetectorFilter();
	private LineNumberFilter allLineCounter = new LineNumberFilter(false);
	private LineNumberFilter sourceLineCounter = new LineNumberFilter(true);
	private SVNCommitDetectorFilter svnDetector = new SVNCommitDetectorFilter();
	
	@Override
	public void begin() {
		csv = new CSVWriter();
		csv.addHeader(Arrays.asList(new String[] { "id", "SCM", "author", "time", "lines", "sourceLines" }));
	}

	@Override
	public void end() {
	}

	public CSVWriter getCSVWriter() {
		return csv;
	}

	@Override
	public boolean include(RevWalk walker, RevCommit cmit) throws StopWalkException, MissingObjectException, IncorrectObjectTypeException, IOException {

		Integer commitTime = cmit.getCommitTime();
		PersonIdent author = cmit.getAuthorIdent();

		String id = cmit.getId().getName();
		String msg = cmit.getShortMessage();
		String authorName = author.getName();

		allLineCounter.include(walker, cmit);
		int allLineCount = allLineCounter.getCount();

		sourceLineCounter.include(walker, cmit);
		int sourceLineCount = sourceLineCounter.getCount();

		svnDetector.include(walker, cmit);
		String mode = svnDetector.getMode();

		csv.addRow(Arrays.asList(new String[] { id, mode, authorName, commitTime.toString(), allLineCount + "", sourceLineCount + "" }));

		return true;
	}

	@Override
	public CommitFilter setRepository(Repository repository) {
		CommitFilter ret = super.setRepository(repository);

		allLineCounter.setRepository(repository);
		sourceLineCounter.setRepository(repository);
		svnDetector.setRepository(repository);

		return ret;

	}
}
