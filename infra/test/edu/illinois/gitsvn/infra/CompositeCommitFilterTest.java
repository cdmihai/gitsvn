package edu.illinois.gitsvn.infra;

import org.gitective.core.CommitFinder;
import org.gitective.core.filter.commit.CommitCountFilter;
import org.gitective.tests.GitTestCase;
import org.junit.Test;

import edu.illinois.gitsvn.infra.filters.AnalysisCompositeFilter;

public class CompositeCommitFilterTest extends GitTestCase {	

	@Test
	public void testOneFilter() throws Exception {
		add("file1.txt", "file1");
		add("file2.txt", "file2");
		
		CommitCountFilter filter = new CommitCountFilter();
		AnalysisCompositeFilter compositeFilter = new AnalysisCompositeFilter();
		compositeFilter.addFilter(new AnalysisFilterAdapter(filter));
		
		CommitFinder finder = new CommitFinder(testRepo);
		finder.setFilter(compositeFilter);
		finder.find();

		assertEquals(2,filter.getCount());
	}
	
	@Test
	public void testTwoFilters() throws Exception {
		add("file1.txt", "file1");
		add("file2.txt", "file2");
		
		CommitCountFilter filter1 = new CommitCountFilter();
		CommitCountFilter filter2 = new CommitCountFilter();
		AnalysisCompositeFilter compositeFilter = new AnalysisCompositeFilter();
		compositeFilter.addFilter(new AnalysisFilterAdapter(filter1));
		compositeFilter.addFilter(new AnalysisFilterAdapter(filter2));
		
		CommitFinder finder = new CommitFinder(testRepo);
		finder.setFilter(compositeFilter);
		finder.find();

		assertEquals(2,filter1.getCount());
		assertEquals(2,filter2.getCount());
	}
	
	@Test
	public void testNoFilters() throws Exception {
		add("file1.txt", "file1");
		add("file2.txt", "file2");
		
		AnalysisCompositeFilter compositeFilter = new AnalysisCompositeFilter();
		
		CommitFinder finder = new CommitFinder(testRepo);
		finder.setFilter(compositeFilter);
		finder.find();
	}
}
