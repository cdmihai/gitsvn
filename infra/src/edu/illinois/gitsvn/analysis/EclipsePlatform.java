package edu.illinois.gitsvn.analysis;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;

public class EclipsePlatform extends AnalysisConfiguration {

	@Override
	protected Git getGitRepo() {
		try {
			return Git.open(new File(
					"../../projects/eclipse.platform"));
		} catch (IOException e) {
		}

		return null;
	}

	@Override
	protected String getProjectName() {
		return "EclipsePlatform";
	}

	@Override
	protected PipelineCommitFilter configureAnalysis() {
		PipelineCommitFilter analysis = super.configureAnalysis();
		analysis.addDataCollector(new CutofDetectorFilter(1317272400));
		return analysis;
	}

}
