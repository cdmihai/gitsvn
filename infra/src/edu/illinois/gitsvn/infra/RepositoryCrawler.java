package edu.illinois.gitsvn.infra;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.gitective.core.CommitFinder;
import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.infra.collectors.CSVCommitPrinter;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;
import edu.illinois.gitsvn.infra.collectors.AllLineNumberFilter;
import edu.illinois.gitsvn.infra.collectors.JavaLineNumberFilter;
import edu.illinois.gitsvn.infra.filters.AnalysisFilter;
import edu.illinois.gitsvn.infra.filters.blacklister.FileOperationBlacklister;

public class RepositoryCrawler {

	public RepositoryCrawler() {
	}

	public void crawlRepo(String remoteRepoLoc, PipelineCommitFilter pipelineFilter) throws GitAPIException, InvalidRemoteException, TransportException {
		Git repo = cloneRepo(remoteRepoLoc);
		crawlRepo(repo, pipelineFilter);
	}

	public void crawlRepo(Git repo, PipelineCommitFilter pipelineFilter) {
		CommitFinder finder = new CommitFinder(repo.getRepository());
		
		pipelineFilter.setRepository(repo);
		AnalysisFilter agregator = pipelineFilter.getAgregator();
		
		finder.setFilter(pipelineFilter);
		agregator.begin();
		finder.find();
		agregator.end();
	}

	private Git cloneRepo(String remoteRepoLoc) throws GitAPIException,
			InvalidRemoteException, TransportException {
		String cloneDirName = "repos/clone" + System.nanoTime();
		File cloneDir = new File(cloneDirName);
		cloneDir.deleteOnExit();

		Git repo = Git.cloneRepository().setURI(remoteRepoLoc).setDirectory(cloneDir).call();
		return repo;
	}
}
