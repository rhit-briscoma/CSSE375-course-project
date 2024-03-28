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
import domain.checks.design_pattern_checks.FacadePattern;

public class FacadePatternDetectorTest {
    
    @Test
    public void testFPDFail() {
        String directoryPath = "target/test-classes/domain/FacadePatternTest/FacadePatternFail";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check fpd = new FacadePattern();
        analyzer.addCheck(fpd);
        ArrayList<String> result = analyzer.runChecks();

        String noFacade = "Checking for Facade Pattern in class: domain/FacadePatternTest/FacadePatternFail/UnderThreeDelegations\n" +
            "The class likely does not implement the Facade Pattern based on method delegation.";
        assertFalse(analyzer.getNodesNum() == 0);

        assertEquals(noFacade, result.get(1));
    }

    @Test
    public void testFPDPass() {
        String directoryPath = "target/test-classes/domain/FacadePatternTest/FacadePatternPass";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check fpd = new FacadePattern();
        analyzer.addCheck(fpd);
        ArrayList<String> result = analyzer.runChecks();

        String likelyFacade = "Checking for Facade Pattern in class: domain/FacadePatternTest/FacadePatternPass/OverThreeDelegations\n" +
            "The class likely implements the Facade Pattern based on method delegation.";

        assertFalse(analyzer.getNodesNum() == 0);

        assertEquals(likelyFacade, result.get(1));
    }
}
