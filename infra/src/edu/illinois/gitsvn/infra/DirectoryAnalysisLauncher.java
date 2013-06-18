package edu.illinois.gitsvn.infra;

import java.io.File;
import java.util.List;

import edu.illinois.gitsvn.analysis.GenericAnalysis;


/**
 * Creates analysis configurations for all repos inside a given parent directory
 * @author mihai
 *
 */

public class DirectoryAnalysisLauncher extends AnalysisLauncher {
	
	public static void main(String[] args) throws Exception {
		new DirectoryAnalysisLauncher().run();
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
