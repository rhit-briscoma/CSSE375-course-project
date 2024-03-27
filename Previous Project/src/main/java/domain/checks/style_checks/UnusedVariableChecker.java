package domain.checks.style_checks;

import domain.MyClassNode;
import domain.MyFieldNode;
import domain.MyMethodNode;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.Type;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UnusedVariableChecker extends StyleCheck {

    StringBuilder sb;

    Set<String> usedFields = new HashSet<>();

    public UnusedVariableChecker() {
    }

    public String performWork(MyClassNode node) {
        // Check for Used Class Fields
        for (MyMethodNode method : node.methods()) {
            InsnList instructions = method.instructions(); 
            for (AbstractInsnNode insn : instructions) {
                if (insn instanceof FieldInsnNode) {
                    FieldInsnNode fieldInsn = (FieldInsnNode) insn;
                    usedFields.add(fieldInsn.name);
                }
            }
        }

        // Report Unused Class Fields
        for (MyFieldNode field : node.fields()) {
            if (!usedFields.contains(field.name())) {
                sb.append("Unused class field found: ").append(field.name()).append("\n");
            }
        }

        // Check for Unused Local Variables in Methods
        for (MyMethodNode method : node.methods()) {
            Set<Integer> usedVariables = new HashSet<>();
            InsnList instructions = method.instructions();

            for (AbstractInsnNode insn : instructions) {
                if (insn instanceof VarInsnNode) {
                    VarInsnNode varInsn = (VarInsnNode) insn;
                    usedVariables.add(varInsn.var);
                }
            }

            int startIdx = Type.getArgumentTypes(method.desc()).length; 
            if ((method.access() & Opcodes.ACC_STATIC) == 0) {
                startIdx++;
            }

            for (int i = startIdx; i < method.maxLocals(); i++) { 
                if (!usedVariables.contains(i)) {
                    sb.append("Unused local variable found at index ").append(i).append(" in method: ").append(method.name()).append("\n");
                }
            }
        }

        if (sb.length() == 0) {
            sb.append("No unused variables found in class: ").append(node.name()).append("\n");
        }

        return sb.toString();
    }

    private void findUnusedClassFiles(List<MyMethodNode> methods, List<MyFieldNode> fields){
        // Check for Used Class Fields
        for (MyMethodNode method : methods) {
            InsnList instructions = method.instructions(); 
            for (AbstractInsnNode insn : instructions) {
                if (insn instanceof FieldInsnNode) {
                    FieldInsnNode fieldInsn = (FieldInsnNode) insn;
                    usedFields.add(fieldInsn.name);
                }
            }
        }

        // Report Unused Class Fields
        for (MyFieldNode field : fields) {
            if (!usedFields.contains(field.name())) {
                sb.append("Unused class field found: ").append(field.name()).append("\n");
            }
        }
    }

    private void findUnusedLocalVariablesInMethods(List<MyMethodNode> methods){
        // Check for Unused Local Variables in Methods
        for (MyMethodNode method : methods) {
            Set<Integer> usedVariables = new HashSet<>();
            InsnList instructions = method.instructions();

            for (AbstractInsnNode insn : instructions) {
                if (insn instanceof VarInsnNode) {
                    VarInsnNode varInsn = (VarInsnNode) insn;
                    usedVariables.add(varInsn.var);
                }
            }

            int startIdx = Type.getArgumentTypes(method.desc()).length; 
            if ((method.access() & Opcodes.ACC_STATIC) == 0) {
                startIdx++;
            }

            for (int i = startIdx; i < method.maxLocals(); i++) { 
                if (!usedVariables.contains(i)) {
                    sb.append("Unused local variable found at index ").append(i).append(" in method: ").append(method.name()).append("\n");
                }
            }
        }
    }



    @Override
    public String performCheck(MyClassNode node) {
        sb = new StringBuilder();
        this.node = node;
        List<MyMethodNode> methods = node.methods();
        List<MyFieldNode> fields = node.fields();
        findUnusedClassFiles(methods, fields);
        findUnusedLocalVariablesInMethods(methods);
        if (sb.isEmpty()) {
            sb.append("No unused variables found in class: ").append(node.name()).append("\n");
        }

        return this.sb.toString();
    }
    
}
