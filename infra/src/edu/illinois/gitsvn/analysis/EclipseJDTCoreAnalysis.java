package edu.illinois.gitsvn.analysis;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;

public class EclipseJDTCoreAnalysis extends AnalysisConfiguration {

	@Override
	protected Git getGitRepo() {
		try {
			return Git.open(new File("../../projects/eclipse.jdt.core"));
		} catch (IOException e) {
		}
		return null;
	}

	@Override
	protected String getProjectName() {
		return "JDTCore";
	}
	
	@Override
	protected PipelineCommitFilter configureAnalysis() {
		PipelineCommitFilter analysis = super.configureAnalysis();
		analysis.addDataCollector(new CutofDetectorFilter(1316790694));
		return analysis;
	}

}
