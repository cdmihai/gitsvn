package edu.illinois.gitsvn.infra.collectors.diff;

import java.util.Collection;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.Edit;
import org.gitective.core.filter.commit.DiffCountFilter;

public class ModifyDiffCountFilter extends DiffCountFilter {

	@Override
	protected boolean acceptDiff(DiffEntry diff, Collection<Edit> edits) {
		return diff.getChangeType().equals(ChangeType.MODIFY) ? true : false;
	}
}