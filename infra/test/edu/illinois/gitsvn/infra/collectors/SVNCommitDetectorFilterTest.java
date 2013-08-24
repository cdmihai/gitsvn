package edu.illinois.gitsvn.infra.collectors;

import org.junit.Before;
import org.junit.Test;

public class SVNCommitDetectorFilterTest extends DataCollectorTestCase {

	@Before
	public void before() {
		initTest(new SVNCommitDetectorFilter());
	}

	@Test
	public void testLastGit() throws Exception {
		add("test1.txt", "some contents\n", "a \n");
		add("test2.txt", "some contents\n", "b \n git-svn-id:sfsdfdsf");
		add("test3.txt", "some contents\n", "c \n git-svn-id:23f2fre");
		
		finder.find();
		String expected = "SVN; SVN; GIT; ";
		String actual = collector.data;

		assertEquals(expected, actual);
	}
	
	@Test
	public void testMiddleGit() throws Exception {
		add("test1.txt", "some contents\n", "b \n git-svn-id:sfsdfdsf");
		add("test2.txt", "some contents\n", "a \n");
		add("test3.txt", "some contents\n", "c \n :git-svn-id:23f2fre");
		
		finder.find();
		String expected = "SVN; GIT; SVN; ";
		String actual = collector.data;

		assertEquals(expected, actual);
	}
	
	@Test
	public void testFirstGit() throws Exception {
		add("test1.txt", "some contents\n", "b \n git-svn-id:sfsdfdsf");
		add("test2.txt", "some contents\n", "c \n git-svn-id:23f2fre");
		add("test3.txt", "some contents\n", "a \n");
		
		finder.find();
		String expected = "GIT; SVN; SVN; ";
		String actual = collector.data;

		assertEquals(expected, actual);
	}
	
	@Test
	public void testOnlyGit() throws Exception {
		add("test1.txt", "some contents\n", "b \n");
		add("test2.txt", "some contents\n", "c \n");
		add("test3.txt", "some contents\n", "a \n");
		
		finder.find();
		String expected = "GIT; GIT; GIT; ";
		String actual = collector.data;

		assertEquals(expected, actual);
	}
}
