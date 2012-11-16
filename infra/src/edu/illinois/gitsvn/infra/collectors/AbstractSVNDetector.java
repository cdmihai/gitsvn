package edu.illinois.gitsvn.infra.collectors;

import edu.illinois.gitsvn.infra.DataCollector;


public interface AbstractSVNDetector extends DataCollector {

	public static String SVN = "SVN";
	public static String GIT = "GIT";

	public abstract String getMode();
}