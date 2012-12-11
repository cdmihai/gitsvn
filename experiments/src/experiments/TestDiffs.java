package experiments;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.revwalk.RevCommit;
import org.gitective.core.BlobUtils;
import org.gitective.core.CommitFinder;
import org.gitective.core.filter.commit.CommitDiffEditFilter;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestDiffs extends GitTestCase {

	private static final String CMIT = "cmit";
	private CommitFinder finder;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		finder = new CommitFinder(testRepo);
	}

	private void repo1() throws Exception {
		List<String> files = Arrays.asList(new String[]{"f1"});
		List<String> content = Arrays.asList(new String[]{
				"first line\n" 
				+ "second line\n" 
				+ "third line\n"
				+ "fourth line\n" 
				+ "fifth line\n"
				+ "sixth line\n"
				});

		add(files, content);
		
		files = Arrays.asList(new String[]{"f1", "f2"});
		content = Arrays.asList(new String[]{
				"second line\n" 
				+ "third changed line\n"
				+ "fourth line\n" 
				+ "replaced content\n"
				+ "sixth line\n"
				+ "seventh line",
				"content"});

		add(testRepo, files, content, CMIT);
	}
	
	private void repo2() throws Exception {
		List<String> files = Arrays.asList(new String[]{"f1"});
		List<String> content = Arrays.asList(new String[]{
				"first line\n" 
				+ "second line\n" 
				+ "third line\n"
				+ "fourth line\n" 
				+ "fifth line\n"
				+ "sixth line\n"
				});

		add(files, content);
		
		files = Arrays.asList(new String[]{"f1"});
		content = Arrays.asList(new String[]{
				"second line\n" 
				+ "third changed line\n"
				+ "fourth line\n" 
				+ "replaced content\n"
				+ "sixth line\n"
				+ "seventh line"
				});

		add(testRepo, files, content, CMIT);
	}

	@Ignore
	@Test
	public void testDiffGranularity() throws Exception {
		repo1();
		
		finder.setFilter(new CommitDiffEditFilter(true) {
			@Override
			protected boolean include(RevCommit commit, DiffEntry diff, Collection<Edit> edits) {
				if(!commit.getFullMessage().equals(CMIT))
					return true;
				
				System.out.println(diff.getNewPath());
				
				for (Edit edit : edits) {
					System.out.println(edit.getType()  +" - "+ edit.getBeginA() + ";" + edit.getEndA() );
				}
				
				System.out.println();
				
				return true;
			}
		});
		
		finder.find();
	}
	
	@Test
	public void testPrintDiffs() throws Exception {
		repo2();
		
		finder.setFilter(new CommitDiffEditFilter(true) {
			@Override
			protected boolean include(RevCommit commit, DiffEntry diff, Collection<Edit> edits) {
				if(!commit.getFullMessage().equals(CMIT))
					return true;
				
				System.out.println(diff.getNewPath());
				
				String fOld = getOldFile(diff);
				String fNew = getNewFile(diff);
				
				System.err.println(fOld);
				System.err.println();
				System.err.println(fNew);
				
				for (Edit edit : edits) {
					System.out.println(edit.getType());
					System.out.println("Old:\n" + StringUtils.getLineDelimitedRegion(fOld, edit.getBeginA(), edit.getEndA()));
					System.out.println("New:\n" + StringUtils.getLineDelimitedRegion(fNew, edit.getBeginB(), edit.getEndB()));
					System.out.println();
				}
				
				System.out.println();
				
				return true;
			}

			private String getOldFile(DiffEntry diff) {
				try {

					File f = new File(diff.getOldPath());
					return BlobUtils.getContent(Git.open(testRepo).getRepository(), diff.getOldId().toObjectId());

				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
			
			private String getNewFile(DiffEntry diff) {
				try {
					return BlobUtils.getContent(Git.open(testRepo).getRepository(), diff.getNewId().toObjectId());
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
		
		finder.find();
	}
}
