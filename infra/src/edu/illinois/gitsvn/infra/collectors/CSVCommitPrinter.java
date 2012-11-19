package edu.illinois.gitsvn.infra.collectors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.infra.DataCollector;
import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.filters.AnalysisFilter;
import edu.illinois.gitsvn.infra.util.CSVWriter;

//TODO refactor this class to be a filter composite
public class CSVCommitPrinter extends AnalysisFilter {

	private CSVWriter csv;
	private List<DataCollector> allCollectors;
	
	public CSVCommitPrinter(PipelineCommitFilter filter) {
		super(filter);
	}
	
	@Override
	public void begin() {
		csv = new CSVWriter();
		List<String> headerData = new ArrayList<>();
		
		allCollectors = filter.getAllCollectors();
		for (DataCollector collector : allCollectors)
			headerData.add(collector.name());
		
		csv.addHeader(headerData);
	}

	@Override
	public void end() {
		try {
			csv.dumpToFile("mumu.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CSVWriter getCSVWriter() {
		return csv;
	}

	@Override
	public boolean include(RevWalk walker, RevCommit cmit) throws StopWalkException, MissingObjectException, IncorrectObjectTypeException, IOException {
		List<String> data = new ArrayList<String>();
		
		for (DataCollector collector : allCollectors)
			data.add(collector.getDataForCommit());
		
		csv.addRow(data);
		
		return true;
	}

	@Override
	public CommitFilter setRepository(Repository repository) {
		CommitFilter ret = super.setRepository(repository);

		return ret;
	}
}
