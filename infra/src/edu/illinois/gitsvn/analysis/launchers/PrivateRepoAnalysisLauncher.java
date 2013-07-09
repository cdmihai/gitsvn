package edu.illinois.gitsvn.analysis.launchers;

import java.util.List;

import edu.illinois.gitsvn.analysis.CutoffGenericAnalysis;
import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.AnalysisLauncher;

public class PrivateRepoAnalysisLauncher extends AnalysisLauncher {

	@Override
	protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
		configurations.add(new CutoffGenericAnalysis("../../personalSvnToGitRepos/AlexConverseFFmpeg", "AlexConverseFFmpeg", 1295258573));
		configurations.add(new CutoffGenericAnalysis("../../personalSvnToGitRepos/CarlEugenHoyosFFmpeg", "CarlEugenHoyosFFmpeg", 1295258573));
		configurations.add(new CutoffGenericAnalysis("../../personalSvnToGitRepos/DeepakAzadJDTUI", "DeepakAzadJDTUI", 1316608366));
		configurations.add(new CutoffGenericAnalysis("../../personalSvnToGitRepos/dsaffJUnit", "dsaffJUnit", 1231818542));
		configurations.add(new CutoffGenericAnalysis("../../personalSvnToGitRepos/JanneGrunauFFmpeg", "JanneGrunauFFmpeg", 1295258573));
		configurations.add(new CutoffGenericAnalysis("../../personalSvnToGitRepos/JoakimPlateFFmpeg", "JoakimPlateFFmpeg", 1295258573));
		configurations.add(new CutoffGenericAnalysis("../../personalSvnToGitRepos/MichaelNiedermayerFFmpeg", "MichaelNiedermayerFFmpeg", 1295258573));
		configurations.add(new CutoffGenericAnalysis("../../personalSvnToGitRepos/RamiroPollaFFmpeg", "RamiroPollaFFmpeg", 1295258573));
		configurations.add(new CutoffGenericAnalysis("../../personalSvnToGitRepos/TobieLangelPrototype", "TobieLangelPrototype", 1205935492));
	}

	public static void main(String[] args) throws Exception {
		new PrivateRepoAnalysisLauncher().run();
	}
}
