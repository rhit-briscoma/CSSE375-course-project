package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import datasource.ClassFileReader;
import domain.InformationHidingTest.InformationHidingFail.AllFieldsAndMethodsPublic;
import domain.checks.Check;
import domain.checks.design_principle_checks.InformationHiding;
import domain.checks.design_principle_checks.SingleResponsibilityPrinciple;

public class InformationHidingCheckTest {
    
    @Test
    public void testIHCFailure() {
        String directoryPath = "target/test-classes/domain/InformationHidingTest/InformationHidingFail";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check ihc = new InformationHiding();
        analyzer.addCheck(ihc);
        ArrayList<String> result = analyzer.runChecks();

        String allPublic = "Checking on domain/InformationHidingTest/InformationHidingFail/AllFieldsAndMethodsPublic\n" + 
            "Information Hiding Report for domain/InformationHidingTest/InformationHidingFail/AllFieldsAndMethodsPublic :\n" +
            "A siginicant number of the fields are public. Please determine if some of these can be changed to a more private access modifier.\n" + 
            "A siginicant number of the methods are public. Please determine if some of these can be changed to a more private access modifier.\n" +
            "Finish Information Hiding Report fordomain/InformationHidingTest/InformationHidingFail/AllFieldsAndMethodsPublic\n";

        String fourtyPercentPublic = "Checking on domain/InformationHidingTest/InformationHidingFail/FourtyPercentFieldsAndMethodsPublic\n" + 
            "Information Hiding Report for domain/InformationHidingTest/InformationHidingFail/FourtyPercentFieldsAndMethodsPublic :\n" +
            "Some fields are public. Please determine if some of these can be changed to a more private access modifier.\n" + 
            "Some methods are public. Please determine if some of these can be changed to a more private access modifier.\n" + 
            "Finish Information Hiding Report fordomain/InformationHidingTest/InformationHidingFail/FourtyPercentFieldsAndMethodsPublic\n";

        String sixtyPercentPublic = "Checking on domain/InformationHidingTest/InformationHidingFail/SixtyPercentFieldsAndMethodsPublic\n" + 
            "Information Hiding Report for domain/InformationHidingTest/InformationHidingFail/SixtyPercentFieldsAndMethodsPublic :\n" +
            "The majority of fields are public. Please determine if some of these can be changed to a more private access modifier.\n" + 
            "The majority of methods are public. Please determine if some of these can be changed to a more private access modifier.\n" + 
            "Finish Information Hiding Report fordomain/InformationHidingTest/InformationHidingFail/SixtyPercentFieldsAndMethodsPublic\n";

        assertFalse(analyzer.getNodesNum() == 0);

        assertEquals(allPublic, result.get(1));
        // assertEquals(fourtyPercentPublic, result.get(3));
        assertEquals(sixtyPercentPublic, result.get(5));
    }

    @Test
    public void testIHCPass() {
        String directoryPath = "target/test-classes/domain/InformationHidingTest/InformationHidingPass";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check ihc = new InformationHiding();
        analyzer.addCheck(ihc);
        ArrayList<String> result = analyzer.runChecks();

        String notEnoughFieldsOrMethods = "Checking on domain/InformationHidingTest/InformationHidingPass/BelowMinimumNumberOfFieldsAndMethods\n" + 
            "Information Hiding Report for domain/InformationHidingTest/InformationHidingPass/BelowMinimumNumberOfFieldsAndMethods :\n" +
            "Not enough fields to accurately determine proper information hiding\n" + 
            "Not enough methods to accurately determine proper information hiding\n" +
            "Finish Information Hiding Report fordomain/InformationHidingTest/InformationHidingPass/BelowMinimumNumberOfFieldsAndMethods\n";

        String noPublic = "Checking on domain/InformationHidingTest/InformationHidingPass/NoPublicFieldsAndMethods\n" + 
            "Information Hiding Report for domain/InformationHidingTest/InformationHidingPass/NoPublicFieldsAndMethods :\n" +
            "The majority of fields are not public. Please continue to keep hiding information.\n" + 
            "The majority of methods are not public. Please continue to keep hiding information.\n" + 
            "Finish Information Hiding Report fordomain/InformationHidingTest/InformationHidingPass/NoPublicFieldsAndMethods\n";

        assertFalse(analyzer.getNodesNum() == 0);

        assertEquals(notEnoughFieldsOrMethods, result.get(1));
        assertEquals(noPublic, result.get(3));
    }
    
}
