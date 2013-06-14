package edu.illinois.gitsvn.analysis;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;

public class GitAnalysis extends AnalysisConfiguration {

	@Override
	protected Git getGitRepo() {
		Git repo = null;
		try {
			repo = Git.open(new File("../../projects/git"));
		} catch (IOException e) {
		}
		return repo;
	}

	@Override
	protected String getProjectName() {
		return "Git";
	}

}
