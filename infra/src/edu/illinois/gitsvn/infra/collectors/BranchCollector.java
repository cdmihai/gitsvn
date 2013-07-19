package edu.illinois.gitsvn.infra.collectors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.CheckoutEntry;
import org.eclipse.jgit.storage.file.ReflogEntry;
import org.eclipse.jgit.util.StringUtils;
import org.gitective.core.GitException;
import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.infra.DataCollector;

public class BranchCollector extends CommitFilter implements DataCollector {

	private String branchName;
	
	private Repository repository;
	
	private Git git;
	
	private Set<Entry<String, Ref>> refs;
	
	private Ref masterRef;
	
	private static final String MASTER_REF_KEY = "refs/heads/master";
	 
	public BranchCollector(Repository repository) {
		this.repository = repository;
		this.git = new Git(repository);
		this.refs = repository.getAllRefs().entrySet();
		this.masterRef = this.findMasterRef();
		
		this.branchesCheckout();
	}
	private Ref findMasterRef() {
		for(Entry<String, Ref> ref: this.refs) {
			if(ref.getKey() == MASTER_REF_KEY) {
				return ref.getValue();
			}
		}
		return null;
	}
	
	private void branchesCheckout() {
		String branchName = "";
		for (Entry<String, Ref> ref : this.refs) {
			if(ref.getKey().startsWith(Constants.R_REMOTES) && !ref.getKey().contains(Constants.HEAD)) {
				branchName = ref.getValue().getName().split(Constants.R_REMOTES)[1];
				//TODO: replace with logging
				System.out.println("Trying to checkout branch: " + branchName + " (" + branchName.split("origin/")[1] + ")");
				CheckoutCommand checkoutCommand = this.git.checkout();
				checkoutCommand.setForce(true);
				checkoutCommand.setCreateBranch(true);
				checkoutCommand.setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK);
				checkoutCommand.setName(branchName.split("origin/")[1]);
				checkoutCommand.setStartPoint(branchName);
				try {
					Ref result = checkoutCommand.call();
					//TODO: replace with logging
					//println "Successfully checked out branch " + branchName;
				} catch (RefAlreadyExistsException e) {
					//TODO: replace with logging
					//println "Skipping branch (already exists): " + branchName;
				} catch (CheckoutConflictException e) {
					//TODO: replace with logging
//					println "There were conflicts on ${branchName} branch checkout: " + e.getMessage()
				} catch (RefNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidRefNameException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch ( GitAPIException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * find out which branch that specified commit come from.
	 *
	 * @param commit
	 * @return branch name.
	 * @throws GitException
	 */
	public String getBranchName(RevCommit commit) throws GitException {
		List<String> foundInBranches = new ArrayList<String>();
		try {
			RevWalk walker = new RevWalk(this.repository); 
			RevCommit targetCommit = walker.parseCommit(this.repository.resolve(commit.getName()));
			for (Entry<String, Ref> ref : this.refs) {
				if (ref.getKey().startsWith(Constants.R_HEADS) && !ref.getKey().equals(this.masterRef)) {
					boolean commitBelongsToBranch = walker.isMergedInto(targetCommit, walker.parseCommit(ref.getValue().getObjectId()));
					boolean commitBelongsToMaster = walker.isMergedInto(targetCommit, walker.parseCommit(this.masterRef.getObjectId()));
					if (commitBelongsToBranch && !commitBelongsToMaster) {
						foundInBranches.add(ref.getValue().getName());
					} 
				} 
			}
			
		} catch ( GitException | MissingObjectException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IncorrectObjectTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(foundInBranches.size() == 0) {
			return "master";
		} 
		return StringUtils.join(foundInBranches, ", ");
	}
	
	/**
	 * 
	 * @deprecated
	 * @param walker
	 * @param commit
	 * @return
	 */
	public String oldGetBranchName(RevWalk walker, RevCommit commit) {
		try {
			Collection<ReflogEntry> entries = this.git.reflog().call();
			for (ReflogEntry entry:entries){
				if (!entry.getOldId().getName().equals(commit.getName())){
					continue;
				}

				CheckoutEntry checkOutEntry = entry.parseCheckout();
				if (checkOutEntry != null){
					return checkOutEntry.getFromBranch();
				}
			}

			return null;
		} catch (Exception e) {
			throw new GitException("failed to get ref log: " + e.getMessage(), this.repository );
		}
	}
	
	@Override
	public boolean include(RevWalk walker, RevCommit cmit)
			throws StopWalkException, MissingObjectException,
			IncorrectObjectTypeException, IOException {
		try {
			branchName = this.getBranchName(cmit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String name() {
		return "Branch name";
	}

	@Override
	public String getDataForCommit() {
		return branchName;
	}

}
