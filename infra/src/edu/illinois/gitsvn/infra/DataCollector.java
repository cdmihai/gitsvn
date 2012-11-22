package edu.illinois.gitsvn.infra;

/**
 * Interface that specifies how a collectors returns the data it finds.
 * 
 * @author caius
 *
 */
public interface DataCollector {

	public String name();
	
	public String getDataForCommit();
}
