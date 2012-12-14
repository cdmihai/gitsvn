package edu.illinois.gitsvn.infra.collectors.diff.editfilter;

import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.lib.Repository;

/**
 * EditFilter that accepts only edits that are not a result of formatting.
 * @author mihai
 *
 */
public class FormatEditFilter extends EditFilter{

	@Override
	protected boolean shouldInclude(String oldContent, String newContent, Edit edit) {
		oldContent = oldContent.replaceAll("\\s", "");
		newContent = newContent.replaceAll("\\s", "");
		
		return !oldContent.equals(newContent);
	}
}