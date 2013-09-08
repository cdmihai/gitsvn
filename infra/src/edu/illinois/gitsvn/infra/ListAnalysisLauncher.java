package edu.illinois.gitsvn.infra;

import java.io.File;
import java.util.List;

import edu.illinois.gitsvn.analysis.GenericAnalysis;

/**
 * Analysis Launcher that takes a list of repository paths
 * @author mihai
 *
 */
public class ListAnalysisLauncher extends AnalysisLauncher{
	
	private List<String> repoPaths;
	
	public ListAnalysisLauncher(List<String> repoPaths) {
		this.repoPaths = repoPaths;
	}
	
	@Override
	protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
		for (String repo : repoPaths) {
			File f = new File(repo);
			
			configurations.add(new GenericAnalysis(f.getAbsolutePath(), f.getName()));
		}
	}
}
