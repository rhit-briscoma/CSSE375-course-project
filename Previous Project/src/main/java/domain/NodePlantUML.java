package domain;

import java.util.ArrayList;
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
        this.className = getName(this.node);
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
                this.codeUML.append("+class ");
                break;
            case Opcodes.ACC_ABSTRACT:
                this.codeUML.append("+abstract class ");
                break;
            case Opcodes.ACC_ENUM:
                this.codeUML.append("enum ");
                break;
            case Opcodes.ACC_INTERFACE:
                this.codeUML.append("+interface ");
            case Opcodes.ACC_PRIVATE:
                this.codeUML.append("+class - ");
                break;
            case Opcodes.ACC_PROTECTED:
                this.codeUML.append("+class # ");
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
            this.codeUML.append(getName(current)); // name needs to be parsed
            this.codeUML.append(": ");
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
            this.codeUML.append(getDesc(field));
        } else { // has a different type of field (List, HashMap, etc.)
            StringBuilder simpSig = simplifySignature(field.signature()); // includes '<' at beginning
            String desc = getDesc(field);
            parseSignature(simpSig, desc);
        }
    }

    private String getDesc(MyFieldNode field){
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
            return name.toString();
    }

    private String getDesc(MyMethodNode method){
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
            return name.toString();
    }

    private void parseSignature(StringBuilder sig, String desc){
        List<String> typesToParseList = new ArrayList<>();
        int length = sig.length();
        StringBuilder type = new StringBuilder();

        for(int i = 1; i < length; i++){
            char c = sig.charAt(i);
            if(c == '<') break;
            else if (c == ';'){
                typesToParseList.add(type.toString());
                type.setLength(0);
                continue;
            }
            else type.append(c);

        }
        this.codeUML.append(desc + "<");
        for(int i = 0; i < typesToParseList.size(); i++){
            this.codeUML.append(typesToParseList.get(i));
            if(i == typesToParseList.size() - 1){
                break;
            } else this.codeUML.append(", ");
        }
        this.codeUML.append("> \n");
    }


    private void handleMethods() {
        for (int i = 0; i < this.methods.size(); i++) {
            MyMethodNode current = this.methods.get(i);
            handleMethodAccessModifiers(current);
            this.codeUML.append(getName(current)); // name needs to be parsed
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

    private StringBuilder simplifySignature(String sig){
        StringBuilder full = new StringBuilder(sig);
        String reversed = full.reverse().toString();
        StringBuilder newSig = new StringBuilder();
        int length = full.length();
        for(int i = 0; i < length; i++){
            char c = reversed.charAt(i);
            newSig.append(c);
            if (c == '<'){
                break;
            } 
        }
        newSig = newSig.reverse();
        return newSig;
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

    private String getName(MyMethodNode m){
        StringBuilder fullNameRev = new StringBuilder(m.name());
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

    private String getName(MyClassNode classNode){
        StringBuilder fullNameRev = new StringBuilder(classNode.name());
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
