package edu.illinois.gitsvn.infra.filters;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import edu.illinois.gitsvn.infra.util.CSVWriter;

public class CSVCommitPrinter extends AnalysisFilter {
	
	private CSVWriter csv;

	@Override
	public void begin() {
		csv = new CSVWriter();
		csv.addHeader(Arrays.asList(new String[]{"id", "time"}));
	}

	@Override
	public void end() {
	}
	
	public CSVWriter getCSVWriter(){
		return csv;
	}

	@Override
	public boolean include(RevWalk walker, RevCommit cmit) throws StopWalkException, MissingObjectException, IncorrectObjectTypeException, IOException {
		
		ObjectId id = cmit.getId();
		Integer commitTime = cmit.getCommitTime();
		
		csv.addRow(Arrays.asList(new String[]{id.getName(), commitTime.toString()}));
		
		return true;
	}
}
