package edu.illinois.gitsvn.analysis.launchers;

import java.util.List;

import edu.illinois.gitsvn.analysis.CutoffGenericAnalysis;
import edu.illinois.gitsvn.analysis.GitSvnCutoffAnalysis;
import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.AnalysisLauncher;

public class SvnToGitRepoAnalysisLauncher extends AnalysisLauncher {

	@Override
	protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
//		configurations.add(new CutoffGenericAnalysis("../../projects/junit", "JUnit", 1231818542));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/junit", "JUnit", 1231818542));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/Arduino", "Arduino", 1284741802));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/cyclopsgroup", "CyclopsGroup", 1295380000));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/eclipse.jdt.core", "JDTCore", 1316790694));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/eclipse.jdt.debug", "JDTDebug", 1316538989));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/eclipse.jdt.ui", "JDTUI", 1316608366));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/eclipse.platform", "EclipsePlatform", 1317272400));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/eclipse.platform.common", "EclipsePlatformCommon", 1318741200));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/eclipse.platform.debug", "EclipsePlatformDebug", 1316408400));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/eclipse.platform.team", "EclipsePlatformTeam", 1316581200));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/eclipse.platform.text", "EclipsePlatformText", 1316149200));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/FFmpeg", "FFmpeg", 1295258573));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/libreoffice", "LibreOffice", 1282256340));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/MPS", "MPS", 1277726186));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/prototype", "prototype", 1205935492));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/thymeleaf", "Thymeleaf", 1337530081));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/upm-swing/", "UPM", 1287344636));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/astrid/", "astrid", 1273138392));
		
		configurations.add(new GitSvnCutoffAnalysis("../../svnToGitRepos/cassandra", "cassandra"));
		configurations.add(new GitSvnCutoffAnalysis("../../svnToGitRepos/Essentials", "Essentials"));
		configurations.add(new GitSvnCutoffAnalysis("../../svnToGitRepos/k-9", "k-9"));
		configurations.add(new GitSvnCutoffAnalysis("../../svnToGitRepos/liferay-plugins", "liferay-plugins"));
		configurations.add(new GitSvnCutoffAnalysis("../../svnToGitRepos/twitter4j", "twitter4j"));
		

	}

	public static void main(String[] args) throws Exception {
		new SvnToGitRepoAnalysisLauncher().run();
	}
}
