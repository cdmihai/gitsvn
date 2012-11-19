package edu.illinois.gitsvn.infra;

import org.gitective.core.CommitFinder;
import org.gitective.core.filter.commit.CommitFilter;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

import edu.illinois.gitsvn.infra.collectors.DateCollector;

public class DateCollectorTest extends GitTestCase {
	
	private DataCollectorWrapper collector;
	private CommitFinder finder;
	
	@Before
	public void before() {
		collector = new DataCollectorWrapper(new DateCollector());
		finder = new CommitFinder(testRepo);
		finder.setFilter(collector);
	}
	
	@Test
	public void testTime() throws Exception {
		long currentTime = System.currentTimeMillis()/1000;
		add("test.txt", "some contents", "first");
		finder.find();
		String expected = currentTime + "; ";
		String actual = collector.bla;
		
		assertEquals(expected, actual);
	}

}
