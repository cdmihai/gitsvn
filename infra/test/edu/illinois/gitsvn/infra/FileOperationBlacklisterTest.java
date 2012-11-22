package edu.illinois.gitsvn.infra;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.gitective.core.CommitFinder;
import org.gitective.core.filter.commit.AndCommitFilter;
import org.gitective.core.filter.commit.CommitCountFilter;
import org.gitective.core.filter.commit.CommitDiffFilter;
import org.gitective.core.filter.commit.CommitFilter;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

import edu.illinois.gitsvn.infra.filters.blacklister.FileOperationBlacklister;

public class FileOperationBlacklisterTest extends GitTestCase {

	private static final int NOTHING = 0;
	private static final int DELETE = 1;
	private static final int RENAME = 2;

	private FileOperationBlacklister deleteFilter;
	private CommitCountFilter countFilter;
	private AndCommitFilter deleteCountAndFilter;
	private CommitFinder finder;
	private AndCommitFilter renameCountAndFilter;
	private CommitDiffFilter renameFilter;

	@Before
	public void setup() {
		deleteFilter = FileOperationBlacklister.getDeleteDiffFilter();
		renameFilter = FileOperationBlacklister.getRenameDiffFilter();
		countFilter = new CommitCountFilter();

		deleteCountAndFilter = new AndCommitFilter();
		deleteCountAndFilter.add(deleteFilter, countFilter);

		renameCountAndFilter = new AndCommitFilter();
		renameCountAndFilter.add(renameFilter, countFilter);

		finder = new CommitFinder(testRepo);
	}

	@Test
	public void testDeleteFilterEffect() throws Exception {
		runTest(DELETE, deleteCountAndFilter);
	}

	@Test
	public void testnoDeleteFilterInterference() throws Exception {
		runTest(NOTHING, deleteCountAndFilter);
	}

	@Test
	public void testRenameFilterEffect() throws Exception {
		runTest(RENAME, renameCountAndFilter);
	}

	@Test
	public void testnoRenameFilterInterference() throws Exception {
		runTest(NOTHING, renameCountAndFilter);
	}

	public void runTest(int repoAction, CommitFilter blackListerFilter) throws Exception {
		int expectedCommitCount = 3;
		int expectedFilteredCommitCount = 3;

		add("file/a.txt", "mumu", "c1");
		add("file/a1.txt", "mumu", "c2");
		add("file/a2.txt", "mumu", "c3");

		switch (repoAction) {
		case DELETE:
			delete("file");
			expectedCommitCount = 4;
			break;

		case RENAME:
			mv("file/a.txt", "file/rename.txt", "c4");

			expectedCommitCount = 4;

			// printRepo();
		}

		finder.setFilter(countFilter);
		finder.find();
		assertEquals(expectedCommitCount, countFilter.getCount());

		countFilter.reset();

		finder.setFilter(blackListerFilter);
		finder.find();
		assertEquals(expectedFilteredCommitCount, countFilter.getCount());
	}

	private void printRepo() throws IOException {
		Files.walkFileTree(testRepo.getParentFile().toPath(), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				System.out.println("\t" + file.getFileName() + ":" + file.getParent().getFileName());
				return super.visitFile(file, attrs);
			}

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				System.out.println(dir.getFileName());
				return super.preVisitDirectory(dir, attrs);
			}
		});
	}
}
