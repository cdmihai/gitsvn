package edu.illinois.gitsvn.infra.collectors.diff;

import java.util.Collection;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;

import edu.illinois.gitsvn.infra.DataCollector;

/**
 * Code duplication of {@link AllLineNumberFilter}
 * @author mihai
 *
 */
public class ModifyFileAllLineNumberFilter extends ModifyDiffCountFilter implements DataCollector {

	private int count;

	@Override
	protected boolean include(RevCommit commit, Collection<DiffEntry> diffs, int diffCount) {
		count = diffCount;

		return true;
	}

	@Override
	public String name() {
			return "LOC";
	}

	@Override
	public String getDataForCommit() {
		return "" + count;
	}
}