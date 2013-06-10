package edu.illinois.gitsvn.analysis;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.collectors.CppLineNumberCollector;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;

public class PrototypeAnalysis extends AnalysisConfiguration {

	@Override
	protected Git getGitRepo() {
		try {
			return Git.open(new File("../../projects/prototype"));
		} catch (IOException e) {
		}
		return null;
	}

	@Override
	protected String getProjectName() {
		return "prototype";
	}
	
	@Override
	protected PipelineCommitFilter configureAnalysis() {
		PipelineCommitFilter configureAnalysis = super.configureAnalysis();
		configureAnalysis.addDataCollector(new CutofDetectorFilter(1205935492));
		return configureAnalysis;
	}

}