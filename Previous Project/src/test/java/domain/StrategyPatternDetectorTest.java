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
import domain.checks.design_pattern_checks.StrategyPattern;

public class StrategyPatternDetectorTest {
    
    @Test
    public void testSPDConcreteImpl() {
        String directoryPath = "target/test-classes/domain/StrategyPatternTest/ConcreteStrategyImpl";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check spd = new StrategyPattern();
        analyzer.addCheck(spd);
        ArrayList<String> result = analyzer.runChecks();

        String noStrat = "No strategy pattern detected in class: domain/StrategyPatternTest/ConcreteStrategyImpl/NewStrat";
        String concreteImpl = "Potential strategy interface found: domain/StrategyPatternTest/ConcreteStrategyImpl/NewStrat\n" +
            "Concrete strategy implementation detected: domain/StrategyPatternTest/ConcreteStrategyImpl/NewStratImpl";

        assertFalse(analyzer.getNodesNum() == 0);

        assertEquals(noStrat, result.get(1));
        assertEquals(concreteImpl, result.get(3));
    }

    @Test
    public void testSPDLikelyContext() {
        String directoryPath = "target/test-classes/domain/StrategyPatternTest/LikelyContext";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check spd = new StrategyPattern();
        analyzer.addCheck(spd);
        ArrayList<String> result = analyzer.runChecks();

        String noStrat = "No strategy pattern detected in class: domain/StrategyPatternTest/LikelyContext/InterfaceStrat";
        String likelyContext = "Field with strategy interface type detected: Ldomain/StrategyPatternTest/LikelyContext/InterfaceStrat;\n" +
            "Method potentially delegating to a strategy: useStrategy\n" + 
            "Class likely represents a context in the Strategy Pattern: domain/StrategyPatternTest/LikelyContext/HasStrategyField";

        assertFalse(analyzer.getNodesNum() == 0);

        assertEquals(likelyContext, result.get(1));
        assertEquals(noStrat, result.get(3));
    }

    @Test
    public void testSPDNoStrategyPattern() {
        String directoryPath = "target/test-classes/domain/StrategyPatternTest/NoStrategyPattern";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Scanner scanner = new Scanner(System.in);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner);
        Check spd = new StrategyPattern();
        analyzer.addCheck(spd);
        ArrayList<String> result = analyzer.runChecks();

        String noStrat = "No strategy pattern detected in class: domain/StrategyPatternTest/NoStrategyPattern/NoStrategy";
        
        assertFalse(analyzer.getNodesNum() == 0);

        assertEquals(noStrat, result.get(1));
    }
}
