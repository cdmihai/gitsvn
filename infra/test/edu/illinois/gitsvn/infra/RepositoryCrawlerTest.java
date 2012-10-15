package edu.illinois.gitsvn.infra;

import java.util.List;

import org.gitective.core.CommitFinder;
import org.gitective.core.filter.commit.CommitCountFilter;
import org.gitective.core.filter.commit.CommitFilter;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

public class RepositoryCrawlerTest extends GitTestCase{
	
	private RepositoryCrawler crawler;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		add("file1.txt", "file1");
		add("file2.txt", "file2");
		
		crawler = new RepositoryCrawler();
	}
	
	@Test
	public void testOneFilter() throws Exception {
		crawler.addFilterClass(CommitCountFilter.class);
		List<CommitFilter> filters = crawler.crawlRepo(testRepo.getAbsolutePath());
		assertEquals(1,filters.size());
		CommitCountFilter filter = (CommitCountFilter) filters.get(0);
		assertEquals(2, filter.getCount());
	}
	
	@Test
	public void testTwoFilters() throws Exception {
		crawler.addFilterClass(CommitCountFilter.class);
		crawler.addFilterClass(CommitCountFilter.class);
		
		List<CommitFilter> filters = crawler.crawlRepo(testRepo.getAbsolutePath());
		assertEquals(2,filters.size());
		CommitCountFilter filter1 = (CommitCountFilter) filters.get(0);
		CommitCountFilter filter2 = (CommitCountFilter) filters.get(1);

		assertEquals(2,filter1.getCount());
		assertEquals(2,filter2.getCount());
	}
	
	@Test
	public void testNoFilters() throws Exception {
		List<CommitFilter> filters = crawler.crawlRepo(testRepo.getAbsolutePath());
		assertEquals(0,filters.size());
	}

}
