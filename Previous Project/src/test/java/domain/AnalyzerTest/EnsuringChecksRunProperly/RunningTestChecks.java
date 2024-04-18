package domain.AnalyzerTest.EnsuringChecksRunProperly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import datasource.ClassFileReader;
import domain.Analyzer;
import domain.checks.test_checks.TestCheck1;
import domain.checks.test_checks.TestCheck2;
import domain.checks.test_checks.TestCheck3;

public class RunningTestChecks {
    
    @Test
    public void testNoChecks() {
        String directoryPath = "target/test-classes/domain/AnalyzerTestClasses/";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory);
        
        ArrayList<String> result = analyzer.runChecks();

        assertEquals("--------Performing Checks for domain/AnalyzerTestClasses/AnalyzerDummyClass--------\n", result.get(0));

        boolean unableToAccessIndex1 = false;
        try {
            result.get(1);
        } catch (IndexOutOfBoundsException e) {
            unableToAccessIndex1 = true;
        }

        assertTrue(unableToAccessIndex1);
    }

    @Test
    public void testNoClassesToCheckNoChecks() {
        String directoryPath = "target/test-classes/domain/AnalyzerTestClasses/EmptyFolder";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory);
        
        ArrayList<String> result = analyzer.runChecks();

        boolean unableToAccessIndex0 = false;
        try {
            result.get(0);
        } catch (IndexOutOfBoundsException e) {
            unableToAccessIndex0 = true;
        }

        assertTrue(unableToAccessIndex0);
    }

    @Test
    public void testRunningTestCheck() {
        String directoryPath = "target/test-classes/domain/AnalyzerTestClasses/";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory);
        analyzer.addCheck(new TestCheck1());
        
        ArrayList<String> result = analyzer.runChecks();

        assertEquals("Test Check 1 result", result.get(1));
    }

    @Test
    public void testRunningTwoTestChecks() {
        String directoryPath = "target/test-classes/domain/AnalyzerTestClasses/";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory);
        analyzer.addCheck(new TestCheck1());
        analyzer.addCheck(new TestCheck2());
        
        ArrayList<String> result = analyzer.runChecks();

        assertEquals("Test Check 1 result", result.get(1));
        assertEquals("Test Check 2 result", result.get(2));
    }

    @Test
    public void testRunningTwoTestChecksAddedInOddOrder() {
        String directoryPath = "target/test-classes/domain/AnalyzerTestClasses/";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory);
        analyzer.addCheck(new TestCheck3());
        analyzer.addCheck(new TestCheck1());
        
        ArrayList<String> result = analyzer.runChecks();

        assertEquals("Test Check 3 result", result.get(1));
        assertEquals("Test Check 1 result", result.get(2));
    }

    @Test
    public void testNoClassesToCheckWithChecks() {
        String directoryPath = "target/test-classes/domain/AnalyzerTestClasses/EmptyFolder";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory);
        analyzer.addCheck(new TestCheck1());
        analyzer.addCheck(new TestCheck2());
        
        ArrayList<String> result = analyzer.runChecks();

        boolean unableToAccessIndex0 = false;
        try {
            result.get(0);
        } catch (IndexOutOfBoundsException e) {
            unableToAccessIndex0 = true;
        }

        assertTrue(unableToAccessIndex0);
    }
}