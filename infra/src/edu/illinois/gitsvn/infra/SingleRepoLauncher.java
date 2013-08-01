package edu.illinois.gitsvn.infra;

import java.io.File;
import java.util.List;

import edu.illinois.gitsvn.analysis.GenericAnalysis;

public class SingleRepoLauncher extends AnalysisLauncher {
	
	private String repositoryDir;

	public SingleRepoLauncher(String repositoryDir) {
		this.repositoryDir = repositoryDir;
	}
	
	@Override
	protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
		
		File f = new File(this.repositoryDir);
		if(f.exists()) {
			configurations.add(new GenericAnalysis(f.getAbsolutePath(), f.getName()));
		} else {
			System.out.println("Repository " + this.repositoryDir + " does not exist");
		}
	}
}