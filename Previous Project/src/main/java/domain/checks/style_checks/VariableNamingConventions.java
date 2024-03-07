package domain.checks.style_checks;

import java.util.ArrayList;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import domain.MyClassNode;
import domain.MyFieldNode;

public class VariableNamingConventions extends StyleCheck {
    // This class will check the fields of a classnode to check that the field names
    // follow the Java naming conventions. The naming conventions include:
    // Variables begin with lower case letters, variables should not be named after
    // keywords/reserved words, variables cannot start with a digit, constants must
    // be all uppercase

    // ArrayList<String> reservedNames;

    public VariableNamingConventions() {
    }

    
    private String performWork() {
        String result = "";
        for (MyFieldNode field : this.node.fields()) {
            if ((field.access() & Opcodes.ACC_FINAL) != 0) {
                if (field.name().equals(field.name().toUpperCase()) == false) {
                    result += field.name() + " is a constant without all uppercase letters" + "\n";
                }
            } else if ((field.access() & Opcodes.ACC_PUBLIC) != 0) {
                System.out.println(field.name());
                String[] fieldName = field.name().split("(?<!^)(?=[A-Z])");
                if(Character.isAlphabetic(fieldName[0].charAt(0)) == false){
                    result += field.name() + "does not start with a letter" + "\n";
                }
  
               for(int i = 0; i < fieldName.length; i++){
                if(i == 0){
                    if(Character.isLowerCase(fieldName[0].charAt(0)) == false){
                        result += field.name() + " does not start with a lower case letter" + "\n";
                       
                    }
                }
                else{
                    if(Character.isLowerCase(fieldName[i].charAt(0)) == true){
                        result += field.name() + " starts with a lower case letter" + "\n";
                    }

                }
               }   
            }
        }
        if(result.equals("")){
            return "No violations to the Variable Naming Conventions";
        }
        return result;
    }

    @Override
    public String performCheck(MyClassNode node) {
        this.node = node;
        return performWork();
    }
}