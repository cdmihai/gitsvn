package edu.illinois.gitsvn.infra;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.gitsvn.analysis.CyclopsGroupAnalysis;
import edu.illinois.gitsvn.analysis.ThymeleafAnalysis;

public abstract class AnalysisLauncher {

	/**
	 * Starts the analysis.
	 * 
	 * @param args
	 *            a list of repositories to crawl.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		List<AnalysisConfiguration> configurations = new ArrayList<AnalysisConfiguration>();
		
		configurations.add(new CyclopsGroupAnalysis());
		configurations.add(new ThymeleafAnalysis());
		
		for (AnalysisConfiguration configuration : configurations) {
			configuration.run();
		}
	}
}
