package edu.illinois.gitsvn.analysis;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.collectors.CppLineNumberCollector;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;

public class LibreOfficeAnalysis extends AnalysisConfiguration {

	@Override
	protected Git getGitRepo() {
		try {
			return Git.open(new File("../../svnToGitRepos/libreoffice"));
		} catch (IOException e) {
		}

		return null;
	}

	@Override
	protected String getProjectName() {
		return "LibreOffice";
	}

	@Override
	protected PipelineCommitFilter configurePipelineAnalysis() {
		PipelineCommitFilter analysis = super.configurePipelineAnalysis();
		analysis.addDataCollector(new CutofDetectorFilter(1282256340));
		analysis.addDataCollector(new CppLineNumberCollector());
		return analysis;
	}
}
