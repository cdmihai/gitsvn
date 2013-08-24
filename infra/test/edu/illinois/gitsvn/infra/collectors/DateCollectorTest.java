package edu.illinois.gitsvn.infra.collectors;

import org.junit.Before;
import org.junit.Test;

import edu.illinois.gitsvn.infra.collectors.DateCollector;


public class DateCollectorTest extends DataCollectorTestCase {
	
	@Before
	public void before() {
		initTest(new DateCollector());
	}
	
	@Test
	public void testTime() throws Exception {
		long currentTime = System.currentTimeMillis()/1000;
		add("test.txt", "some contents", "first");
		finder.find();
		String expected = currentTime + "; ";
		String actual = collector.data;
		
		assertEquals(expected, actual);
	}

}
