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
import domain.checks.design_principle_checks.CheckDuplicateCode;

public class DuplicateCodeCheckTest {
    
    @Test
    public void testDCCFail() {
        String directoryPath = "target/test-classes/domain/DuplicateCodeTest/DuplicateCodeFail";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check dcc = new CheckDuplicateCode();
        analyzer.addCheck(dcc);
        ArrayList<String> result = analyzer.runChecks();

        String duplicateCode = "Class domain/DuplicateCodeTest/DuplicateCodeFail/" + 
        "DuplicateCodeExampleTrue has duplicate code. " +
        "Please refactor to remove the duplicate code";

        assertFalse(analyzer.getNodesNum() == 0);

        assertEquals(duplicateCode, result.get(1));
    }

    @Test
    public void testDCCPass() {
        String directoryPath = "target/test-classes/domain/DuplicateCodeTest/DuplicateCodePass";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check dcc = new CheckDuplicateCode();
        analyzer.addCheck(dcc);
        ArrayList<String> result = analyzer.runChecks();

        String duplicateCode = "Class domain/DuplicateCodeTest/DuplicateCodePass/" + 
        "DuplicateCodeExampleFalse does not have duplicate code.";

        assertFalse(analyzer.getNodesNum() == 0);

        assertEquals(duplicateCode, result.get(1));
    }

}
