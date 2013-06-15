package edu.illinois.gitsvn.infra;

import java.io.File;
import java.util.List;

import edu.illinois.gitsvn.analysis.GenericAnalysis;

public class BatchAnalysisLauncher extends AnalysisLauncher {
	
	public static void main(String[] args) throws Exception {
		new BatchAnalysisLauncher().run();
	}
	
	@Override
	protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
		String reposPath = "../../svnProjects";
		
		File parent = new File(reposPath);
		
		for (File f : parent.listFiles()) {
			configurations.add(new GenericAnalysis(f.getAbsolutePath(), f.getName()));
		}
	}
}
