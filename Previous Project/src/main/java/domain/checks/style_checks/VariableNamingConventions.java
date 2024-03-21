package domain.checks.style_checks;

import org.objectweb.asm.Opcodes;

import domain.MyClassNode;
import domain.MyFieldNode;

public class VariableNamingConventions extends StyleCheck {
    // This class will check the fields of a classnode to check that the field names
    // follow the Java naming conventions. The naming conventions include:
    // Variables begin with lower case letters, variables should not be named after
    // keywords/reserved words, variables cannot start with a digit, constants must
    // be all uppercase

    // ArrayList<String> reservedNames;

    private StringBuilder sb;

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
                // System.out.println(field.name());
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

    private void checkForFinal(MyFieldNode field, String fieldName){
        if ((field.access() & Opcodes.ACC_FINAL) != 0) {
            if (fieldName.equals(fieldName.toUpperCase()) == false) {
                this.sb.append(fieldName);
                this.sb.append(" is a constant without all uppercase letters" + "\n");
            }
        }
    }

    private void checkForPublic(MyFieldNode field, String fieldName){
        if ((field.access() & Opcodes.ACC_PUBLIC) != 0) {
            String[] fieldNameArr = field.name().split("(?<!^)(?=[A-Z])");
            if(Character.isAlphabetic(fieldNameArr[0].charAt(0)) == false){
                this.sb.append(field.name()); 
                this.sb.append("does not start with a letter" + "\n");
            }
        }
    }

    private void checkCase(String fieldName, String[] fieldNameArr){
                if(Character.isUpperCase(fieldNameArr[0].charAt(0))){
                    this.sb.append(fieldName); 
                    this.sb.append(" starts with a capital letter but should be lower case." + "\n");
                }    
    }

    @Override
    public String performCheck(MyClassNode node) {
        this.node = node;
        sb = new StringBuilder();
        for (MyFieldNode field : this.node.fields()) {
            String fieldName = field.name();
            if ((field.access() & Opcodes.ACC_FINAL) != 0) {
            checkForFinal(field, fieldName);
            }
            else {
                checkForPublic(field, fieldName);
                checkCase(fieldName, field.name().split("(?<!^)(?=[A-Z])"));
            }
            if(sb.isEmpty()) {
                sb.append("No violations to the Variable Naming Conventions");
            }
        }
        return this.sb.toString();
    }
}