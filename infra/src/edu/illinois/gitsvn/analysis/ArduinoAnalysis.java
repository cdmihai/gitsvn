package edu.illinois.gitsvn.analysis;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;

public class ArduinoAnalysis extends AnalysisConfiguration {

	@Override
	protected Git getGitRepo() {
		try {
			return Git.open(new File("../../projects/Arduino"));
		} catch (IOException e) {
		}
		return null;
	}

	@Override
	protected String getProjectName() {
		return "Arduino";
	}
	
	@Override
	protected PipelineCommitFilter configurePipelineAnalysis() {
		PipelineCommitFilter configureAnalysis = super.configurePipelineAnalysis();
		configureAnalysis.addDataCollector(new CutofDetectorFilter(1284741802));
		return configureAnalysis;
	}

}
