package edu.illinois.gitsvn.infra.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Builds a CSV row by row.
 * 
 * Does not ensure equal number of columns. 
 * 
 * @author mihai
 *
 */
public class CSVWriter {
	private List<List<String>> rows;
	private List<String> headers;

	public CSVWriter() {
		rows = new ArrayList<>();
		headers = new LinkedList<>();
	}

	public void addHeader(List<String> headers) {
		this.headers = headers;
	}

	public List<String> getHeader() {
		return headers;
	}

	public void addRow(List<String> row) {
		rows.add(row);
	}

	public List<List<String>> getRows() {
		return rows;
	}

	/**
	 * Dump the contents to the provided path. If a file already at the existing path is deleted.
	 * 
	 * The object states remains unaffected.
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void dumpToFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);

		Files.deleteIfExists(path);
		Files.createFile(path);

		String content = constructCSV();

		Files.write(path, content.toString().getBytes());
	}
	
	public String getCSVString(){
		return constructCSV();
	}

	private String constructCSV() {
		StringBuffer content = new StringBuffer();

		String line = getLine(headers);
		appendNewLineToBuffer(content, line);
		

		for (List<String> row : rows) {
			line = getLine(row);
			
			appendNewLineToBuffer(content, line);
		}
		
		return content.substring(0, content.length() -1).toString();
	}

	private void appendNewLineToBuffer(StringBuffer buffer, String line) {
		buffer.append(line);
		buffer.append("\n");
	}

	private String getLine(List<String> line) {
		StringBuffer sb = new StringBuffer();

		for (String element : line) {
			sb.append(element);
			sb.append(",");
		}

		return sb.substring(0, sb.length() - 1);
	}
}
