package edu.illinois.gitsvn.infra.filters;

import java.util.List;

import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.infra.DataCollector;

/**
 *	Represents an Analysis over the commits. (the commit analysis visitor).
 *	Will most likely end up as a template.
 * @author mihai
 */
public abstract class AnalysisFilter extends CommitFilter implements AnalysisLifecycle{

	protected List<DataCollector> collectors;
	
	public AnalysisFilter(List<DataCollector> collectors) {
		this.collectors = collectors;
	}
}
