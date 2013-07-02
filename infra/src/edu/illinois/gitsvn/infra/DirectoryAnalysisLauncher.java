package edu.illinois.gitsvn.infra;

import java.io.File;
import java.util.List;

import edu.illinois.gitsvn.analysis.GenericAnalysis;


/**
 * Create and run analysis configurations for all repos inside a given parent directory
 * @author mihai
 *
 */

public class DirectoryAnalysisLauncher extends AnalysisLauncher {
	
	private String repositoryParentDir;

	public DirectoryAnalysisLauncher(String repositoryParentDir) {
		this.repositoryParentDir = repositoryParentDir;
	}
	
	@Override
	protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
		File parent = new File(repositoryParentDir);
		
		for (File f : parent.listFiles()) {
			configurations.add(new GenericAnalysis(f.getAbsolutePath(), f.getName()));
		}
	}
}
