package edu.illinois.gitsvn.infra.filters.blacklister;

import java.io.IOException;
import java.util.regex.Pattern;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;

/**
 * Checks for comment and unused imports keywords.
 * 
 * Rejects the commit if: (detect > 1 keywords) OR (detect 1 keyword AND has less than 5 words)
 * @author mihai
 *
 */
public class CopyrightJavadocImportBlacklister extends CommitFilter {

	private static final Pattern JAVADOC_PATTERN = Pattern.compile("javadoc", Pattern.CASE_INSENSITIVE);
	private static final Pattern COPYRIGHT_PATTERN = Pattern.compile("copyright", Pattern.CASE_INSENSITIVE);
	private static final Pattern UNUSED_IMPORTS_PATTERN = Pattern.compile("unused imports", Pattern.CASE_INSENSITIVE);
	private static final int NR_ENOUGH_WORDS = 5;
	
	@Override
	public boolean include(RevWalk walker, RevCommit cmit)
			throws StopWalkException, MissingObjectException,
			IncorrectObjectTypeException, IOException {
		
		String shortMsg = cmit.getShortMessage();
		int nrMatches = getNrPatternMatches(shortMsg);
		
		if (nrMatches > 1)
			return false;
		else if (nrMatches == 1)
			return hasEnoughWords(shortMsg);
		
		return true;
	}

	private boolean hasEnoughWords(String message) {
		return message.split("\\s+").length >= NR_ENOUGH_WORDS;
	}

	private int getNrPatternMatches(String message) {
		int nrMatches = 0;
		
		if (JAVADOC_PATTERN.matcher(message).find())
			nrMatches ++;
		
		if (COPYRIGHT_PATTERN.matcher(message).find())
			nrMatches ++;
		
		if (UNUSED_IMPORTS_PATTERN.matcher(message).find())
			nrMatches ++;
		
		return nrMatches;
	}
}
