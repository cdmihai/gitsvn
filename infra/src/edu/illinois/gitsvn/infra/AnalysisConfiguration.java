package edu.illinois.gitsvn.infra;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.collectors.AuthorCollector;
import edu.illinois.gitsvn.infra.collectors.BranchCollector;
import edu.illinois.gitsvn.infra.collectors.CSVCommitPrinter;
import edu.illinois.gitsvn.infra.collectors.DateCollector;
import edu.illinois.gitsvn.infra.collectors.IssuesCollector;
import edu.illinois.gitsvn.infra.collectors.SHACollector;
import edu.illinois.gitsvn.infra.collectors.diff.ModifyDiffCountFilter;
import edu.illinois.gitsvn.infra.collectors.diff.ModifyFileAllLineNumberFilter;
import edu.illinois.gitsvn.infra.collectors.diff.ModifyFileJavaLineNumberFilter;
import edu.illinois.gitsvn.infra.filters.AnalysisFilter;
import edu.illinois.gitsvn.infra.filters.MetadataService;
import edu.illinois.gitsvn.infra.filters.blacklister.CVSManufacturedCommitBlacklister;
import edu.illinois.gitsvn.infra.filters.blacklister.CopyrightJavadocImportBlacklister;
import edu.illinois.gitsvn.infra.filters.blacklister.FileOperationBlacklister;
import edu.illinois.gitsvn.infra.filters.blacklister.MergeMessageCommitBlackLister;
import edu.illinois.gitsvn.infra.filters.blacklister.MultipleParentCommitBlacklister;

/**
 * Runs a preconfigured analysis on a particular repo. Subclasses provide the
 * repo location, project name and may further configure the analyses.
 * 
 * @author mihai
 * 
 */
public abstract class AnalysisConfiguration {

	public void run() {
		RepositoryCrawler crawler = new RepositoryCrawler();
		PipelineCommitFilter pipeline = configurePipelineAnalysis();
		Git repo = getGitRepo();

		System.out.println("Running for: " + getProjectName());
		crawler.crawlRepo(repo, pipeline);
		System.out.println("Finished for: " + getProjectName());
	}

	/**
	 * This should return the {@link Git} repo that should be analyzed. To
	 * further configure the analysis please see {@link #configurePipelineAnalysis()}.
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
	protected PipelineCommitFilter configurePipelineAnalysis() {
		MetadataService.getService().pushInfo(CSVCommitPrinter.PROJ_NAME_PROP, getProjectName());
		PipelineCommitFilter pipeLineFilter = new PipelineCommitFilter();

		pipeLineFilter.addFilter(FileOperationBlacklister.getAddDiffFilter());
		pipeLineFilter.addFilter(FileOperationBlacklister.getDeleteDiffFilter());
		pipeLineFilter.addFilter(FileOperationBlacklister.getRenameDiffFilter());
		pipeLineFilter.addFilter(new MergeMessageCommitBlackLister());
		pipeLineFilter.addFilter(new MultipleParentCommitBlacklister());
		pipeLineFilter.addFilter(new CopyrightJavadocImportBlacklister());
		pipeLineFilter.addFilter(new CVSManufacturedCommitBlacklister());

		pipeLineFilter.addDataCollector(new SHACollector());
		pipeLineFilter.addDataCollector(new DateCollector());
		pipeLineFilter.addDataCollector(new AuthorCollector());
		pipeLineFilter.addDataCollector(new ModifyFileAllLineNumberFilter(ModifyDiffCountFilter.getCommentEditFilter(), ModifyDiffCountFilter.getFormatEditFilter()));
		pipeLineFilter.addDataCollector(new ModifyFileJavaLineNumberFilter(ModifyDiffCountFilter.getCommentEditFilter(), ModifyDiffCountFilter.getFormatEditFilter()));
		pipeLineFilter.addDataCollector(new IssuesCollector());
		pipeLineFilter.addDataCollector(new BranchCollector(getGitRepo().getRepository()));

		AnalysisFilter agregator = new CSVCommitPrinter(pipeLineFilter);
		pipeLineFilter.setDataAgregator(agregator);
		return pipeLineFilter;
	}
}
