package domain.checks.design_principle_checks;

import java.util.ArrayList;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import domain.MyClassNode;
import domain.MyFieldNode;
import domain.MyMethodNode;

public class InformationHiding extends PrincipleCheck {

    private MyClassNode currentNode = null;
    private int publicFields = 0;
    private int privateFields = 0;
    private int protectedFields = 0;

    private int publicMethods = 0;
    private int privateMethods = 0;
    private int protectedMethods = 0;
    
    public InformationHiding(){
    }

    // abstract method that is used to call functionality withinn the class.
    @Override
    public String performCheck(MyClassNode node) {
        this.node = node;
        return checkModifiers();
    }

    // checkModifiers() parses through the fields and methods of a MyClassNode. These are sent to
    // another method that will check their access modifier
    private String checkModifiers(){
        this.currentNode = this.node;
        StringBuilder sb = new StringBuilder("Checking on " + this.currentNode.name());
        // print("Checking on " + this.node.name);
        int numFields = 0;
            for (MyFieldNode field : this.node.fields()) {
                int fieldOpcode = field.access();
                checkAccessFlag(fieldOpcode, 1);
                // System.out.println(fieldName + " has an Opcode of " + fieldOpcode);
                numFields++;
            }
            int numMethods = 0;
            for (MyMethodNode method : this.node.methods()) {
                int methodOpcode = method.access();
                checkAccessFlag(methodOpcode, 2);
                // System.out.println(methodName + " has an Opcode of " + methodOpcode);
                numMethods++;
            }
            finalCheck(numFields, numMethods, sb);
            return sb.toString();
    }
    // checkAccessFlag takes in a field or method's Opcode, which is used to determine it's access
    // modifier as well as an identifying int to allow the program to know whether the input is a
    // field or method. Once an access modifier is found, it is incremented in the class for use later.
    private void checkAccessFlag(int code, int inputType){
        // public = 1
        // private = 2
        // protected  = 4

        switch(code){
            case 1: 
                if(inputType == 1){
                    this.publicFields++;
                } else {
                    this.publicMethods++;
                }

            case 2:
                if(inputType == 1){
                    this.privateFields++;
                } else {
                    this.privateMethods++;
                }

            case 3: 
                if(inputType == 1){
                    this.protectedFields++;
                } else {
                    this.protectedMethods++;
                }
        }
    }
    
    // 
    private void finalCheck(int numFields, int numMethods, StringBuilder sb2){
        StringBuilder sb = new StringBuilder("\nInformation Hiding Report for " + this.node.name() + " :\n");

        if(numFields >= 4){
            double result = this.publicFields / numFields;
            if(result >= 0.9){
                sb.append("A siginicant number of the fields are public. Please determine if some of these can be changed to a more private access modifier.\n");
            } else if(result >= 0.5){
                sb.append("The majority of fields are public. Please determine if some of these can be changed to a more private access modifier.\n");
            } else if(result >= 0.3){
                sb.append("Some fields are public. Please determine if some of these can be changed to a more private access modifier.\n");
            } else {
                sb.append("The majority of fields are not public. Please continue to keep hiding information.\n");
            }
        } else {
            sb.append("Not enough fields to accurately determine proper information hiding\n");
        }
        if(numMethods >= 4){
            double result2 = this.publicMethods / numMethods;
            if(result2 >= 0.9){
                sb.append("A siginicant number of the methods are public. Please determine if some of these can be changed to a more private access modifier.\n");
            } else if(result2 >= 0.5){
                sb.append("The majority of methods are public. Please determine if some of these can be changed to a more private access modifier.\n");
            } else if(result2 >= 0.3){
                sb.append("Some methods are public. Please determine if some of these can be changed to a more private access modifier.\n");
            } else {
                sb.append("The majority of methods are not public. Please continue to keep hiding information.\n");
            }
        } else {
            sb.append("Not enough methods to accurately determine proper information hiding\n");
        } 
        sb.append("Finish Information Hiding Report for" + this.node.name() + "\n");
        sb2.append(sb.toString());

        // print(sb.toString());
        resetCounter();
    }

    private void resetCounter(){
        this.publicFields = 0;
        this.privateFields = 0;
        this.protectedFields = 0;
        this.publicMethods = 0;
        this.privateMethods = 0;
        this.protectedMethods = 0;
    }

    
    
}
