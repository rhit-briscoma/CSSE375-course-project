package presentation;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import datasource.ClassFileReader;
import domain.Analyzer;
import domain.checks.Check;
import domain.checks.design_pattern_checks.DecoratorPattern;
import domain.checks.design_pattern_checks.FacadePattern;
import domain.checks.design_pattern_checks.ObserverPattern;
import domain.checks.design_pattern_checks.StrategyPattern;
import domain.checks.design_principle_checks.CheckDuplicateCode;
import domain.checks.design_principle_checks.InformationHiding;
import domain.checks.design_principle_checks.SingleResponsibilityPrinciple;
import domain.checks.style_checks.CheckClassName;
import domain.checks.style_checks.MethodStyleCheck;
import domain.checks.style_checks.UnusedVariableChecker;
import domain.checks.style_checks.VariableNamingConventions;
import javafx.scene.layout.Border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MyFrame extends JFrame {

    public static final int FRAME_WIDTH = 1280;
    public static final int FRAME_HEIGHT = 800;

    public static final int STYLISH_WIDTH = 225;
    public static final int STYLISH_HEIGHT = 25;
    Dimension minimum = new Dimension(STYLISH_WIDTH, STYLISH_HEIGHT);
    private JFrame myFrame;
    private JPanel north, east, south, west, center;
    private JButton run, quit, explorerButton;
    private JCheckBox patternChecksBox, principleChecksBox, styleChecksBox;
    private JComboBox<String> patternOptions, principleOptions, styleOptions;
    private JFileChooser fc;
    private JTextField tf;
    private int result;
    private String path, tfPathString;
    private Path manualPath1, manualPath2;

    public MyFrame() {
        myFrame = new JFrame("Java Linter");
        myFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        myFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout g = new BorderLayout();
        myFrame.setLayout(g);
        buildComponents();
    }

    private void buildComponents() {
        buildPanels();
        buildLabels();
        buildInteractive();
        buildButtons();

    }

    private void buildPanels() {
        // panels for GridLayout
        north = new JPanel();
        north.setBackground(Color.RED);
        east = new JPanel();
        south = new JPanel();
        south.setBackground(Color.GREEN);
        west = new JPanel();
        center = new JPanel();
        center.setBackground(Color.cyan);
        center.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 25));
        myFrame.add(north, BorderLayout.NORTH);
        myFrame.add(east, BorderLayout.EAST);
        myFrame.add(south, BorderLayout.SOUTH);
        myFrame.add(west, BorderLayout.WEST);
        myFrame.add(center, BorderLayout.CENTER);
    }

    private void buildLabels() {
        // labels
        JLabel title = new JLabel("Java Linter");
        title.setPreferredSize(new Dimension(100, 50));
        north.add(title);

        JLabel desc1 = new JLabel("This program will read in Java .class files and run various checks on code.\n test"
                + "Please select a directory via the file explorer or enter a file path for a project direcory.");
        desc1.setPreferredSize(new Dimension(1250, 25));

        JLabel desc2 = new JLabel(
                "Please enter or select a Java project directory to run through the linter program and select the checks you would like to run.");
        desc2.setPreferredSize(new Dimension(1250, 25));
        // center.add(desc);
        center.add(desc1);
        center.add(desc2);

    }

    private void buildButtons() {
        // buttons
        run = new JButton("Run Linter");
        run.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ManualPath1 " + manualPath1);
                if (manualPath1 != null) {
                    System.out.println("In manualPath1");
                    if (checkDirectory(manualPath1)) {
                        // do work like console version
                        JOptionPane.showMessageDialog(null, "Directory selected, program starting.");
                        startLinter(manualPath1);
                        System.out.println("SUCCESS!\n");
                        return;
                    }
                }
                tfPathString = tf.getText();
                System.out.println("tfPathString is" + tfPathString);
                if (tfPathString != null) {
                    manualPath2 = Paths.get(tfPathString);
                    System.out.println(tfPathString);
                    System.out.println(manualPath2);
                    if (checkDirectory(manualPath2)) {
                        // do work like console version
                        JOptionPane.showMessageDialog(null, "Directory selected, program starting.");
                        startLinter(manualPath2);
                        System.out.println("SUCCESS!\n");
                    }
                } else JOptionPane.showMessageDialog(null, "Please provide an appropriate directory.");
            }

        });
        quit = new JButton("Quit Program");
        quit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });
        styleChecksBox = new JCheckBox("Enable/Disable Style Checks");
        styleChecksBox.setPreferredSize(minimum);
        styleChecksBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                styleOptions.setEnabled(!styleOptions.isEnabled());
            }
        });

        principleChecksBox = new JCheckBox("Enable/Disable Principle Checks");
        principleChecksBox.setPreferredSize(minimum);
        principleChecksBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                principleOptions.setEnabled(!principleOptions.isEnabled());
            }
        });

        patternChecksBox = new JCheckBox("Enable/Disable Pattern Checks");
        patternChecksBox.setPreferredSize(minimum);
        patternChecksBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                patternOptions.setEnabled(!patternOptions.isEnabled());
            }
        });

        south.add(run);
        south.add(quit);
        center.add(styleChecksBox);
        center.add(principleChecksBox);
        center.add(patternChecksBox);
    }

    private void buildInteractive() {
        fc = new JFileChooser(new File("."));
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // use later
        // result = fc.showOpenDialog(null);
        explorerButton = new JButton("Open File Explorer");
        explorerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                result = fc.showOpenDialog(null);
                System.out.println("Opened JFileChooser");
                if (result == JFileChooser.APPROVE_OPTION) {
                    path = fc.getSelectedFile().getAbsolutePath();
                    manualPath1 = Paths.get(path);
                    System.out.println(tfPathString);
                } else if(result == JFileChooser.CANCEL_OPTION) {
                    return;  
                } else JOptionPane.showMessageDialog(null, "Error: project directory is not valid.");
            }

        });
        tf = new JTextField("Enter project direcotry path here.", 60);
        tf.setHorizontalAlignment((int) LEFT_ALIGNMENT);

        String[] patternNames = {"All", "Decorator", "Facade", "Observer", "Strategy"};
        String[] principleNames = {"All", "Code Duplication", "Information Hiding", "Single Responsibility Principle"};
        String[] styleNames = {"All", "Proper Class Names", "Proper Method Names", "Check for Unused Variables", "Proper Variables Names"};

        styleOptions = new JComboBox<>(styleNames);
        styleOptions.setPreferredSize(minimum);
        styleOptions.setEnabled(false);

        principleOptions = new JComboBox<>(principleNames);
        principleOptions.setPreferredSize(minimum);
        principleOptions.setEnabled(false);

        patternOptions = new JComboBox<>(patternNames);
        patternOptions.setPreferredSize(minimum);
        patternOptions.setEnabled(false);

        center.add(tf);
        center.add(explorerButton);
        center.add(styleOptions);
        center.add(principleOptions);
        center.add(patternOptions);
    }

    private boolean checkDirectory(Path projectPath) {
        if (Files.isDirectory(projectPath)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "File Path does not point to a directory.");
        }
        return false;
    }

    public void displayFrame() {
        myFrame.setVisible(true);
    }

    public void hideFrame() {
        myFrame.setVisible(false);
    }

    public void startLinter(Path path){
        Analyzer analyzer = new Analyzer(new ClassFileReader(), path);
        // analyzer.analyzeGUI();

        if(styleOptions.isEnabled()) {
            ArrayList<Check> styleChecks = new ArrayList<>();
            styleChecks.add(new CheckClassName());
            styleChecks.add(new MethodStyleCheck());
            styleChecks.add(new UnusedVariableChecker());
            styleChecks.add(new VariableNamingConventions());

            int currentOption = styleOptions.getSelectedIndex();
            if(currentOption == 0) {
                analyzer.addAllChecks(styleChecks);
            } else {
                analyzer.addCheck(styleChecks.get(currentOption - 1));
            }
        }

        if(principleOptions.isEnabled()) {
            ArrayList<Check> principleChecks = new ArrayList<>();
            principleChecks.add(new CheckDuplicateCode());
            principleChecks.add(new InformationHiding());
            principleChecks.add(new SingleResponsibilityPrinciple());

            int currentOption = principleOptions.getSelectedIndex();
            if(currentOption == 0) {
                analyzer.addAllChecks(principleChecks);
            } else {
                analyzer.addCheck(principleChecks.get(currentOption--));
            }
        }

        if(patternOptions.isEnabled()) {
            ArrayList<Check> patternChecks = new ArrayList<>();
            patternChecks.add(new DecoratorPattern());
            patternChecks.add(new FacadePattern());
            patternChecks.add(new ObserverPattern());
            patternChecks.add(new StrategyPattern());

            int currentOption = patternOptions.getSelectedIndex();
            if(currentOption == 0) {
                analyzer.addAllChecks(patternChecks);
            } else {
                analyzer.addCheck(patternChecks.get(currentOption--));
            }
        }
        analyzer.analyzeGUI();
        // Main.startLinter(path);
        JOptionPane.showMessageDialog(null, "The linter has finished running. Results can be found in " + path + "\\" + "linter-report.txt");
    }

}
