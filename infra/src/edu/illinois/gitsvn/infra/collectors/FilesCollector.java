package edu.illinois.gitsvn.infra.collectors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.gitective.core.TreeUtils;
import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.infra.DataCollector;
//import org.gitective.core.filter.commit.CommitDiffFilter.LocalDiffEntry;

public class FilesCollector  extends CommitFilter implements DataCollector {

	private List<String> files;
	
	@Override
	public String name() {
		return "Number of modified files";
	}

	@Override
	public String getDataForCommit() {
		return  Integer.toString(files.size());
	}

	@Override
	public boolean include(RevWalk walker, RevCommit cmit) throws StopWalkException, MissingObjectException, IncorrectObjectTypeException, IOException {
		this.files = new ArrayList<String>();
		
		TreeWalk walk = TreeUtils.diffWithParents(walker, cmit);
		walk.setRecursive(true);
		
		while(walk.next()) {
			files.add(walk.getPathString());
		}
		
		return true;
	}
}