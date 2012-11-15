package edu.illinois.gitsvn.infra.filters.blacklister;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.gitective.core.filter.tree.BaseTreeFilter;

public final class FileExtensionBlacklister extends BaseTreeFilter {
	@Override
	public boolean include(TreeWalk walker) throws MissingObjectException, IncorrectObjectTypeException, IOException {
		return true;
	}
}