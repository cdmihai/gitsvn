package edu.illinois.gitsvn.analysis;

import edu.illinois.gitsvn.infra.RepositoryCrawler;
import edu.illinois.gitsvn.infra.collectors.CSVCommitPrinter;

public class CyclopsGroupAnalysis {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String repoLoc = "git://github.com/jiaqi/cyclopsgroup.git";

		RepositoryCrawler crawler = new RepositoryCrawler();

		crawler.crawlRepo(repoLoc);
	}

}
