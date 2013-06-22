package edu.illinois.gitsvn.infra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.illinois.gitsvn.infra.util.CSVWriter;

public class CSVwriterTest {

	private CSVWriter csv;
	private String[] headers;
	private String[] row1;
	private String[] row2;
	private String fileContent;
	

	@Before
	public void setUp() {
		csv = new CSVWriter();
		headers = new String[] { "a", "b", "c" };
		row1 = new String[]{"1", "2", "3"};
		row2 = new String[]{"11", "22", "22"};
		
		fileContent = "\"a\",\"b\",\"c\"\n\"1\",\"2\",\"3\"\n\"11\",\"22\",\"22\"";
	}

	@Test
	public void testHeader() {
		csv.addHeader(Arrays.asList(headers));

		List<String> header = csv.getHeader();
		List<List<String>> rows = csv.getRows();
		
		assertNotNull(header);
		assertTrue(rows.isEmpty());
		assertEquals(3, header.size());
		assertEquals(headers, header.toArray(new String[0]));
	}
	
	@Test
	public void testRows() {
		csv.addHeader(Arrays.asList(headers));
		csv.addRow(Arrays.asList(row1));
		csv.addRow(Arrays.asList(row2));

		List<List<String>> rows = csv.getRows();

		assertNotNull(rows);
		assertEquals(2, rows.size());
		assertEquals(headers, csv.getHeader().toArray());
		assertEquals(row1, rows.get(0).toArray());
		assertEquals(row2, rows.get(1).toArray());
	}
	
	@Test
	public void testFileWrite() throws IOException{
		String file = "TestFileWrite";
		
		csv.addHeader(Arrays.asList(headers));
		csv.addRow(Arrays.asList(row1));
		csv.addRow(Arrays.asList(row2));

		csv.dumpToFile(file);
		
		Path p = Paths.get(file);
		assertTrue(Files.exists(p));
		
		String actualFileContent = new String(Files.readAllBytes(p));
		assertEquals(fileContent, actualFileContent);
		
		Files.delete(p);
		assertTrue(!Files.exists(p));
	}
}
