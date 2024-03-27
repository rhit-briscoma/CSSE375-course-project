package domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import datasource.ClassFileReader;

import java.util.ArrayList;
import java.util.Scanner;

import java.nio.file.Path;

import java.nio.file.Paths;
import domain.checks.*;
import domain.checks.style_checks.VariableNamingConventions;

public class VariableNamingConventionsCheckTest {

    @Test
    public void testVNCFailure() {
        String directoryPath = "target/test-classes/domain/VariableNamingConventionsTest/VariableNamingConventionsFail";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check variableNamingConventionsCheck = new VariableNamingConventions();
        analyzer.addCheck(variableNamingConventionsCheck);
        ArrayList<String> result = analyzer.runChecks();

        String nonUppercaseConstant = "field is a constant without all uppercase letters" + "\n";
        String uppercaseNonConstant = "VARIABLE starts with a capital letter but should be lower case." + "\n";

        assertFalse(analyzer.getNodesNum() == 0);

        assertEquals(nonUppercaseConstant, result.get(1));
        assertEquals(uppercaseNonConstant, result.get(3));
    }

    @Test
    public void testVNCPass() {
        String directoryPath = "C:/Users/hallamcs/CSSE375Project/CSSE375-course-project/Previous Project/target/test-classes/domain/VariableNamingConventionsTest/VariableNamingConventionsPass";
        Path projectDirectory = Paths.get(directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check variableNamingConventionsCheck = new VariableNamingConventions();
        analyzer.addCheck(variableNamingConventionsCheck);
        ArrayList<String> result = analyzer.runChecks();

        String noIssueString = "No violations to the Variable Naming Conventions";

        assertFalse(analyzer.getNodesNum() == 0);

        assertEquals(noIssueString, result.get(1));
        assertEquals(noIssueString, result.get(3));
    }

}
