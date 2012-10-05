package experiments;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepository;

public class Sandbox {
	private static final String parentDir = "./tmp/test";
	private static final String localRepo = "repo";
	private static final String remoteRepo = "https://github.com/caiusb/tomighty.git";

	private static final Path parentDirPath = Paths.get(parentDir);
	private static final Path localRepoPath = Paths.get(parentDir, localRepo);
	
	private static Git git;

	public static void main(String args[]) throws IOException {
		destroy();
		init();
		run();
	}

	private static void run() throws IOException {
		
		try {
			git = Git.cloneRepository().setURI(remoteRepo).setDirectory(localRepoPath.toFile()).call();
			Repository repository = git.getRepository();
			ObjectId head = repository.resolve(Constants.HEAD);
			RevWalk revWalk = new RevWalk(repository);
			String fullMessage = revWalk.parseCommit(head).getFullMessage();
			System.out.println(fullMessage);
		} catch (InvalidRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("finished");

	}

	private static void init() throws IOException {
		
		Files.createDirectories(localRepoPath);
		assert Files.exists(localRepoPath) : "Should exist" + localRepoPath;
	}

	private static void delete(Path file) throws IOException {
		Files.deleteIfExists(file);
		assert !Files.exists(file);
	}

	private static void destroy() throws IOException {
		Files.walkFileTree(parentDirPath, new SimpleFileVisitor<Path>() {
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
