package edu.illinois.gitsvn.analysis;

import java.util.List;

import edu.illinois.gitsvn.infra.RepositoryCrawler;
import edu.illinois.gitsvn.infra.filters.AnalysisFilter;
import edu.illinois.gitsvn.infra.filters.CSVCommitPrinter;
import edu.illinois.gitsvn.infra.util.CSVWriter;

public class ThymeleafAnalysis {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String repoLoc = "https://github.com/thymeleaf/thymeleaf.git";

		RepositoryCrawler crawler = new RepositoryCrawler();

		crawler.addFilter(CSVCommitPrinter.class);
		List<AnalysisFilter> filters = crawler.crawlRepo(repoLoc);
		CSVCommitPrinter csvFilter = (CSVCommitPrinter) filters.get(0);
		CSVWriter csvWriter = csvFilter.getCSVWriter();
		csvWriter.dumpToFile("thymeleaf.csv");
	}

}
