package edu.illinois.gitsvn.infra.filters;

import static org.junit.Assert.*;

import org.junit.Test;

public class MetadataServiceTest {

	@Test
	public void testPushInfo() {
		MetadataService service = MetadataService.getService();
		service.pushInfo("time", "100");
		String info = service.getInfo("time");
		assertEquals("100", info);
	}
	
	@Test
	public void getInexistentInfo() {
		MetadataService service = MetadataService.getService();
		String info = service.getInfo("nonexistent");
		assertNull(info);
	}

}
