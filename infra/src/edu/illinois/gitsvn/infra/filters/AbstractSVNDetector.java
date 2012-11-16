package edu.illinois.gitsvn.infra.filters;

import edu.illinois.gitsvn.infra.DataCollector;


public interface AbstractSVNDetector extends DataCollector {

	public static String SVN = "SVN";
	public static String GIT = "GIT";

	public abstract String getMode();
}