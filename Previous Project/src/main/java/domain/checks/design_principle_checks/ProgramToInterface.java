package domain.checks.design_principle_checks;

import org.objectweb.asm.Type;

import datasource.ClassFileReader;
import domain.MyClassNode;
import domain.MyFieldNode;
import domain.MyMethodNode;

public class ProgramToInterface extends PrincipleCheck {
    // For checking if a class violates the Programming to Interface not
    // Implementation principle, first we will check the fields of the class and see
    // if any field is an
    // instance of another concrete class.

    StringBuilder sb = new StringBuilder();
    public ProgramToInterface() {

    }

    public void readFromClassNode(String nodeName, String filePath) {
        ClassFileReader cReader = new ClassFileReader();
        MyClassNode newNode = cReader.readBuiltInClass(filePath);
        if (newNode != null && newNode.superName().equals("java/lang/Object") == false) {
            sb.append(nodeName + " violates the Programming to Interface not Implementation check by having a field of a concrete node and not a abstract node\n");
        }
    }

    public String performWork(MyClassNode cNode) {
        // for each concrete node, we go through its fields. If it contains a field that
        // that is in concrete nodes then it is a violation
        // TO ADD: things may not be in the concrete node that should still be
        // considered a violation. May just need to create a class visitor to visit
        // each fields type and see if it is a concrete or super

        String[] parentStrings = cNode.classFilePath().getParent().toString().split("\\\\");

        for (MyFieldNode fNode : cNode.fields()) {
            if (fNode.desc().length() > 1) {
                // Checking if a class is a built in java class.
                for (String s : parentStrings) {
                    if (s.equals(fNode.desc().substring(1).split("/")[0])) {
                        // ClassFileReader cReader = new ClassFileReader();
                        // MyClassNode newNode = cReader.readBuiltInClass(cNode.classFilePath().toString() + fNode.desc().split("/")[fNode.desc().split("/").length - 1] + ".class");
                        // if (newNode != null 
                        //     && newNode.superName().equals("java/lang/Object") == false) {
                        //     // Maybe make this better
                        //     return cNode.name()+ "violates the Programming to Interface not Implementation check by having a field of a concrete node and not a abstract node";
                        // }
                        readFromClassNode(cNode.name(), cNode.classFilePath().toString() + fNode.desc().split("/")[fNode.desc().split("/").length - 1] + ".class");
                    }
                }
            }
        }

        // Checks the return type of the method to see if it is of a concrete node
        // which is a violation
        for (MyMethodNode mNode : cNode.methods()) {
            for (String s : parentStrings) {
                if (s.equals(Type.getReturnType(mNode.desc()).getClassName())) {
                    // ClassFileReader cReader = new ClassFileReader();
                    // MyClassNode newNode = cReader.readBuiltInClass(cNode.classFilePath().toString() + Type.getReturnType(mNode.desc()).getClassName().split("/")[Type.getReturnType(mNode.desc()).getClassName().split("/").length - 1] + ".class");
                    // if (newNode != null 
                    //     && newNode.superName().equals("java/lang/Object") == false) {
                    //     // Maybe make this better
                    //     return cNode.name() + "violates the Programming to Interface not Implementation check by having a field of a concrete node and not a abstract node";
                    // }
                    readFromClassNode(cNode.name(), cNode.classFilePath().toString() + Type.getReturnType(mNode.desc()).getClassName().split("/")[Type.getReturnType(mNode.desc()).getClassName().split("/").length - 1] + ".class");
                }
            }
            
            // checks to see if there is an argument of the method node that is a concrete
            // node, which is a violation
            for (Type argType : Type.getArgumentTypes(mNode.desc())) {
                System.out.println(argType.getClassName());
                for (String s : parentStrings) {
                    if (s.equals(argType.getClassName())) {
                        // ClassFileReader cReader = new ClassFileReader();
                        // MyClassNode newNode = cReader.readBuiltInClass(cNode.classFilePath().toString() + argType.getClassName().split("/")[argType.getClassName().split("/").length - 1] + ".class");
                        // if (newNode != null && 
                        //     newNode.superName().equals("java/lang/Object") == false) {
                        //     // Maybe make this better
                        //     return cNode.name() + "violates the Programming to Interface not Implementation check by having a field of a concrete node and not a abstract node";
                        // }
                        readFromClassNode(cNode.name(), cNode.classFilePath().toString() + argType.getClassName().split("/")[argType.getClassName().split("/").length - 1] + ".class");
                    }
                }
            }
        }
        if(sb.isEmpty()) {
        return "No violation of Programming to Interface commited";
        } else {
            return sb.toString();
        }
    }


    @Override
    public String performCheck(MyClassNode node) {
        // TODO Auto-generated method stub
        sb = new StringBuilder();
        return performWork(node);
    }
}