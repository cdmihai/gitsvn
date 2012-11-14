package edu.illinois.gitsvn.infra;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.gitective.core.CommitFinder;
import org.gitective.core.filter.commit.AndCommitFilter;
import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.infra.filters.AnalysisCompositeFilter;
import edu.illinois.gitsvn.infra.filters.AnalysisFilter;

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

	public List<AnalysisFilter> crawlRepo(String remoteRepoLoc) throws GitAPIException, InvalidRemoteException, TransportException {

		String cloneDirName = "repos/clone" + System.nanoTime();
		File cloneDir = new File(cloneDirName);
		cloneDir.deleteOnExit();

		Git repo = Git.cloneRepository().setURI(remoteRepoLoc).setDirectory(cloneDir).call();

		CommitFinder finder = new CommitFinder(repo.getRepository());
		AnalysisCompositeFilter analysisFilter = createAndAttachFilters(finder);

		analysisFilter.setRepository(repo.getRepository());
		analysisFilter.begin();
		finder.find();
		analysisFilter.end();

		return analysisFilter.getFilters();
	}

	private AnalysisCompositeFilter createAndAttachFilters(CommitFinder finder) {
		AnalysisCompositeFilter analysisFilter = null;
		try {
			analysisFilter = createAnalysisFilter();
		} catch (InstantiationException | IllegalAccessException e) {
			System.out.println("Error creating filter:" + e.getMessage());
		}

		AndCommitFilter aggregate = new AndCommitFilter();

		addCommitBlacklisters(aggregate);
		aggregate.add(analysisFilter);

		finder.setFilter(aggregate);
		
		return analysisFilter;
	}

	private void addCommitBlacklisters(AndCommitFilter andCommitFilter) {
		// TODO Auto-generated method stub
	}

	private AnalysisCompositeFilter createAnalysisFilter() throws InstantiationException, IllegalAccessException {
		AnalysisCompositeFilter compositeFilter = new AnalysisCompositeFilter();

		for (Class<? extends CommitFilter> filterClass : filterClasses) {
			final CommitFilter filterInstance = filterClass.newInstance();

			if (filterInstance instanceof AnalysisFilter)
				compositeFilter.addFilter((AnalysisFilter) filterInstance);
			else {
				compositeFilter.addFilter(new AnalysisFilterAdapter(filterInstance));
			}
		}

		return compositeFilter;
	}
}
