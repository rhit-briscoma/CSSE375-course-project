package presentation;

// import domain.Analyzer;
// import datasource.ClassFileReader;

// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        frame.displayFrame();
        // initialize();
    }

    // Deprecated methods from when user input was handled through the console.
    // Can be unburied if need be to recreation console UI.

    // private static void initialize(){
    //     Scanner scanner = new Scanner(System.in);
    //     System.out.println("Enter the project directory path: ");

    //     String directoryPath = scanner.nextLine();

    //     Path projectDirectory = Paths.get(directoryPath);
    //     Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory, scanner); 
    //     analyzer.analyze();
    //     System.out.println("Results can be found in linter-report.txt");

    //     scanner.close(); 
    // }

    // public static void startLinter(Path projectDirectory){
    //     // Analyzer analyzer = new Analyzer(new ClassFileReader(), projectDirectory);
    //     // analyzer.analyzeGUI();
    // }
}

