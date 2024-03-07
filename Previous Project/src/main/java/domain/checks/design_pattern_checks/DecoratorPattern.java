package domain.checks.design_pattern_checks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.MyClassNode;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import datasource.ClassFileReader;
import domain.MyClassNode;
import domain.MyMethodNode;

public class DecoratorPattern extends PatternCheck {

    // This is where the implementation of the decorator pattern will go
    // the decorator pattern is a design pattern where a an instance of a superclass
    // contains another instance
    // of that same class so that "decorations" can be made to certain methods of
    // that class without having to create new instances
    // for the different modifications.
    // using the asm library, I believe that the way to do this will be to find a
    // class that contains an instance of a super class then
    // once we find that class, check if the class containing the superclass has the
    // same methods as
    // that super class. The violation would be if the
    // proposed decorator class has the instance of the class it wants to decorate,
    // but not the same operations

    private String result;

    public DecoratorPattern() {
        this.result = "";
    }



    private String performWork() {
        result = "";
        // Find all instances of abstract super nodes in the project

        if (this.node.superName().equals("java/lang/Object") == true) {
            return "Because " + this.node.name() + " does not implement another class, Decorator pattern not violated";
        } else {
            for (String s : this.node.classFilePath().getParent().toString().split("\\\\")) {
                if (s.equals(this.node.superName().split("/")[this.node.superName().split("/").length - 1])) {
                    ClassFileReader cReader = new ClassFileReader();
                    MyClassNode newNode = cReader.readBuiltInClass(this.node.classFilePath().toString() + this.node.superName().split("/")[this.node.superName().split("/").length - 1] + ".class");
                    if(newNode != null){
                        if (checkSameNumPublic(this.node, newNode) == false) {
                            result +=this.node.name() + "does not have the same number of public classes as " + newNode.name();
                            result += this.node.name() + "has commited a violation of the Decorator Design Pattern";

                        }
                        // check if the method signatures are equal
                        if (checkSameSignature(this.node, newNode) == false) {
                            result += this.node.name() + "has commited a violation of the Decorator Design Pattern";
                        }
                    }
                }
            }
        }

        if(result.equals("")){
            return "No violations to the Decorator Pattern Commited";
        }

        return result;
    }

    public boolean checkSameNumPublic(MyClassNode superNode, MyClassNode decoratorNode) {
        int superCount = 0;
        int decoratorCount = 0;

        for (MyMethodNode method : superNode.methods()) {
            if ((method.access() & Opcodes.ACC_PUBLIC) != 0) {
                superCount++;
            }
        }

        for (MyMethodNode method : decoratorNode.methods()) {
            if ((method.access() & Opcodes.ACC_PUBLIC) != 0) {
                decoratorCount++;
            }
        }
        return superCount == decoratorCount;
    }

    public boolean checkSameSignature(MyClassNode superNode, MyClassNode decoratorNode) {

        for (MyMethodNode method : superNode.methods()) {
            if ((method.access() & Opcodes.ACC_PUBLIC) != 0) {
                if (containsMethod(decoratorNode, method) == false) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean containsMethod(MyClassNode decoratorNode, MyMethodNode methodNode) {
        for (MyMethodNode method : decoratorNode.methods()) {
            if (method.name().equals(methodNode.name()) && method.desc().equals(methodNode.desc())) {
                return true;
            } else {
                result += decoratorNode.name() + "does not have the same method signature as its super class";
                return false;
            }
        }
        result += decoratorNode.name() + "Does not contain the same method as its super class";
        return false;
    }

    @Override
    public String performCheck(MyClassNode node) {
        this.node = node;
        return performWork();
    }

}
