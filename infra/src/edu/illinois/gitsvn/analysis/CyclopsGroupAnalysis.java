package edu.illinois.gitsvn.analysis;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;

public class CyclopsGroupAnalysis extends AnalysisConfiguration {

	@Override
	protected Git getGitRepo() {
		try {
			return Git.open(new File("../../svnToGitRepos/cyclopsgroup"));
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	protected PipelineCommitFilter configurePipelineAnalysis() {
		PipelineCommitFilter configuredAnalysis = super.configurePipelineAnalysis();
		configuredAnalysis.addDataCollector(new CutofDetectorFilter(1295380000));
		return configuredAnalysis;
	}

	@Override
	protected String getProjectName() {
		return "CyclopsGroup";
	}
}
