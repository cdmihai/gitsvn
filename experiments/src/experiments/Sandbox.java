package experiments;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;

public class Sandbox {
	private static final String offset = "/tmp/test";
	private static final String localRepo = "repo";

	private static Path offsetPath;
	private static Path localRepoPath;
	private static Git git;

	public static void main(String args[]) throws IOException {
		init();
		run();
		destroy();
	}

	private static void run() throws IOException {
		Repository rep = GitHelper.create(localRepoPath);
	}

	private static void init() throws IOException {
		offsetPath = Paths.get(offset);
		localRepoPath = Paths.get(offset, localRepo);
		
		Files.createDirectories(localRepoPath);
		assert Files.exists(localRepoPath) : "Should exist" + localRepoPath;
	}

	private static void delete(Path file) throws IOException {
		Files.deleteIfExists(file);
		assert !Files.exists(file);
	}

	private static void destroy() throws IOException {
		Files.walkFileTree(offsetPath, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {

				delete(file);

				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc)
					throws IOException {

				delete(dir);

				return FileVisitResult.CONTINUE;
			}

		});
	}

}
