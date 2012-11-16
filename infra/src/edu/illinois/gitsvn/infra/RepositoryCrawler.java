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
import edu.illinois.gitsvn.infra.collectors.LineNumberFilter;
import edu.illinois.gitsvn.infra.filters.blacklister.FileOperationBlacklister;

public class RepositoryCrawler {

	private List<Class<? extends CommitFilter>> filterClasses;

	public RepositoryCrawler() {
		filterClasses = new ArrayList<>();
	}

	public void addFilter(Class<? extends CommitFilter> filterClass) {
		filterClasses.add(filterClass);
	}

	public void removeFilterClass(Class<? extends CommitFilter> filterClass) {
		filterClasses.remove(filterClass);
	}

	public void crawlRepo(String remoteRepoLoc) throws GitAPIException, InvalidRemoteException, TransportException {

		String cloneDirName = "repos/clone" + System.nanoTime();
		File cloneDir = new File(cloneDirName);
		cloneDir.deleteOnExit();

		Git repo = Git.cloneRepository().setURI(remoteRepoLoc).setDirectory(cloneDir).call();

		CommitFinder finder = new CommitFinder(repo.getRepository());
		
		PipelineCommitFilter analysisFilter = new PipelineCommitFilter();
		
		analysisFilter.addFilter(FileOperationBlacklister.getDeleteDiffFilter());
		analysisFilter.addFilter(FileOperationBlacklister.getRenameDiffFilter());
		
		analysisFilter.addDataCollector(new LineNumberFilter(true));
		analysisFilter.addDataCollector(new LineNumberFilter(false));
		analysisFilter.addDataCollector(new CutofDetectorFilter());
		
		CSVCommitPrinter agregator = new CSVCommitPrinter();
		analysisFilter.setDataAgregator(agregator);
		
		finder.setFilter(analysisFilter);
		agregator.begin();
		finder.find();
		agregator.end();
	}
}
