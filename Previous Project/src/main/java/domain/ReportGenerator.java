package domain;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
public class ReportGenerator {

    public ReportGenerator(){}

    public void generateTextReport(List<String> analysisResults, Path outputPath) {
        StringBuilder textContent = new StringBuilder();
        textContent.append("Linter Analysis Results\n");
        textContent.append("=======================\n\n");
        for (String result : analysisResults) {
            textContent.append(result).append("\n\n");
        }
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardOpenOption.TRUNCATE_EXISTING)) {
            // writer.flush();
            // writer.write(textContent.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter bw = Files.newBufferedWriter(outputPath, StandardOpenOption.CREATE)){
            bw.write(textContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
