package edu.illinois.gitsvn.infra;

import java.util.regex.Pattern;

import org.gitective.core.CommitFinder;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

import edu.illinois.gitsvn.infra.collectors.SHACollector;

public class SHA1CollectorTest extends GitTestCase {
	
	private DataCollectorWrapper collector;
	private CommitFinder finder;

	@Before
	public void before() {
		collector = new DataCollectorWrapper(new SHACollector());
		finder = new CommitFinder(testRepo);
		finder.setFilter(collector);
	}
	
	@Test
	public void testSHA1() throws Exception {
		add("test.txt","some contents","first");
		finder.find();
		
		assertTrue(Pattern.matches("[a-h0-9]*; ", collector.data));
	}

}
