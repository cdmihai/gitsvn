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
	
	private Git git;
	
	private Ref masterRef;
	
	private static final String MASTER_REF_KEY = "refs/heads/master";
	 
	public BranchCollector(Repository repository) {
		this.repository = repository;
		this.git = new Git(repository);
		this.branchesCheckout();
		if(masterRef == null) {
			this.masterRef = this.findMasterRef();
		} 
	}
	private Ref findMasterRef() {
		Set<Entry<String, Ref>> refs = repository.getAllRefs().entrySet();
		for(Entry<String, Ref> ref: refs) {
			if(ref.getKey().equals(MASTER_REF_KEY)) {
				return ref.getValue();
			}
		}
		return null;
	}
	
	private String truncateBranchName(String branchName) {
		String truncatedBranchName = "";
		String[] branchNameArr;
		branchNameArr = branchName.split("origin/");
		if(branchNameArr.length >= 2) {
			truncatedBranchName = branchNameArr[1];
		} else {
			branchNameArr = branchName.split("svn/");
			if(branchNameArr.length >= 2) {
				truncatedBranchName = branchNameArr[1];
			} else {
				truncatedBranchName = branchName;
			}
		}
		return truncatedBranchName;
	}
	
	public void branchesCheckout() {
		String branchName = "", truncatedBranchName = "";
		Set<Entry<String, Ref>> refs = repository.getAllRefs().entrySet();
		for (Entry<String, Ref> ref : refs) {
			if(ref.getKey().startsWith(Constants.R_REMOTES) && !ref.getKey().contains(Constants.HEAD)) {
				branchName = ref.getValue().getName().split(Constants.R_REMOTES)[1];
				truncatedBranchName = this.truncateBranchName(branchName);
				System.out.println("Trying to checkout branch: " + branchName + " (" + truncatedBranchName + ")");
				CheckoutCommand checkoutCommand = this.git.checkout();
				checkoutCommand.setForce(true);
				checkoutCommand.setCreateBranch(true);
				checkoutCommand.setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK);
				checkoutCommand.setName(truncatedBranchName);
				checkoutCommand.setStartPoint(branchName);
				try {
					checkoutCommand.call();
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
		Set<Entry<String, Ref>> refs = repository.getAllRefs().entrySet();
		if(this.masterRef == null) {
			this.masterRef = this.findMasterRef();
		}
		boolean commitBelongsToBranch;
		boolean commitBelongsToMaster;
		List<String> foundInBranches = new ArrayList<String>();
		try {
			RevWalk walker = new RevWalk(this.repository); 
			RevCommit targetCommit = walker.parseCommit(this.repository.resolve(commit.getName()));
			for (Entry<String, Ref> ref : refs) {
				if (ref.getKey().startsWith(Constants.R_HEADS) && !ref.getKey().equals(this.masterRef)) {
					commitBelongsToBranch = walker.isMergedInto(targetCommit, walker.parseCommit(ref.getValue().getObjectId()));
					commitBelongsToMaster = false;
					if(this.masterRef != null) {
						commitBelongsToMaster = walker.isMergedInto(targetCommit, walker.parseCommit(this.masterRef.getObjectId()));
					}
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
