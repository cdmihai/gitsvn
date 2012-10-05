package experiments;

import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;

public class GitHelper {

	public static Repository create(Path repoPath) throws IOException {
		Repository newRepo = new FileRepository(repoPath.toString());
		newRepo.create();

		return newRepo;
	}

}