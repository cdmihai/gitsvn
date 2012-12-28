package edu.illinois.gitsvn.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;

import edu.illinois.gitsvn.analysis.CyclopsGroupAnalysis;
import edu.illinois.gitsvn.analysis.EclipseJDTCoreAnalysis;
import edu.illinois.gitsvn.analysis.EclipseJDTDebugAnalysis;
import edu.illinois.gitsvn.analysis.EclipseJDTUIAnalysis;
import edu.illinois.gitsvn.analysis.EclipsePlatform;
import edu.illinois.gitsvn.analysis.EclipsePlatformCommon;
import edu.illinois.gitsvn.analysis.EclipsePlatformDebug;
import edu.illinois.gitsvn.analysis.EclipsePlatformTeam;
import edu.illinois.gitsvn.analysis.EclipsePlatformText;
import edu.illinois.gitsvn.analysis.JUnitAnalysis;
import edu.illinois.gitsvn.analysis.ThymeleafAnalysis;
import edu.illinois.gitsvn.analysis.UPMAnalysis;

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
		
		configurations.add(new UPMAnalysis());
		configurations.add(new CyclopsGroupAnalysis());
		configurations.add(new ThymeleafAnalysis());
		configurations.add(new EclipseJDTCoreAnalysis());
		configurations.add(new EclipseJDTDebugAnalysis());
		configurations.add(new EclipseJDTUIAnalysis());
		configurations.add(new EclipsePlatform());
		configurations.add(new EclipsePlatformTeam());
		configurations.add(new EclipsePlatformText());
		configurations.add(new EclipsePlatformDebug());
		configurations.add(new EclipsePlatformCommon());
		configurations.add(new JUnitAnalysis());
		//configurations.add(new LibreOfficeAnalysis());
		
		long before = System.nanoTime();
		runParallel(configurations);
		long after = System.nanoTime();
		
		System.out.println((after - before) / 1000000);
	}

	private static void run(List<AnalysisConfiguration> configurations) {
		for (int i = 0; i < configurations.size(); i++) {
			System.out.println("\n" + (i + 1) + " / " + configurations.size());
			configurations.get(i).run();
		}
	}
	
	private static void runParallel(List<AnalysisConfiguration> configurations) {
		ForkJoinPool pool = new ForkJoinPool();
		List<Callable<Void>> list = new ArrayList<>();
		
		for (final AnalysisConfiguration analysisConfiguration : configurations) {
			list.add(new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					analysisConfiguration.run();
					return null;
				}
			});
		}
		
		pool.invokeAll(list);
	}
}
