package edu.illinois.gitsvn.analysis;

import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.collectors.SVNCommitDetectorFilter;

/**
 * Generic analysis that builds the generic pipeline and adds the git-svn cutoff
 * detector, for projects that have string metada in their commit messages
 * 
 * @author mihai
 * 
 */
public class GitSvnCutoffAnalysis extends GenericAnalysis {

	public GitSvnCutoffAnalysis(String repoDir, String projectName) {
		super(repoDir, projectName);
	}

	@Override
	protected PipelineCommitFilter configurePipelineAnalysis() {

		PipelineCommitFilter pipeline = super.configurePipelineAnalysis();
		pipeline.addDataCollector(new SVNCommitDetectorFilter());

		return pipeline;
	}
}
