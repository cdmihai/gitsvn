package edu.illinois.gitsvn.infra.filters;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a service for filters to get their required info. You
 * push an information here, and then the filters pull it a the
 * required time.
 * 
 * @author caius
 *
 */
public class MetadataService {
	
	private Map<String, String> infoMap = new HashMap<String, String>();
	
	private MetadataService() {
	}
	
	private static class Service{
		public static final ThreadLocal<MetadataService> service = new ThreadLocal<MetadataService>(){
			@Override
			protected MetadataService initialValue() {
				return new MetadataService();
			}
		};
		
	}
	
	public static MetadataService getService() {
		return Service.service.get();
	}
	
	public void pushInfo(String name, String value) {
		infoMap.put(name, value);
	}
	
	public String getInfo(String name) {
		return infoMap.get(name);
	}
}
