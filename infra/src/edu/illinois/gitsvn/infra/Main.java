package edu.illinois.gitsvn.infra;

import edu.illinois.gitsvn.infra.collectors.CSVCommitPrinter;



public class Main {

	/**
	 * Starts the analysis.
	 * 
	 * @param args
	 *            a list of repositories to crawl.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		RepositoryCrawler crawler = new RepositoryCrawler();
		
		addFilters(crawler);
		
		for (String remoteRepoLoc : args) {
			crawler.crawlRepo(remoteRepoLoc);
		}
	}

	private static void addFilters(RepositoryCrawler crawler) {
		crawler.addFilter(CSVCommitPrinter.class);
	}

}
