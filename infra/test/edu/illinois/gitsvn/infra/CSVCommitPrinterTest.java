package edu.illinois.gitsvn.infra;

import java.util.Arrays;
import java.util.List;

import org.gitective.tests.GitTestCase;
import org.junit.Test;

import edu.illinois.gitsvn.infra.filters.AnalysisFilter;
import edu.illinois.gitsvn.infra.filters.CSVCommitPrinter;
import edu.illinois.gitsvn.infra.util.CSVWriter;

public class CSVCommitPrinterTest extends GitTestCase {
	
	@Test
	public void testSimpleRepo() throws Exception {
		add("a1", "c1");
		add("a2", "c2");
		add("a3", "c3");
		add("a4", "c4");
		
		RepositoryCrawler c = new RepositoryCrawler();
		
		c.addFilter(CSVCommitPrinter.class);
		List<AnalysisFilter> filters = c.crawlRepo(testRepo.getAbsolutePath());
		
		CSVCommitPrinter csvfilter = (CSVCommitPrinter) filters.get(0);
		
		CSVWriter csvWriter = csvfilter.getCSVWriter();
		
		assertEquals(Arrays.asList(new String[]{"id", "time"}), csvWriter.getHeader());
		
		List<List<String>> rows = csvWriter.getRows();
		
		assertEquals(4, rows.size());
		
		System.out.println(csvWriter.getCSVString());
	}
}
