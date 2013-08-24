package edu.illinois.gitsvn.infra;

import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.gitective.core.CommitFinder;
import org.gitective.core.filter.commit.CommitFilter;
import org.gitective.tests.GitTestCase;

import edu.illinois.gitsvn.infra.util.DataCollectorWrapper;

public abstract class DataCollectorTestCase extends GitTestCase {

        protected DataCollectorWrapper collector;
        protected CommitFinder finder;

        public void initTest(DataCollector collectorUnderTest) {
                collector = new DataCollectorWrapper(collectorUnderTest);
                try {
                        ((CommitFilter)collectorUnderTest).setRepository(Git.open(testRepo).getRepository());
                } catch (IOException e) {
                }
                finder = new CommitFinder(testRepo);
                finder.setFilter(collector);
        }

}