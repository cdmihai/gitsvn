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
//	private BufferedWriter log;
	
	public void setRepository(Repository repo){
		this.repo = repo;
		
//		try {
//			String pathname = "/home/mihai/workspaces/results/edits/" + MetadataService.getService().getInfo(CSVCommitPrinter.PROJ_NAME_PROP);
//			log = new BufferedWriter(new FileWriter(new File(pathname)));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
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
//			else{
//				try {
//					log.write(oldEditRegion);
//					log.write("\n-----------------------------------------------------------------\n");
//					log.write(newEditRegion);
//					
//					log.write("\n|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n");
//					
//					log.flush();
//					
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
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