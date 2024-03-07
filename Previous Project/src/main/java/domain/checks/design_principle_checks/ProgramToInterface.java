package domain.checks.design_principle_checks;

import java.util.ArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import datasource.ClassFileReader;
import domain.MyClassNode;
import domain.MyFieldNode;
import domain.MyMethodNode;

public class ProgramToInterface extends PrincipleCheck {
    // For checking if a class violates the Programming to Interface not
    // Implementation principle, first we will check the fields of the class and see
    // if any field is an
    // instance of another concrete class.

    public ProgramToInterface() {

    }

    public String performWork(MyClassNode cNode) {
        // for each concrete node, we go through its fields. If it contains a field that
        // that is in concrete nodes then it is a violation
        // TO ADD: things may not be in the concrete node that should still be
        // considered a violation. May just need to create a class visitor to visit
        // each fields type and see if it is a concrete or super

        for (MyFieldNode fNode : cNode.fields()) {
            if (fNode.desc().length() > 1) {
                // Checking if a class is a built in java class.
                for (String s : cNode.classFilePath().getParent().toString().split("\\\\")) {
                    if (s.equals(fNode.desc().substring(1).split("/")[0])) {
                        ClassFileReader cReader = new ClassFileReader();
                        MyClassNode newNode = cReader.readBuiltInClass(cNode.classFilePath().toString() + fNode.desc().split("/")[fNode.desc().split("/").length - 1] + ".class");
                        if (newNode != null) {
                            if (newNode.superName().equals("java/lang/Object") == false) {
                                // Maybe make this better
                                return cNode.name()+ "violates the Programming to Interface not Implementation check by having a field of a concrete node and not a abstract node";
                            }
                        }
                    }
                }
            }
        }

        // Checks the return type of the method to see if it is of a concrete node
        // which is a violation
        for (MyMethodNode mNode : cNode.methods()) {
            for (String s : cNode.classFilePath().getParent().toString().split("\\\\")) {
                if (s.equals(Type.getReturnType(mNode.desc()).getClassName())) {
                    ClassFileReader cReader = new ClassFileReader();
                    MyClassNode newNode = cReader.readBuiltInClass(cNode.classFilePath().toString() + Type.getReturnType(mNode.desc()).getClassName().split("/")[Type.getReturnType(mNode.desc()).getClassName().split("/").length - 1] + ".class");
                    if (newNode != null) {
                        if (newNode.superName().equals("java/lang/Object") == false) {
                            // Maybe make this better
                            return cNode.name() + "violates the Programming to Interface not Implementation check by having a field of a concrete node and not a abstract node";
                        }
                    }
                }
            }
            
            // checks to see if there is an argument of the method node that is a concrete
            // node, which is a violation
            for (Type argType : Type.getArgumentTypes(mNode.desc())) {
                System.out.println(argType.getClassName());
                for (String s : cNode.classFilePath().getParent().toString().split("\\\\")) {
                    if (s.equals(argType.getClassName())) {
                        ClassFileReader cReader = new ClassFileReader();
                        MyClassNode newNode = cReader.readBuiltInClass(cNode.classFilePath().toString() + argType.getClassName().split("/")[argType.getClassName().split("/").length - 1] + ".class");
                        if (newNode != null) {
                            if (newNode.superName().equals("java/lang/Object") == false) {
                                // Maybe make this better
                                return cNode.name() + "violates the Programming to Interface not Implementation check by having a field of a concrete node and not a abstract node";
                            }
                        }
                    }
                }
            }
        }

        return "No violation of Programming to Interface commited";
    }


    @Override
    public String performCheck(MyClassNode node) {
        // TODO Auto-generated method stub
        return performWork(node);
    }
}