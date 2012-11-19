package edu.illinois.gitsvn.infra.filters.blacklister;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.gitective.core.CommitFinder;
import org.gitective.core.filter.tree.BaseTreeFilter;
import org.gitective.core.filter.tree.CommitTreeFilter;
import org.gitective.core.filter.tree.TypeCountFilter;
import org.gitective.tests.GitTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NonJavaFileExtensionBlacklisterTest extends GitTestCase {

	private List<String> paths;
	private List<String> contents;
	private TypeCountFilter counter;
	private CommitFinder finderWithoutBlacklist;
	private CommitFinder finderWithBlacklist;
	private BlacklistCounter blackListCounter;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		paths = Arrays.asList("a.txt", "b", "c.java", "f1/d.c", "f1/f2/e.cpp", "f1/f2/f3/f.py");
		contents = Arrays.asList("a", "a", "a", "a", "a", "a");
		add(testRepo, paths, contents, "c1");
		
		counter = TypeCountFilter.file();
		blackListCounter = new BlacklistCounter();
		
		finderWithoutBlacklist = new CommitFinder(testRepo);
		finderWithoutBlacklist.setFilter(new CommitTreeFilter(counter));
		
		finderWithBlacklist = new CommitFinder(testRepo);
		finderWithBlacklist.setFilter(new CommitTreeFilter(blackListCounter));
	}

	@After
	public void tearDown(){
		counter.reset();
		blackListCounter.reset();
	}
	
	@Test
	public void testNoFilterEffect() {
		finderWithoutBlacklist.find();
		
		assertEquals(6, counter.getCount());
	}
	
	@Test
	public void testFilterEffect() {
		finderWithBlacklist.find();
		
		assertEquals(1, blackListCounter.getCount());
	}

	
	static class BlacklistCounter extends BaseTreeFilter{
		NonJavaFileExtensionBlacklister extensionBlackLister = new NonJavaFileExtensionBlacklister();
		TypeCountFilter counter = TypeCountFilter.file();
		
		@Override
		public boolean include(TreeWalk walker) throws MissingObjectException, IncorrectObjectTypeException, IOException {
			if (extensionBlackLister.include(walker))
				counter.include(walker);
			
			return true;
		}
		
		public long getCount(){
			return counter.getCount();
		}
		
		@Override
		public BaseTreeFilter reset() {
			counter.reset();
			return super.reset();
		}
	}
}
