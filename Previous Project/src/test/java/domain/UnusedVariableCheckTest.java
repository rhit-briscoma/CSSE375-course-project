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
import domain.checks.style_checks.UnusedVariableChecker;

public class UnusedVariableCheckTest {
    
    @Test
    public void testUVCFailure() {
        String directoryPath = "target/test-classes/domain/UnusedVariableTest/UnusedVariableFail";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check UnusedVariableCheck = new UnusedVariableChecker();
        analyzer.addCheck(UnusedVariableCheck);
        ArrayList<String> result = analyzer.runChecks();

        String unusedClassField = "Unused class field found: classField" + "\n";
        String unusedMethodField = "Unused local variable found at index 0 in method hasUnusedField" + "\n";

        assertFalse(analyzer.getNodesNum() == 0);

        assertEquals(unusedClassField, result.get(1));
        assertEquals(unusedMethodField, result.get(3));
    }

    @Test
    public void testUVCPass() {
        String directoryPath = "target/test-classes/domain/UnusedVariableTest/UnusedVariablePass";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check UnusedVariableCheck = new UnusedVariableChecker();
        analyzer.addCheck(UnusedVariableCheck);
        ArrayList<String> result = analyzer.runChecks();

        String firstClassPass = "No unused variables found in class: domain/UnusedVariableTest/UnusedVariablePass/NoClassOrLocalFields" + "\n";
        String secondClassPass = "No unused variables found in class: domain/UnusedVariableTest/UnusedVariablePass/UsedClassAndLocalField" + "\n";

        assertFalse(analyzer.getNodesNum() == 0);

        assertEquals(firstClassPass, result.get(1));
        assertEquals(secondClassPass, result.get(3));
    }

}
