package domain;

import datasource.ClassFileReader;
import datasource.IClassFileReader;
import domain.checks.Check;
import domain.checks.design_pattern_checks.DecoratorPattern;
import domain.checks.design_pattern_checks.FacadePattern;
import domain.checks.design_pattern_checks.ObserverPattern;
import domain.checks.design_pattern_checks.StrategyPattern;
import domain.checks.design_principle_checks.CheckDuplicateCode;
import domain.checks.design_principle_checks.InformationHiding;
import domain.checks.design_principle_checks.ProgramToInterface;
import domain.checks.design_principle_checks.SingleResponsibilityPrinciple;
import domain.checks.style_checks.CheckClassName;
import domain.checks.style_checks.MethodStyleCheck;
import domain.checks.style_checks.UnusedVariableChecker;
import domain.checks.style_checks.VariableNamingConventions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Analyzer {
    private IClassFileReader classFileReader;
    private ArrayList<Check> checks;
    private ArrayList<Check> selectedChecks;
    private Path projectDirectory;
    private Scanner scanner;

    private ArrayList<MyClassNode> nodes = new ArrayList<>();

    public Analyzer(IClassFileReader classFileReader, Path projectDirectory, Scanner scanner) {
        this.classFileReader = classFileReader;
        this.projectDirectory = projectDirectory;
        this.scanner = scanner;
        this.checks = new ArrayList<>();
        this.selectedChecks = new ArrayList<>();
        readFiles(projectDirectory);
        populateCheckObjects();
    }

    public Analyzer(ClassFileReader classFileReader, Path projectDirectory) {
        this.classFileReader = classFileReader;
        this.projectDirectory = projectDirectory;
        this.checks = new ArrayList<>();
        this.selectedChecks = new ArrayList<>();
        readFiles(this.projectDirectory);
        populateCheckObjects();
    }

    private void populateCheckObjects(){
        // style checks
        this.checks.add(new MethodStyleCheck());
        this.checks.add(new UnusedVariableChecker());
        this.checks.add(new CheckClassName());
        this.checks.add(new VariableNamingConventions());


        // principle checks
        this.checks.add(new InformationHiding());
        this.checks.add(new SingleResponsibilityPrinciple());
        this.checks.add(new CheckDuplicateCode());
        // this.checks.add(new ProgramToInterface());

        // pattern checks
        this.checks.add(new FacadePattern());
        this.checks.add(new ObserverPattern());
        this.checks.add(new StrategyPattern());
        this.checks.add(new DecoratorPattern());
    }

    public void readFiles(Path projectDirectory) {
        // Ensure the path is a directory
        if (!Files.isDirectory(projectDirectory)) {
            System.err.println("The provided path is not a directory: " + projectDirectory);
            return;
        }

        // Walk through the directory to find class files and analyze them
        try (Stream<Path> paths = Files.walk(projectDirectory)) {
            paths.filter(Files::isRegularFile)
                 .filter(path -> path.toString().endsWith(".class"))
                 .forEach(this::addToList);
        } catch (IOException e) {
            System.err.println("Error walking through the project directory: " + projectDirectory);
            e.printStackTrace();
        }

    }

    private void addToList(Path classFilePath){
        MyClassNode classNode = classFileReader.readClassFile(classFilePath);
        this.nodes.add(classNode);
        // System.out.println(classFilePath.toString());
    }


    public void analyze() {
        // ArrayList<String> analysisResults = new ArrayList<>();
        // ArrayList<Check> selectedChecks = new ArrayList<>();
    
        // Ask if all checks should be run
        System.out.println("Do you want to run all checks? (YES/no)");
        String allChecksResponse = scanner.nextLine().trim().toLowerCase();
        boolean runAllChecks = allChecksResponse.equals("yes") || allChecksResponse.isEmpty();
    
        if (runAllChecks) {
            selectedChecks.addAll(this.checks);
        } else {
            // Determine which individual checks to run
            for (Check check : this.checks) {
                System.out.println("Do you want to run " + check.getClass().getSimpleName() + "? (YES/no)");
                String response = scanner.nextLine().trim().toLowerCase();
                if (response.equals("yes") || response.isEmpty()) { // YES or Enter selects the check
                    selectedChecks.add(check);
                }
            }
        }
    
        // // Run selected checks on all classes
        // for (MyClassNode node : this.nodes) {
        //     analysisResults.add("--------Performing Checks for " + node.name() + "--------\n");
        //     for (Check check : selectedChecks) {
        //         analysisResults.add(check.performCheck(node));
        //         // analysisResults.add(check.performCheck(node));
        //     }
        // }
        ArrayList<String> analysisResults = runChecks();
    
        ReportGenerator reportGenerator = new ReportGenerator();
        Path reportPath = this.projectDirectory.resolve("linter-report.txt");
        reportGenerator.generateTextReport(analysisResults, reportPath);
        this.checks.clear();
        selectedChecks.clear();
    }

    public void analyzeGUI(){
        // selectedChecks.addAll(this.checks);
        ArrayList<String> analysisResults = runChecks();
    
        ReportGenerator reportGenerator = new ReportGenerator();
        Path reportPath = this.projectDirectory.resolve("linter-report.txt");
        reportGenerator.generateTextReport(analysisResults, reportPath);
        this.checks.clear();
        selectedChecks.clear();
    }

    public ArrayList<String> runChecks() {
        // Run selected checks on all classes
        ArrayList<String> analysisResults = new ArrayList<>();
        for (MyClassNode node : this.nodes) {
            analysisResults.add("--------Performing Checks for " + node.name() + "--------\n");
            for (Check check : selectedChecks) {
                analysisResults.add(check.performCheck(node));
                // analysisResults.add(check.performCheck(node));
            }
        }
        return analysisResults;
    }

    public void addCheck(Check check) {
        selectedChecks.add(check);
    }

    public void addAllChecks(List<Check> checks) {
        selectedChecks.addAll(checks);
    }

    public int getNodesNum() {
        return nodes.size();
    }
    
    }

