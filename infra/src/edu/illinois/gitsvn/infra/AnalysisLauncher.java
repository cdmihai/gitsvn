package edu.illinois.gitsvn.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;

/**
 * Class responsible for running the analyses
 * Subclasses should implement {@link populateWithConfigurations} in order to provide analysis configurations
 * @author mihai
 *
 */
public abstract class AnalysisLauncher {

	/**
	 * Starts the analysis. 
	 * @throws Exception
	 */
	public void run() throws Exception {
		List<AnalysisConfiguration> configurations = new ArrayList<AnalysisConfiguration>();
		
		populateWithConfigurations(configurations);
		
		long before = System.nanoTime();
		runParallel(configurations);
		long after = System.nanoTime();
		
		System.out.println((after - before) / 1000000);
	}

	protected abstract void populateWithConfigurations(List<AnalysisConfiguration> configurations);

	private void runSerial(List<AnalysisConfiguration> configurations) {
		for (int i = 0; i < configurations.size(); i++) {
			System.out.println("\n" + (i + 1) + " / " + configurations.size());
			configurations.get(i).run();
		}
	}
	
	private void runParallel(List<AnalysisConfiguration> configurations) {
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
