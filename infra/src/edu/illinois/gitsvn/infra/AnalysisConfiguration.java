package edu.illinois.gitsvn.infra;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.collectors.CSVCommitPrinter;
import edu.illinois.gitsvn.infra.collectors.DateCollector;
import edu.illinois.gitsvn.infra.collectors.SHACollector;
import edu.illinois.gitsvn.infra.collectors.diff.ModifyFileAllLineNumberFilter;
import edu.illinois.gitsvn.infra.collectors.diff.ModifyFileJavaLineNumberFilter;
import edu.illinois.gitsvn.infra.filters.AnalysisFilter;
import edu.illinois.gitsvn.infra.filters.MetadataService;
import edu.illinois.gitsvn.infra.filters.blacklister.CVSManufacturedCommitBlacklister;
import edu.illinois.gitsvn.infra.filters.blacklister.FileOperationBlacklister;
import edu.illinois.gitsvn.infra.filters.blacklister.MergeMessageCommitBlackLister;
import edu.illinois.gitsvn.infra.filters.blacklister.MultipleParentCommitBlacklister;

/**
 * Runs a preconfigured analysis on a particular repo. Subclasses provide the repo location, project name and may further configure the analyses.
 * @author mihai
 *
 */
public abstract class AnalysisConfiguration {

	public void run() {
		RepositoryCrawler crawler = new RepositoryCrawler();
		PipelineCommitFilter analysisFilter = configureAnalysis();
		Git repo = getGitRepo();
		crawler.crawlRepo(repo, analysisFilter);
	}

	/**
	 * This should return the {@link Git} repo that should be analyzed. To
	 * further configure the analysis please see {@link #configureAnalysis()}.
	 * 
	 * @return the git repo to be analyzed.
	 */
	protected abstract Git getGitRepo();
	
	protected abstract String getProjectName();

	/**
	 * Method for configuring the analysis. The default implementation does it
	 * this way:
	 * <ul>
	 * <li>A rename ignore filter</li>
	 * <li>A delete ignore filter</li>
	 * <li>A SHA1 collector</li>
	 * <li>A Data collector</li>
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
		MetadataService.getService().pushInfo(CSVCommitPrinter.PROJ_NAME_PROP, getProjectName());
		PipelineCommitFilter analysisFilter = new PipelineCommitFilter();

		analysisFilter.addFilter(FileOperationBlacklister.getAddDiffFilter());
		analysisFilter.addFilter(FileOperationBlacklister.getDeleteDiffFilter());
		analysisFilter.addFilter(FileOperationBlacklister.getRenameDiffFilter());
		analysisFilter.addFilter(new CVSManufacturedCommitBlacklister());
		analysisFilter.addFilter(new MergeMessageCommitBlackLister());
		analysisFilter.addFilter(new MultipleParentCommitBlacklister());

		analysisFilter.addDataCollector(new SHACollector());
		analysisFilter.addDataCollector(new DateCollector());
		analysisFilter.addDataCollector(new ModifyFileAllLineNumberFilter());
		analysisFilter.addDataCollector(new ModifyFileJavaLineNumberFilter());

		AnalysisFilter agregator = new CSVCommitPrinter(analysisFilter);
		analysisFilter.setDataAgregator(agregator);
		return analysisFilter;
	}
}
