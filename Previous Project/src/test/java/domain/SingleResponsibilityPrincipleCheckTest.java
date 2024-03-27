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
import domain.checks.design_principle_checks.SingleResponsibilityPrinciple;

public class SingleResponsibilityPrincipleCheckTest {
    
    @Test
    public void testSRPFailure() {
        String directoryPath = "target/test-classes/domain/SingleResponsibilityPrincipleTest/SingleResponsibilityPrincipleFail";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check srp = new SingleResponsibilityPrinciple();
        analyzer.addCheck(srp);
        ArrayList<String> result = analyzer.runChecks();

        // String highComplexity = "Warning: Class methods have high average complexity (5), which might violate SRP.";
        String highCoupling = "Checking Single Responsibility Principle for class: domain/SingleResponsibilityPrincipleTest/SingleResponsibilityPrincipleFail/HighMethodCoupling\n" +
                        "Warning: High coupling between class methods (10 interactions), which might violate SRP.\n";
        String manyFields = "Checking Single Responsibility Principle for class: domain/SingleResponsibilityPrincipleTest/SingleResponsibilityPrincipleFail/ManyFields\n" + 
                        "Warning: Class has many fields (6), which might violate SRP.\n";

        assertFalse(analyzer.getNodesNum() == 0);

        // assertEquals(highComplexity, result.get(1));
        assertEquals(highCoupling, result.get(3));
        assertEquals(manyFields, result.get(5));
    }

    @Test
    public void testSRPPass() {
        String directoryPath = "target/test-classes/domain/SingleResponsibilityPrincipleTest/SingleResponsibilityPrinciplePass";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check srp = new SingleResponsibilityPrinciple();
        analyzer.addCheck(srp);
        ArrayList<String> result = analyzer.runChecks();

        String srpPass = "Checking Single Responsibility Principle for class: domain/SingleResponsibilityPrincipleTest/SingleResponsibilityPrinciplePass/SingleResponsibilityClass\n" +
                        "Based on the heuristic, the class likely follows the Single Responsibility Principle.\n";

        assertFalse(analyzer.getNodesNum() == 0);

        assertEquals(srpPass, result.get(1));
    }
}
