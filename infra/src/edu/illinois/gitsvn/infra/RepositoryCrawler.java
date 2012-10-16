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

import edu.illinois.gitsvn.infra.filters.CompositeCommitFilter;

public class RepositoryCrawler {
	
	private List<Class<? extends CommitFilter>> filterClasses;
	
	public RepositoryCrawler() {
		filterClasses = new ArrayList<Class<? extends CommitFilter>>();
	}
	
	public void addFilterClass(Class<? extends CommitFilter> filterClass) {
		filterClasses.add(filterClass);
	}
	
	public void removeFilterClass(Class<? extends CommitFilter> filterClass) {
		filterClasses.remove(filterClass);
	}
	
	public List<CommitFilter> crawlRepo(String remoteRepoLoc) throws GitAPIException,
			InvalidRemoteException, TransportException {
		
		String cloneDirName = "repos/clone" + System.nanoTime();
		File cloneDir = new File(cloneDirName);
		cloneDir.deleteOnExit();
		
		Git repo = Git.cloneRepository().setURI(remoteRepoLoc)
				.setDirectory(cloneDir).call();
		
		CommitFinder finder = new CommitFinder(repo.getRepository());
		
		CompositeCommitFilter filter = null;
		try {
			filter = createFilter();
		} catch (InstantiationException | IllegalAccessException e) {
			System.out.println("Error creating filter:" + e.getMessage());
		}
		
		finder.setFilter(filter);
		finder.find();
		
		return filter.getFilters();
	}

	private CompositeCommitFilter createFilter() throws InstantiationException, IllegalAccessException {
		CompositeCommitFilter compositeFilter = new CompositeCommitFilter();
		
		for (Class<? extends CommitFilter> filterClass : filterClasses) {
			CommitFilter filterInstance = filterClass.newInstance();
			compositeFilter.addFilter(filterInstance);
		}
		
		return compositeFilter;
	}

}
