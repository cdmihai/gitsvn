package edu.illinois.gitsvn.infra.collectors;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.gitective.core.filter.commit.DiffCountFilter;
import org.gitective.core.filter.tree.BaseTreeFilter;

import edu.illinois.gitsvn.infra.DataCollector;

public class CppLineNumberCollector extends DiffCountFilter implements
		DataCollector {
	
	private int count;

	@Override
	protected TreeWalk createTreeWalk(RevWalk walker, RevCommit commit) {
		TreeWalk walk = super.createTreeWalk(walker, commit);

		TreeFilter previousFilter = walk.getFilter();
		TreeFilter newFilter = AndTreeFilter.create(
				new BaseTreeFilter() {
					
					
					private final List<String> allowedExtensions = Arrays.asList("cpp", "hpp",  "c", "h", "cxx", "hxx");
					
					@Override
					public boolean include(TreeWalk walker) throws MissingObjectException, IncorrectObjectTypeException, IOException {
						if (walker.isSubtree()){
							return true;
						}
						
						String name = walker.getNameString();
						name = name.toLowerCase();
						final int extensionStart = name.lastIndexOf('.') + 1;
						
						// Ignore names that don't contain a '.' or end with a '.'
						if (extensionStart == 0 || extensionStart == name.length())
							return false;
						
						name = name.substring(extensionStart);
						
						return allowedExtensions.contains(name);
					}
				}, previousFilter);
		walk.setFilter(newFilter);

		return walk;
	}  @Override
	protected boolean include(RevCommit commit, Collection<DiffEntry> diffs,
			int diffCount) {
		count = diffCount;
		return true;
	}

	@Override
	public String name() {
		return "CppLOC";
	}

	@Override
	public String getDataForCommit() {
		return "" + count;
	}

}
