package domain.checks.style_checks;

import datasource.GetWordsFromFile;
import datasource.WordLoader;
import domain.MyClassNode;
import java.util.Set;

public class CheckClassName extends StyleCheck {

    public CheckClassName() {
    }

    private boolean isPascalCase(String className) {
        WordLoader checker = new GetWordsFromFile();
        Set<String> words = checker.getWords();
        String[] parts = className.split("/");
        String name = parts[parts.length - 1];
        String[] classWords = name.split("(?<!^)(?=[A-Z])");
        for (String word : classWords) {
            if (!words.contains(word)) {
                return false;
            }
        }
        return true;
    }

    public boolean result(String className) {
        return isPascalCase(className);
    }

    @Override
    public String performCheck(MyClassNode node) {
        this.node = node;
        if (!result(this.node.name())) {
            return "The class name is not in Pascal Case. Please refactor " + this.node.getClass().getName()
                    + " so that it meets Pascal Case standards.\n";
        } else
        return "No issues with " + this.node.name() + "'s class name.\n";
    }

}
