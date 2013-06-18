package edu.illinois.gitsvn.analysis;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.collectors.CppLineNumberCollector;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;

public class JUnitAnalysis extends AnalysisConfiguration {

	@Override
	protected Git getGitRepo() {
		try {
			return Git.open(new File("../../projects/junit"));
		} catch (IOException e) {
		}
		return null;
	}

	@Override
	protected String getProjectName() {
		return "JUnit";
	}
	
	@Override
	protected PipelineCommitFilter configurePipelineAnalysis() {
		PipelineCommitFilter configureAnalysis = super.configurePipelineAnalysis();
		configureAnalysis.addDataCollector(new CutofDetectorFilter(1231818542));
		return configureAnalysis;
	}

}
