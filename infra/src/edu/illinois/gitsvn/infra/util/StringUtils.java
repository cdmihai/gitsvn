package edu.illinois.gitsvn.infra.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StringUtils {

	/**
	 * Retrieve the region from the given string between the given line delimiters.
	 * Line numbering starts from zero.
	 * Begin line is inclusive, end line is exclusive.
	 * @param string
	 * @param beginLine
	 * @param endLine
	 * @return
	 */
	public static String getLineDelimitedRegion(String string, int beginLine, int endLine){
		if (string == null)
			return null;

		if(beginLine == endLine)
			return "";

		List<String> lines = getLines(string);

		if(beginLine < 0 || beginLine > lines.size())
			throw new IllegalArgumentException("Line " + beginLine + " does not exist in " + string);

		if(endLine < 0 || endLine > lines.size())
			throw new IllegalArgumentException("Line " + endLine + " does not exist in " + string);

		StringBuffer sb = new StringBuffer();

		for(int line = beginLine; line < endLine; line++){
			sb.append(lines.get(line));
		}

		return sb.toString();
	}

	private static List<String> getLines(String s) {
		List<String> lines = new ArrayList<>();

		try (Scanner scanner = new Scanner(s)) {
			while (scanner.hasNextLine()) {
				lines.add(scanner.nextLine() + "\n");
			}
		}

		if (lines.size() > 0){
			String last = lines.get(lines.size() - 1);
			lines.set(lines.size() - 1, last.substring(0, last.length() - 1));
		}

		return lines;
	}

}