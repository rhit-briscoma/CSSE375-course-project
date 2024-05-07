package presentation;

import java.util.List;

// Inspired by https://stackoverflow.com/questions/18292659/scrollbar-in-jtextarea
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class ResultDisplayBox {

    JFrame frame = new JFrame ("Linter Results");


    public ResultDisplayBox(List<String> analysisResults) {
        // frame settings
        frame.setSize(800,800);
        frame.setResizable(true);
        
        // text settings
        JTextArea textArea = new JTextArea();
        for (String string : analysisResults) {
            textArea.append(string);
        }
        textArea.setSize(800,800);    
        
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setVisible(true);
        
        JScrollPane scroll = new JScrollPane (textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        frame.add(scroll);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void display() {
        frame.setVisible(true);
    }
}
