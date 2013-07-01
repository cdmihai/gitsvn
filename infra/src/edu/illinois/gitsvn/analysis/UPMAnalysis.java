package edu.illinois.gitsvn.analysis;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;
import edu.illinois.gitsvn.infra.collectors.SVNCommitDetectorFilter;

public class UPMAnalysis extends AnalysisConfiguration {

	@Override
	protected Git getGitRepo() {
		try {
			return Git.open(new File("../../svnToGitRepos/upm-swing/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected String getProjectName() {
		return "UPM";
	}

	@Override
	protected PipelineCommitFilter configurePipelineAnalysis() {
		PipelineCommitFilter analysis = super.configurePipelineAnalysis();
		analysis.addDataCollector(new CutofDetectorFilter(1287344636));

		return analysis;
	}
	
	public static void main(String[] args) {
		new UPMAnalysis().run();
	}
}
