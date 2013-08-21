package edu.illinois.gitsvn.analysis.launchers;

import edu.illinois.gitsvn.infra.DirectoryAnalysisLauncher;
import edu.illinois.gitsvn.infra.SingleRepoLauncher;

public class PureReposAnalysis {
	public static void main(String[] args) throws Exception {
		if(args.length > 0) {
			for(String arg : args) {
				new SingleRepoLauncher(arg).run();
			}
		} else {
			new DirectoryAnalysisLauncher("../../pureGitRepos").run();
			// new DirectoryAnalysisLauncher("../../pureSVNRepos");
		}
	}
}
