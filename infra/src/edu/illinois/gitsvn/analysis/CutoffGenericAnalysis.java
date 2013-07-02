package edu.illinois.gitsvn.analysis;

import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;

public class CutoffGenericAnalysis extends GenericAnalysis {

	private int cutoffPoint; //time stamp of last SVN commit

	public CutoffGenericAnalysis(String repoDir, String projectName, int cutoffTime) {
		super(repoDir, projectName);
		this.cutoffPoint = cutoffTime;
	}
	
	@Override
	protected PipelineCommitFilter configurePipelineAnalysis() {
	
		 PipelineCommitFilter pipeline = super.configurePipelineAnalysis();
		 
		 pipeline.addDataCollector(new CutofDetectorFilter(cutoffPoint));
		 
		 return pipeline;
	}
}
