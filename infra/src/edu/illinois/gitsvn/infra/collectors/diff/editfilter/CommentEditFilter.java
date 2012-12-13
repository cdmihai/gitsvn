package edu.illinois.gitsvn.infra.collectors.diff.editfilter;

import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.lib.Repository;

public class CommentEditFilter extends EditFilter {

	@Override
	protected boolean shouldInclude(String oldContent, String newContent, Edit edit) {
		// TODO Auto-generated method stub
		return false;
	}

}
