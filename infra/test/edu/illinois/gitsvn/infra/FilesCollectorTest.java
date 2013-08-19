package edu.illinois.gitsvn.infra;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.illinois.gitsvn.infra.collectors.FilesCollector;

public class FilesCollectorTest extends DataCollectorTestCase {
	
	@Before
	public void before() {
		initTest(new FilesCollector());
	}
	
	@Test
	public void testOneFileAdd() throws Exception {
		add("test.txt", "some contents", "some message");
		finder.find();
		assertEquals("1; ", collector.data);
	}
	
	@Test
	public void testTwoFilesAdd() throws Exception {
		add(
			this.testRepo,
			new ArrayList<String>() {{ add("test.txt"); add("test2.txt");}}, 
			new ArrayList<String>() {{ add("some contents"); add("some text");}}, 
			"some message"
		);
		finder.find();
		assertEquals("2; ", collector.data);
	}
	
	@Test
	public void testThreeFilesAdd() throws Exception {
		add(
			this.testRepo,
			new ArrayList<String>() {{ add("test.txt"); add("test2.txt"); add("test3.txt");}}, 
			new ArrayList<String>() {{ add("some contents"); add("some text"); add("some chars");}}, 
			"some message"
		);
		finder.find();
		assertEquals("3; ", collector.data);
	}
	
	@Test
	public void testOneFileModification() throws Exception {
		add("test.txt", "some contents", "file added");
		add("test.txt", "modified contents", "file modified");
		
		finder.find();
		assertEquals("1; 1; ", collector.data);
	}
	
	@Test
	public void testTwoFilesModification() throws Exception {
		add(
			this.testRepo,
			new ArrayList<String>() {{ 
				add("test.txt"); 
				add("test2.txt"); 
			}}, 
			new ArrayList<String>() {{ 
				add("some contents"); 
				add("some text"); 
			}}, 
			"some message"
		);
		add(
			this.testRepo,
			new ArrayList<String>() {{ 
				add("test.txt"); 
				add("test2.txt"); 
			}}, 
			new ArrayList<String>() {{ 
				add("changed contents"); 
				add("changed text"); 
			}}, 
			"some message"
		);
		finder.find();
		assertEquals("2; 2; ", collector.data);
	}
	
}
