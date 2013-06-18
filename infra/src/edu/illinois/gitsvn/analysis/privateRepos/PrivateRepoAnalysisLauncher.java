package edu.illinois.gitsvn.analysis.privateRepos;

import java.util.List;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.AnalysisLauncher;

public class PrivateRepoAnalysisLauncher extends AnalysisLauncher {
	
	@Override
	protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
		configurations.add(new CutoffGenericAnalysis("/mnt/storage/privateRepos/AlexConverseFFmpeg", "AlexConverseFFmpeg", 1295258573));
		configurations.add(new CutoffGenericAnalysis("/mnt/storage/privateRepos/CarlEugenHoyosFFmpeg", "CarlEugenHoyosFFmpeg", 1295258573));
		configurations.add(new CutoffGenericAnalysis("/mnt/storage/privateRepos/DeepakAzadJDTUI", "DeepakAzadJDTUI", 1316608366));
		configurations.add(new CutoffGenericAnalysis("/mnt/storage/privateRepos/dsaffJUnit", "dsaffJUnit", 1231818542));
		configurations.add(new CutoffGenericAnalysis("/mnt/storage/privateRepos/JanneGrunauFFmpeg", "JanneGrunauFFmpeg", 1295258573));
		configurations.add(new CutoffGenericAnalysis("/mnt/storage/privateRepos/JoakimPlateFFmpeg", "JoakimPlateFFmpeg", 1295258573));
		configurations.add(new CutoffGenericAnalysis("/mnt/storage/privateRepos/MichaelNiedermayerFFmpeg", "MichaelNiedermayerFFmpeg", 1295258573));
		configurations.add(new CutoffGenericAnalysis("/mnt/storage/privateRepos/RamiroPollaFFmpeg", "RamiroPollaFFmpeg", 1295258573));
		configurations.add(new CutoffGenericAnalysis("/mnt/storage/privateRepos/TobieLangelPrototype", "TobieLangelPrototype", 1205935492));
	}
	
	public static void main(String[] args) throws Exception {
		new PrivateRepoAnalysisLauncher().run();
	}
}
