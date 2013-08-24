package edu.illinois.gitsvn.infra.collectors;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import edu.illinois.gitsvn.infra.collectors.SHACollector;

public class SHA1CollectorTest extends DataCollectorTestCase {
	
	@Before
	public void before() {
		initTest(new SHACollector());
	}
	
	@Test
	public void testSHA1() throws Exception {
		add("test.txt","some contents","first");
		finder.find();
		
		assertTrue(Pattern.matches("[a-h0-9]*; ", collector.data));
	}

}
