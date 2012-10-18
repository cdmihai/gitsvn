package edu.illinois.gitsvn.infra.filters;

import org.gitective.core.filter.commit.CommitFilter;

/**
 *	Represents an Analysis over the commits. (the commit analysis visitor).
 *	Will most likely end up as a template.
 * @author mihai
 */
public abstract class AnalysisFilter extends CommitFilter implements AnalysisLifecycle{
}
