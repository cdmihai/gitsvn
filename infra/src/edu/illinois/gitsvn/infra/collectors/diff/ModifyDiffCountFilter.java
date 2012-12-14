package edu.illinois.gitsvn.infra.collectors.diff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.lib.Repository;
import org.gitective.core.filter.commit.CommitFilter;
import org.gitective.core.filter.commit.DiffCountFilter;

import edu.illinois.gitsvn.infra.collectors.diff.editfilter.CommentEditFilter;
import edu.illinois.gitsvn.infra.collectors.diff.editfilter.EditFilter;
import edu.illinois.gitsvn.infra.collectors.diff.editfilter.FormatEditFilter;

/**
 * DiffCountFilter that accepts only diffs of type Modify. Also, it can be
 * configure with a list of {@link EditFilter} that further filters diffs' edits (such as formatting or comment edit)
 * 
 * @author mihai
 * 
 */
public class ModifyDiffCountFilter extends DiffCountFilter {

	private List<EditFilter> editFilters;

	public ModifyDiffCountFilter(EditFilter ... editFilters) {
		super(true);
		this.editFilters = Arrays.asList(editFilters);
	}

	public ModifyDiffCountFilter(boolean enableRenamings) {
		this();
	}

	@Override
	public CommitFilter setRepository(Repository repository) {
		for (EditFilter editFilter : editFilters) {
			editFilter.setRepository(repository);
		}

		return super.setRepository(repository);
	}

	@Override
	protected boolean acceptDiff(DiffEntry diff, Collection<Edit> edits) {
		return diff.getChangeType().equals(ChangeType.MODIFY) ? true : false;
	}

	@Override
	protected Collection<Edit> filterEdits(DiffEntry diff, Collection<Edit> edits) {
		for (EditFilter editFilter : editFilters) {
			edits = editFilter.filterEdits(diff, edits);
		}

		return edits;
	}

	public static EditFilter getFormatEditFilter() {
		return new FormatEditFilter();
	}

	public static EditFilter getCommentEditFilter() {
		return new CommentEditFilter();
	}
}