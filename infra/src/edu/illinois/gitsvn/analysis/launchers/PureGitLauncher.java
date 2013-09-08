package edu.illinois.gitsvn.analysis.launchers;

import edu.illinois.gitsvn.infra.DirectoryAnalysisLauncher;

public class PureGitLauncher {
	public static void main(String[] args) throws Exception {
		DirectoryAnalysisLauncher launcher = new DirectoryAnalysisLauncher("../../extraPureGitRepos");
		launcher.setResultsPath("../../extraPureGitResults");
		launcher.run();
	}
}
