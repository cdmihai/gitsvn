package edu.illinois.gitsvn.infra;

import edu.illinois.gitsvn.infra.collectors.AllLineNumberFilter;
import edu.illinois.gitsvn.infra.collectors.CSVCommitPrinter;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;
import edu.illinois.gitsvn.infra.collectors.JavaLineNumberFilter;
import edu.illinois.gitsvn.infra.filters.AnalysisFilter;
import edu.illinois.gitsvn.infra.filters.blacklister.FileOperationBlacklister;

public class Main {

	/**
	 * Starts the analysis.
	 * 
	 * @param args
	 *            a list of repositories to crawl.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		RepositoryCrawler crawler = new RepositoryCrawler();
		
		addFilters(crawler);
		
		for (String remoteRepoLoc : args) {
			crawler.crawlRepo(remoteRepoLoc, configureAnalysis());
		}
	}

	private static void addFilters(RepositoryCrawler crawler) {
	}
	
	private static PipelineCommitFilter configureAnalysis() {
		PipelineCommitFilter analysisFilter = new PipelineCommitFilter();
		
		analysisFilter.addFilter(FileOperationBlacklister.getDeleteDiffFilter());
		analysisFilter.addFilter(FileOperationBlacklister.getRenameDiffFilter());
		
		analysisFilter.addDataCollector(new AllLineNumberFilter());
		analysisFilter.addDataCollector(new JavaLineNumberFilter());
		analysisFilter.addDataCollector(new CutofDetectorFilter());
		
		AnalysisFilter agregator = new CSVCommitPrinter(analysisFilter.getAllCollectors());
		analysisFilter.setDataAgregator(agregator);
		return analysisFilter;
	}

}
