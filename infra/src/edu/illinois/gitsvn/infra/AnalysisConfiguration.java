package edu.illinois.gitsvn.infra;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.collectors.AllLineNumberFilter;
import edu.illinois.gitsvn.infra.collectors.CSVCommitPrinter;
import edu.illinois.gitsvn.infra.collectors.JavaLineNumberFilter;
import edu.illinois.gitsvn.infra.filters.AnalysisFilter;
import edu.illinois.gitsvn.infra.filters.blacklister.CVSManufacturedCommitBlacklister;
import edu.illinois.gitsvn.infra.filters.blacklister.FileOperationBlacklister;

public abstract class AnalysisConfiguration {

	public void run() {
		RepositoryCrawler crawler = new RepositoryCrawler();
		PipelineCommitFilter analysisFilter = configureAnalysis();
		Git repo = getGitRepo();
		crawler.crawlRepo(repo, analysisFilter);
	}

	/**
	 * This should return the {@link Git} repo that should be
	 * analyzed. To further configure the analysis please see
	 * {@link #configureAnalysis()}.
	 * 
	 * @return the git repo to be analyzed.
	 */
	protected abstract Git getGitRepo();

	/**
	 * Method for configuring the analysis. The default implementation does it
	 * this way:
	 * <ul>
	 * <li>A rename ignore filter</li>
	 * <li>A delete ignore filter</li>
	 * <li>A CSV ignore filter</li>
	 * <li>A line number collector</li>
	 * <li>A java line number collector</li>
	 * <li>A CSV agregator</li>
	 * </ul>
	 * 
	 * Analyses that need some other configurations should specialize or
	 * override this method. Filters and collectors can be added to the returned
	 * filter. Also, the aggregator can be replaced, if so desired.
	 * 
	 * A dream is that one day this would be nicely configurable via an
	 * configuration file of some sort an then it would be really easy to run
	 * the analysis, without having to much about in the code. But this is
	 * easier, so it's going to have to do for now.
	 * 
	 * @return
	 */
	protected PipelineCommitFilter configureAnalysis() {
		PipelineCommitFilter analysisFilter = new PipelineCommitFilter();

		analysisFilter
				.addFilter(FileOperationBlacklister.getDeleteDiffFilter());
		analysisFilter
				.addFilter(FileOperationBlacklister.getRenameDiffFilter());
		analysisFilter.addFilter(new CVSManufacturedCommitBlacklister());

		analysisFilter.addDataCollector(new AllLineNumberFilter());
		analysisFilter.addDataCollector(new JavaLineNumberFilter());

		AnalysisFilter agregator = new CSVCommitPrinter(
				analysisFilter.getAllCollectors());
		analysisFilter.setDataAgregator(agregator);
		return analysisFilter;
	}
}
