package edu.illinois.gitsvn.infra.collectors.diff.editfilter;

import java.util.Scanner;

import org.eclipse.jgit.diff.Edit;

public class CommentEditFilter extends EditFilter {
	
	private final static String[] commentHeaders = new String[]{"*", "//", "/*", "/**", "#"};

	@Override
	protected boolean shouldInclude(String oldContent, String newContent, Edit edit) {
		boolean allLinesHaveHeader = linesStartWithHeader(oldContent, commentHeaders) && linesStartWithHeader(newContent, commentHeaders);
		
		return !allLinesHaveHeader;
	}

	/**
	 * Checks that each line from the content parameter starts with either one of the headers
	 * @param content
	 * @param headers
	 * @return
	 */
	private boolean linesStartWithHeader(String content, String ... headers) {
		if(content.equals(""))
			return true;
		
		try(Scanner s = new Scanner(content)) {
			while(s.hasNextLine()){
				String line = s.nextLine().trim();
				
				if (!lineHasAtLeastOneHeader(line, headers))
					return false;
			}
		}
		
		return true;
	}

	private boolean lineHasAtLeastOneHeader(String line, String... headers) {
		
		for (String header : headers) {
			if (line.startsWith(header))
				return true;
		}
		
		return false;
	}

}
