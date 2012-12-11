package experiments;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StringUtils {

	public static String getLineDelimitedRegion(String s, int beginLine, int endLine){
		if (s == null)
			return s;
		
		if(beginLine == endLine)
			return "";
		
		List<String> lines = getLines(s);
		
		if(beginLine < 0 || beginLine > lines.size())
			throw new IllegalArgumentException("Line " + beginLine + " does not exist in " + s);
		
		if(endLine < 0 || endLine > lines.size())
			throw new IllegalArgumentException("Line " + endLine + " does not exist in " + s);
		
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
