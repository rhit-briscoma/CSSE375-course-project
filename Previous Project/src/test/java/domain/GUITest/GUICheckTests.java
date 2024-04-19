package domain.GUITest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import presentation.MyFrame;

import datasource.ClassFileReader;
import domain.Analyzer;
import domain.checks.Check;
import domain.checks.design_pattern_checks.DecoratorPattern;
import domain.checks.design_principle_checks.InformationHiding;
import domain.checks.style_checks.VariableNamingConventions;

public class GUICheckTests {
    @Test
    public void testNoChecks() {

        MyFrame frame = new MyFrame();

        String directoryPath = "target/test-classes/domain/AnalyzerTestClasses/";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory);

        frame.setupAnalyzerWithCorrectChecks(analyzer);

        assertEquals(0, analyzer.getNumberOfChecks());
    }

    @Test
    public void testAllChecks() {

        MyFrame frame = new MyFrame();

        String directoryPath = "target/test-classes/domain/AnalyzerTestClasses/";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory);

        frame.toggleStyleComboBox();
        frame.togglePrincipleComboBox();
        frame.togglePatternComboBox();
        frame.setupAnalyzerWithCorrectChecks(analyzer);

        assertEquals(11, analyzer.getNumberOfChecks());
    }

    @Test
    public void testOnlyStyleChecks() {

        MyFrame frame = new MyFrame();

        String directoryPath = "target/test-classes/domain/AnalyzerTestClasses/";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory);

        frame.toggleStyleComboBox();
        frame.setupAnalyzerWithCorrectChecks(analyzer);

        assertEquals(4, analyzer.getNumberOfChecks());
    }

    @Test
    public void testOnlyPrincipleChecks() {

        MyFrame frame = new MyFrame();

        String directoryPath = "target/test-classes/domain/AnalyzerTestClasses/";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory);

        frame.togglePrincipleComboBox();
        frame.setupAnalyzerWithCorrectChecks(analyzer);

        assertEquals(3, analyzer.getNumberOfChecks());
    }

    @Test
    public void testOnlyPatternChecks() {

        MyFrame frame = new MyFrame();

        String directoryPath = "target/test-classes/domain/AnalyzerTestClasses/";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory);

        frame.togglePatternComboBox();
        frame.setupAnalyzerWithCorrectChecks(analyzer);

        assertEquals(4, analyzer.getNumberOfChecks());
    }

    @Test
    public void testOnlyVNCStyleCheck() {

        MyFrame frame = new MyFrame();

        String directoryPath = "target/test-classes/domain/AnalyzerTestClasses/";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory);
        ArrayList<Check> allChecks = analyzer.getSelectedChecks();
        VariableNamingConventions exampleVNC = new VariableNamingConventions();

        frame.toggleStyleComboBox();
        frame.setStyleComboBoxOption(4);
        frame.setupAnalyzerWithCorrectChecks(analyzer);

        assertEquals(1, analyzer.getNumberOfChecks());
        assertEquals(exampleVNC.getClass(), allChecks.get(0).getClass());
    }

    @Test
    public void testOnlyInformationHidingPrincipleCheck() {

        MyFrame frame = new MyFrame();

        String directoryPath = "target/test-classes/domain/AnalyzerTestClasses/";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory);
        ArrayList<Check> allChecks = analyzer.getSelectedChecks();
        InformationHiding exampleIHC = new InformationHiding();

        frame.togglePrincipleComboBox();
        frame.setPrincipleComboBoxOption(2);
        frame.setupAnalyzerWithCorrectChecks(analyzer);

        assertEquals(1, analyzer.getNumberOfChecks());
        assertEquals(exampleIHC.getClass(), allChecks.get(0).getClass());
    }

    @Test
    public void testOnlyDecoratorPatternCheck() {

        MyFrame frame = new MyFrame();

        String directoryPath = "target/test-classes/domain/AnalyzerTestClasses/";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory);
        ArrayList<Check> allChecks = analyzer.getSelectedChecks();
        DecoratorPattern exampleDPC = new DecoratorPattern();

        frame.togglePatternComboBox();
        frame.setPatternComboBoxOption(1);
        frame.setupAnalyzerWithCorrectChecks(analyzer);

        assertEquals(1, analyzer.getNumberOfChecks());
        assertEquals(exampleDPC.getClass(), allChecks.get(0).getClass());
    }

    @Test
    public void testVNCandPatternChecks() {

        MyFrame frame = new MyFrame();

        String directoryPath = "target/test-classes/domain/AnalyzerTestClasses/";
        Path projectDirectory = Paths.get(System.getProperty("user.dir"), directoryPath);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory);
        ArrayList<Check> allChecks = analyzer.getSelectedChecks();
        VariableNamingConventions exampleVNC = new VariableNamingConventions();

        frame.toggleStyleComboBox();
        frame.setStyleComboBoxOption(4);
        frame.togglePatternComboBox();
        frame.setupAnalyzerWithCorrectChecks(analyzer);

        assertEquals(5, analyzer.getNumberOfChecks());
        assertEquals(exampleVNC.getClass(), allChecks.get(0).getClass());
    }
}
