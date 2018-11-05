package info.softex.dictionary.core.attributes;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @since       version 5.1, 02/20/2017
 *
 * @author Dmitry Viktorov
 *
 */
public class IntegrityInfo {

    private final Map<String, TestInfo> tests = new LinkedHashMap<>();

    public void addTestResult(String inName, boolean inPassed) {
        addTestResult(inName, inPassed, null);
    }

    public void addTestResult(String inName, boolean inPassed, String inComments) {
        TestInfo testInfo = new TestInfo();
        testInfo.passed = inPassed;
        testInfo.comments = inComments;
        tests.put(inName, testInfo);
    }

    private class TestInfo {
        boolean passed;
        String comments;
    }

}
