package edu.illinois.gitsvn.infra.collectors.diff.editfilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.lib.Repository;
import org.gitective.core.BlobUtils;

import edu.illinois.gitsvn.infra.util.StringUtils;

/**
 * EditFilter that collects all interesting edits.
 * It relies on the {@link EditFilter.sholdInclude()} hook to know if an edit is interesting or not.
 * @author mihai
 *
 */
public abstract class EditFilter{
	
	private Repository repo;
	
	public void setRepository(Repository repo){
		this.repo = repo;
	}
	
	public Collection<Edit> filterEdits(DiffEntry diff, Collection<Edit> edits){
		List<Edit> filteredEdits = new ArrayList<>();
		
		String oldContent = BlobUtils.getContent(repo, diff.getOldId().toObjectId());
		String newContent = BlobUtils.getContent(repo, diff.getNewId().toObjectId());
		
		for (Edit edit : edits) {
			String oldEditRegion = StringUtils.getLineDelimitedRegion(oldContent, edit.getBeginA(), edit.getEndA());
			String newEditRegion = StringUtils.getLineDelimitedRegion(newContent, edit.getBeginB(), edit.getEndB());
			
			
			if(shouldInclude(oldEditRegion, newEditRegion, edit))
				filteredEdits.add(edit);
		}
		
		return filteredEdits;
	}

	/**
	 * Hook that specifies whether the edit is interesting or not
	 * @param oldContent - the edit region from the old file
	 * @param newContent - the edit region from the new file
	 * @param edit - the actual edit object
	 * @return
	 */
	protected abstract boolean shouldInclude(String oldContent, String newContent, Edit edit);
}