package edu.illinois.gitsvn.infra.filters;


public interface AbstractSVNDetector {

	public static String SVN = "SVN";
	public static String GIT = "GIT";

	public abstract String getMode();
}