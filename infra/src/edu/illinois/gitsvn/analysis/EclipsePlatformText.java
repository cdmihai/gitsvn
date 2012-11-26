package edu.illinois.gitsvn.analysis;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;

public class EclipsePlatformText extends AnalysisConfiguration {

	@Override
	protected Git getGitRepo() {
		try {
			return Git.open(new File(
					"../../projects/eclipse_core/eclipse.platform.text"));
		} catch (IOException e) {
		}

		return null;
	}

	@Override
	protected String getProjectName() {
		return "EclipsePlatformText";
	}

	@Override
	protected PipelineCommitFilter configureAnalysis() {
		PipelineCommitFilter analysis = super.configureAnalysis();
		analysis.addDataCollector(new CutofDetectorFilter(1316149200));
		return analysis;
	}
}
