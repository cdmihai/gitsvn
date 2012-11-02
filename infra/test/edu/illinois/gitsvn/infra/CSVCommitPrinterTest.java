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
		add("a1", "test\ntest\ntest");

		add("a1", "test\ntest1\ntest1");

		add("a1", "test\ntest1\ntest2");

		RepositoryCrawler c = new RepositoryCrawler();

		c.addFilter(CSVCommitPrinter.class);
		List<AnalysisFilter> filters = c.crawlRepo(testRepo.getAbsolutePath());

		CSVCommitPrinter csvfilter = (CSVCommitPrinter) filters.get(0);

		CSVWriter csvWriter = csvfilter.getCSVWriter();

		assertEquals(Arrays.asList(new String[] { "id", "SCM", "author", "time", "lines" }), csvWriter.getHeader());

		List<List<String>> rows = csvWriter.getRows();

		assertEquals(3, rows.size());

		System.out.println(csvWriter.getCSVString());
	}
}
