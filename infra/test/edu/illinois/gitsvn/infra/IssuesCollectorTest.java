package edu.illinois.gitsvn.infra;

import org.junit.Before;
import org.junit.Test;

import edu.illinois.gitsvn.infra.collectors.IssuesCollector;

public class IssuesCollectorTest extends DataCollectorTestCase {
	
	@Before
	public void before() {
		initTest(new IssuesCollector());
	}
	
	@Test
	public void testIssuesForSeveralIssueNumbers() throws Exception {
		add("test.txt", "some contents", "fixes issues #1, 3, 6");
		finder.find();
		assertEquals("3; ", collector.data);
	}
	
	@Test
	public void testIssuesForAndConcatenator() throws Exception {
		add("test.txt", "some contents", "fixing 601326, 601327, 601328 and 601329");
		finder.find();
		assertEquals("4; ", collector.data);
	}
	
	@Test
	public void testIssuesBugKeyword() throws Exception {
		add("test.txt", "some contents", "Bug 34434 Garbled message in log file");
		finder.find();
		assertEquals("1; ", collector.data);
	}
	
	@Test
	public void testIssuesLongMessage() throws Exception {
		add("test.txt", "some contents", "v20050726-1200. Platform UI's submission for I20050726-1200. The following fixes are included: + Bug 50323. [Workbench] Add a -showlabel &quot;Workspace Label&quot; startup option + Bug 66616. [PropertiesView] ComboBoxPropertyDescriptor doesn't work in PropertySheetView + Bug 69892. [Tests] Investigate UI test suites on Mac OS X + Bug 71946. 4 detached windows tests fail on Mac OS 10.3.3 + Bug 76714. [Dialogs] ContainerSelectDialog: Double clicking node in tree causes AssertionException + Bug 88791. [Wizards] TVT3.1 #93 - Variables button is truncated in New File advanced options in RUS + Bug 90353. [PresentationAPI] Cache widgets for recently opened perspectives + Bug 90355. [JFace] [Coolbars] Investigate the performance of subactionbars + Bug 92286. [WorkbenchParts] Request new API for EditorPart + Bug 93674. [Dialogs] ProgressMonitorDialog not cancellable when fork is set to false + Bug 96129. [MPE] (regression) MultiEditor.getInnerEditors() returns null. + Bug 100156. [IDE] RenameResourceAction should use IAdaptable for IResource lookuop + Bug 100325. [CellEditors] Table cell editor does not get deactivated on lost focus + Bug 100889. [Undo] - AbstractOperation toString() appends bogus comma + Bug 101346. [FastViews][WorkbenchParts] Problems with views drawing when they shouldn't + Bug 101434. [Contexts] performance: Slow cursor navigation in Text fields + Bug 101584. [Examples] - Build problem with new undo example + Bug 101950. About menu item in PDE-generated product says &quot;About Eclipse Platform&quot; + Bug 102033. [Performance] TableViewerTests too fast on windows + Bug 102081. Abandon the combo box of preferenceDialog + Bug 102127. [DetachedViews] attaching an editor to detached view cause ClassCastException + Bug 102439. [PropertiesView] PropertySheetEntry should format error message when calling setErrorText + Bug 102911. [KeyBindings] assist: Not correctly sized when window is narrow or short + Bug 103379. [MPE] [EditorMgmt] An editor instance is being leaked each time an editor is open and closed + Bug 104280. Support the 'toolbar' button on OS X + Bug 104657. [Undo] NPE in OperationHistoryActionHandler + Bug 104991. [WorkbenchParts] Cannot reopen closed views");
		finder.find();
		assertEquals("28; ", collector.data);
	}
	
	@Test
	public void testIssuesComplexMessage() throws Exception {
		add("test.txt", "some contents", "Backport of bug 388739 for bug 395681: [compiler] Improve simulation of javac6 behavior from bug 317719 after fixing bug 388795");
		finder.find();
		assertEquals("4; ", collector.data);
	}
	
	@Test
	public void testIssuesMixedNumbers() throws Exception {
		add("test.txt", "some contents", "H264: Fix intra only decoding. This possibly fixes issue2679 Signed-off-by: Michael Niedermayer <michaelni@gmx.at>");
		finder.find();
		assertEquals("1; ", collector.data);
	}
	
	@Test
	public void testIssuesDatesExclude() throws Exception {
		add("test.txt", "some contents", "free fr Subject: [FFmpeg-devel] [PATCH] Roundup issue #301 Date: Fri, 28 Dec 2007 19:22:18 +0100");
		finder.find();
		assertEquals("1; ", collector.data);
	}
	
	@Test
	public void testIssuesCloseKeyword() throws Exception {
		add("test.txt", "some contents", "segfault in amr decoder fix patch by (Richard van der Hoff - richvdh0) closes 1037380");
		finder.find();
		assertEquals("1; ", collector.data);
	}
	
	@Test
	public void testIssuesSpaceConcatenator() throws Exception {
		add("test.txt", "some contents", "Fixes 831 881 955 1123 1140");
		finder.find();
		assertEquals("5; ", collector.data);
	}
	
	@Test
	public void testJIRAIssue() throws Exception {
		add("test.txt", "some contents", "HHH-7943 C3P0 and Proxool OSGi support");
		finder.find();
		assertEquals("1; ", collector.data);
	}
	
	@Test
	public void testTwoJIRAIssues() throws Exception {
		add("test.txt", "some contents", "HHH-7850 BulkAccessorFactory Java 7 verify error resolved by JASSIST-163 BulkAccessorFactory.java.diff2 patch from Shigeru Chiba");
		finder.find();
		assertEquals("2; ", collector.data);
	}
	
	@Test
	public void testThreeJIRAIssues() throws Exception {
		add("test.txt", "some contents", "SPR-8830 SPR-8082 SPR-7833 + add support for CacheDefinitions declarations inside XML + more integration tests");
		finder.find();
		assertEquals("3; ", collector.data);
	}
	
	@Test
	public void testMixedIssues() throws Exception {
		add("test.txt", "some contents", "Merge pull request #320 from beamerblvd/SPR-10770");
		finder.find();
		assertEquals("2; ", collector.data);
	}
	
}
