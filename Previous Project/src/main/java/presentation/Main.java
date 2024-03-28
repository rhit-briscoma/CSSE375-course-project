package presentation;

import domain.Analyzer;
import datasource.ClassFileReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        frame.displayFrame();
        // initialize();
    }

    private static void initialize(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the project directory path: ");

        String directoryPath = scanner.nextLine();

        Path projectDirectory = Paths.get(directoryPath);
        Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner); 
        analyzer.analyze();
        System.out.println("Results can be found in linter-report.txt");

        scanner.close(); 
    }
}

