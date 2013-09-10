package edu.illinois.gitsvn.analysis.launchers;

import edu.illinois.gitsvn.infra.DirectoryAnalysisLauncher;

public class PureSvnLauncher {
	public static void main(String[] args) throws Exception {
		DirectoryAnalysisLauncher launcher = new DirectoryAnalysisLauncher("/home/codo/repos/svnRepos");
		launcher.setResultsPath("../../svnLoc");
		launcher.run();
	}
}
