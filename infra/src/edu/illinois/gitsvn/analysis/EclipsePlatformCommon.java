package edu.illinois.gitsvn.analysis;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;

public class EclipsePlatformCommon extends AnalysisConfiguration {

	@Override
	protected Git getGitRepo() {
		try {
			return Git.open(new File(
					"../../svnToGitRepos/eclipse.platform.common"));
		} catch (IOException e) {
		}

		return null;
	}

	@Override
	protected String getProjectName() {
		return "EclipsePlatformCommon";
	}

	@Override
	protected PipelineCommitFilter configurePipelineAnalysis() {
		PipelineCommitFilter analysis = super.configurePipelineAnalysis();
		analysis.addDataCollector(new CutofDetectorFilter(1318741200));
		return analysis;
	}

}
