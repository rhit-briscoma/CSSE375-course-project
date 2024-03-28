package domain.checks.style_checks;

import java.util.ArrayList;
import java.util.HashMap;

import domain.MyClassNode;
import domain.MyMethodNode;

public class MethodStyleCheck extends StyleCheck {

    private ArrayList<String> methodNames = new ArrayList<>();
    private StringBuilder sb;

    public MethodStyleCheck() {
    }

    // populateMethodName() will take in a MyClassNode and parse through the MethodNode to retrieve 
    // all of the method names. It also has a case to not use the contructor for the method name checks..
    private void populateMethodNames() {
        for (int i = 0; i < this.node.methods().size(); i++) {
            if(isInitMethod(this.node.methods().get(i))) continue;
            methodNames.add(node.methods().get(i).name());
            // System.out.println(methodNames.get(i));
        }
    }

    // This is where most of the heavy lifting is done. An ArrayList and HashMap are used to help
    // keep track and store comparisions of method names. Confusing method names are found and are
    // recorded in a StringBuilder to be displayed to the user in the report.
    private void confusingMethodNames() {
        HashMap<String, String> unique = new HashMap<String, String>();
        ArrayList<String> errorNames = new ArrayList<String>();
        // StringBuilder sb = new StringBuilder("--------Testing Method Names--------\n");

        // print("--------Testing Method Names--------\n");

        for (String method : methodNames) {
            String temp = method.toLowerCase();
            if (!unique.containsKey(temp)) {
                unique.put(temp, method);
                continue;
            } else {
                errorNames.add(method);
            }
        }

        if (errorNames.isEmpty()) {
            sb.append("No confusing method names.\n");
            // System.out.println("No confusing method names.\n");
        } else {
            for (String name : errorNames) {
                sb.append("Confusing method name found. " + name + " differs from ");
                sb.append(unique.get(name.toLowerCase()) + " only by capitalization" + "\n");
                // print(sb.toString());
            }
        }
    }
    // this method checks if a given method is the constructor
    private boolean isInitMethod(MyMethodNode method) {
    return "<init>".equals(method.name());
}
    // abstract method that is used to call functionality withinn the class.
    @Override
    public String performCheck(MyClassNode node) {
        sb = new StringBuilder("--------Testing Method Names--------\n");
        this.node = node;
        populateMethodNames();
        confusingMethodNames();
        return this.sb.toString();

    }

    
}
