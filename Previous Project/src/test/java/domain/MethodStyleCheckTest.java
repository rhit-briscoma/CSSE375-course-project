package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import datasource.ClassFileReader;
import domain.checks.Check;
import domain.checks.style_checks.MethodStyleCheck;

public class MethodStyleCheckTest {

    private String banner = "--------Testing Method Names--------\n";
    
    @Test
    public void testMSCFailure() {
        String directoryPath = "target/test-classes/domain/MethodStyleTest/MethodStyleFail";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check methodStyleCheck = new MethodStyleCheck();
        analyzer.addCheck(methodStyleCheck);
        ArrayList<String> result = analyzer.runChecks();

        String confusingMethods = banner + "Confusing method name found. TestMETHOD differs from TestMethod only by capitalization\n";

        assertFalse(analyzer.getNodesNum() == 0);

        assertEquals(confusingMethods, result.get(1));
        // assertEquals(uppercaseNonConstant, result.get(3));
    }

    @Test
    public void testMSCPass() {
        String directoryPath = "target/test-classes/domain/MethodStyleTest/MethodStylePass";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check methodStyleCheck = new MethodStyleCheck();
        analyzer.addCheck(methodStyleCheck);
        ArrayList<String> result = analyzer.runChecks();

        String noConfusingMethods = banner + "No confusing method names.\n";

        assertFalse(analyzer.getNodesNum() == 0);

        assertEquals(noConfusingMethods, result.get(1));
        assertEquals(noConfusingMethods, result.get(3));
        assertEquals(noConfusingMethods, result.get(5));
    }
}
