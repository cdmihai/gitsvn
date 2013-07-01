package edu.illinois.gitsvn.analysis.launchers;

import edu.illinois.gitsvn.infra.DirectoryAnalysisLauncher;

public class PureReposAnalysis {
	public static void main(String[] args) throws Exception {
		new DirectoryAnalysisLauncher("../../pureGitRepos").run();
		// new DirectoryAnalysisLauncher("../../pureSVNRepos");
	}
}
