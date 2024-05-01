package domain;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.util.List;

import org.objectweb.asm.Opcodes;

public class NodePlantUML {

    private MyClassNode node;
    private List<MyFieldNode> fields;
    private List<MyMethodNode> methods;
    private StringBuilder codeUML;
    private StringBuilder connectionsUML;

    private String className;

    public NodePlantUML(MyClassNode incoming) {
        this.node = incoming;
        this.fields = this.node.fields();
        this.methods = this.node.methods();
        this.codeUML = new StringBuilder();
        this.connectionsUML = new StringBuilder();
        this.className = this.node.name();
    }

    public void generate() {
        parseCode();
    }

    private void parseCode() {
        this.codeUML.append("+");
        handleClassAccessModifier();
        handleClassName();
        handleFields();
        handleMethods();
    }

    private void handleClassAccessModifier() {
        int access = this.node.access();
        switch (access) {
            case Opcodes.ACC_PUBLIC:
                this.codeUML.append("class ");
                break;
            case Opcodes.ACC_ABSTRACT:
                this.codeUML.append('"' + "abstract class" + '"' + " ");
                break;
            case Opcodes.ACC_ENUM:
                this.codeUML.append("enum ");
                break;
            case Opcodes.ACC_PRIVATE:
                this.codeUML.append("class -");
                break;
            case Opcodes.ACC_PROTECTED:
                this.codeUML.append("class #");
                break;
            default:
                this.codeUML.append("ERROR READING CLASS ACCESS MODIFIER");
        }
    }

    private void handleClassName() {
        String className = this.className;
        StringBuilder temp = new StringBuilder();
        for (int i = className.length() - 1; i > -1; i--) {
            char c = className.charAt(i);
            if (c == '/') {
                this.codeUML.append(temp.reverse().toString()).append("{\n");
                break;
            } else {
                temp.append(c);
            }
        }
    }

    private void handleFields() {
        for (int i = 0; i < this.fields.size(); i++) {
            MyFieldNode current = this.fields.get(i);
            handleFieldAccessModifiers(current);
            this.codeUML.append(getName(current)); // this needs parsed
            handleFieldConnections(current);
        }
    }

    private void handleFieldAccessModifiers(MyFieldNode field) {
        int access = field.access();
        switch (access) {
            case Opcodes.ACC_PUBLIC:
                this.codeUML.append("\t+");
                break;
            case Opcodes.ACC_PRIVATE:
                this.codeUML.append("\t-");
                break;
            case Opcodes.ACC_PROTECTED:
                this.codeUML.append("\t#");
                break;
            default:
                this.codeUML.append("ERROR READING FIELD ACCESS MODIFIER");
        }
    }

    private void handleFieldConnections(MyFieldNode field) {
        if (field.signature() == null) {
            StringBuilder type = new StringBuilder(field.desc());
            String reversed = type.reverse().toString();
            StringBuilder name = new StringBuilder();
            int length = type.length();
            for (int i = 0; i < length; i++) {
                char c = reversed.charAt(i);
                if (c == '/') {
                    break;
                } else {
                    name.append(c);
                }
            }
            name = name.reverse();
            this.codeUML.append(name);
        } else { // has a different type of field (List, HashMap, etc.)

        }
    }

    private void handleMethods() {
        for (int i = 0; i < this.methods.size(); i++) {
            MyMethodNode current = this.methods.get(i);
            handleMethodAccessModifiers(current);
        }
    }

    private void handleMethodAccessModifiers(MyMethodNode method) {
        int access = method.access();
        switch (access) {
            case Opcodes.ACC_PUBLIC:
                this.codeUML.append("\t+");
                break;
            case Opcodes.ACC_PRIVATE:
                this.codeUML.append("\t-");
                break;
            case Opcodes.ACC_PROTECTED:
                this.codeUML.append("\t#");
                break;
            case Opcodes.ACC_ABSTRACT:
                this.codeUML.append("\t+{abstract}");
                break;
            case Opcodes.ACC_STATIC:
                this.codeUML.append("\t+{static}");
                break;
            default:
                this.codeUML.append("ERROR READING METHOD ACCESS MODIFIER");
        }
        if (method.signature() == null) {
            StringBuilder type = new StringBuilder(method.desc());
            String reversed = type.reverse().toString();
            StringBuilder name = new StringBuilder();
            int length = type.length();
            for (int i = 0; i < length; i++) {
                char c = reversed.charAt(i);
                if (c == '/') {
                    break;
                } else {
                    name.append(c);
                }
            }
            name = name.reverse();
        }
    }

    private void handleConnections() {
        // interfaces
        for (int i = 0; i < this.node.interfaces().size(); i++) {
            this.connectionsUML.append(this.className + " ..> " + this.node.interfaces().get(i) + "\n");
        }

        // inheritance
        if (this.node.superName() != null) {
            this.connectionsUML.append(this.node.superName() + " <|-- " + this.className + "\n");
        }

        // fields and method parameters
    }

    private String getName(MyFieldNode f){
        StringBuilder fullNameRev = new StringBuilder(f.name());
        fullNameRev = fullNameRev.reverse();
        int length = fullNameRev.length();
        StringBuilder name = new StringBuilder();
        for(int i = 0; i < length; i++){
            char c = fullNameRev.charAt(i);
                if (c == '/') {
                    break;
                } else {
                    name.append(c);
                }
        }
        return name.reverse().toString();
    }

}
