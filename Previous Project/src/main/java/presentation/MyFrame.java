package presentation;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import domain.Analyzer;
import javafx.scene.layout.Border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyFrame extends JFrame {

    public static final int FRAME_WIDTH = 1280;
    public static final int FRAME_HEIGHT = 800;

    private JFrame myFrame;
    private JPanel north, east, south, west, center;
    private JButton run, quit, explorerButton;
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
        buildButtons();
        buildInteractive();

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
        center.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 25));
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
        south.add(run);
        south.add(quit);
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

        center.add(tf);
        center.add(explorerButton);
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
        Main.startLinter(path);
        JOptionPane.showMessageDialog(null, "The linter has finished running. Results can be found in linter-report.txt");
    }

}
