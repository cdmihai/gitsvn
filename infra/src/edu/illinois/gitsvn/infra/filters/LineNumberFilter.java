package edu.illinois.gitsvn.infra.filters;

import java.util.Collection;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.gitective.core.filter.commit.DiffCountFilter;

public class LineNumberFilter extends DiffCountFilter{
	
	private int count;
	
	public LineNumberFilter(){
		super(true);
	}
	
	@Override
	protected boolean include(RevCommit commit, Collection<DiffEntry> diffs, int diffCount) {
		count = diffCount;
		
		return true;
	}
	
	public int getCount(){
		return count;
	}
}