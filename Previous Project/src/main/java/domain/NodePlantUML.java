package domain;

import org.objectweb.asm.tree.ClassNode;

import java.util.List;

import org.objectweb.asm.Opcodes;

public class NodePlantUML {

    private MyClassNode node;
    private List<MyFieldNode> fields;
    private List<MyMethodNode> methods;
    private StringBuilder codeUML;
    private StringBuilder connectionsUML;

    public NodePlantUML(MyClassNode incoming){
        this.node = incoming;
        this.fields = this.node.fields();
        this.methods = this.node.methods();
        this.codeUML = new StringBuilder();
        this.connectionsUML = new StringBuilder();
    }

    public void generate(){      
        parseCode();
    }

    private void parseCode(){
        this.codeUML.append("+");
        handleClassAccessModifier();
        handleClassName();
    }

    private void handleClassAccessModifier(){
       int access = this.node.access();
       switch(access){
        case Opcodes.ACC_PUBLIC:
            this.codeUML.append("class ");
            break;
        case Opcodes.ACC_ABSTRACT:
            this.codeUML.append('"' +"abstract class" + '"' + " ");
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

    private void handleClassName(){
        String className = this.node.getClass().getName();
        StringBuilder temp = new StringBuilder();
        for(int i = className.length() - 1; i > -1; i--){
            char c = className.charAt(i);
            if(c == '.'){
                this.codeUML.append(temp.reverse().toString()).append("{\n");
                break;
            } else {
                temp.append(c);
            }
        }
    }

    private void handleFields(){
        for(int i = 0; i < this.fields.size(); i++){
            MyFieldNode current = this.fields.get(i);
            handleAccessModifiers(current);
        }
    }

    private void handleAccessModifiers(MyFieldNode field){
        int access = field.access();
       switch(access){
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

    private void handleAccessModifiers(MyMethodNode method){
        int access = method.access();
       switch(access){
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
            this.codeUML.append("+{abstract}");
            break;
        case Opcodes.ACC_STATIC:
            this.codeUML.append("+{static}");
            break;       
        default:
            this.codeUML.append("ERROR READING METHOD ACCESS MODIFIER");
       } 
    }

}
