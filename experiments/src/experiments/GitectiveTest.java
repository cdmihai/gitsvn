package experiments;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.gitective.core.CommitFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GitectiveTest {

	private final String testRepo = "../test_repos/linear";
	private Git gitRepo;
	private final String firstVersion = "dce9541aaaca8c9d54d035af349c255a8ea72b30";

	@Before
	public void setUp() throws Exception {
		gitRepo = Git.open(new File(testRepo));
		assertNotNull(gitRepo);
	}

	@After
	public void testDown() throws Exception {
		gitRepo = null;
	}

	@Test
	public void testGetCommit() throws Exception {
		CommitFinder finder = new CommitFinder(gitRepo.getRepository());
		finder.setFilter(new RevFilter() {

			@Override
			public boolean include(RevWalk walker, RevCommit cmit)
					throws StopWalkException, MissingObjectException,
					IncorrectObjectTypeException, IOException {
				String fullMessage = cmit.getFullMessage();
				System.out.println(fullMessage);
				return false;
			}

			@Override
			public RevFilter clone() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		finder = finder.find();
	}
}
