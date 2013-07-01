package edu.illinois.gitsvn.analysis;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;

public class EclipseJDTDebugAnalysis extends AnalysisConfiguration {

	@Override
	protected Git getGitRepo() {
		try {
			return Git.open(new File("../../svnToGitRepos/eclipse.jdt.debug"));
		} catch (IOException e) {
		}
		return null;
	}

	@Override
	protected String getProjectName() {
		return "JDTDebug";
	}
	
	@Override
	protected PipelineCommitFilter configurePipelineAnalysis() {
		PipelineCommitFilter analysis = super.configurePipelineAnalysis();
		analysis.addDataCollector(new CutofDetectorFilter(1316538989));
		return analysis;
	}

}
