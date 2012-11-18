package edu.illinois.gitsvn.analysis;

import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.RepositoryCrawler;
import edu.illinois.gitsvn.infra.collectors.AllLineNumberFilter;
import edu.illinois.gitsvn.infra.collectors.CSVCommitPrinter;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;
import edu.illinois.gitsvn.infra.collectors.JavaLineNumberFilter;
import edu.illinois.gitsvn.infra.filters.AnalysisFilter;
import edu.illinois.gitsvn.infra.filters.blacklister.FileOperationBlacklister;

public class CyclopsGroupAnalysis {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String repoLoc = "git://github.com/jiaqi/cyclopsgroup.git";

		RepositoryCrawler crawler = new RepositoryCrawler();

		crawler.crawlRepo(repoLoc, configureAnalysis());
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
