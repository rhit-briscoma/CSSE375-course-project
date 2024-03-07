package domain.checks.design_principle_checks;

import domain.MyClassNode;
import domain.MyMethodNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

public class SingleResponsibilityPrinciple extends PrincipleCheck {

    public SingleResponsibilityPrinciple(){}

    @Override
    public String performCheck(MyClassNode node) {
        // Implementation of the Single Responsibility Principle check
        StringBuilder sb = new StringBuilder("Checking Single Responsibility Principle for class: " + node.name());
        int methodCount = node.methods().size();
        int fieldCount = node.fields().size();
        double avgMethodComplexity = 0;
        int methodInteractions = 0;

        // Check method complexity and interactions
        for (MyMethodNode method : node.methods()) {
            int complexity = 1; // Start with 1 for the method itself
            for (AbstractInsnNode insn : method.instructions()) {
                if (insn instanceof JumpInsnNode) {
                    complexity++; 
                }
            }
            avgMethodComplexity += complexity;

            for (AbstractInsnNode insn : method.instructions()) {
                if (insn instanceof MethodInsnNode) {
                    MethodInsnNode methodInsn = (MethodInsnNode) insn;
                    if (methodInsn.owner.equals(node.name())) {
                        methodInteractions++; 
                    }
                }
            }
        }

        avgMethodComplexity /= methodCount;
        boolean likelyFollowsSRP = true;

        if (avgMethodComplexity > 5) {
            sb.append("Warning: Class methods have high average complexity (" + avgMethodComplexity + "), which might violate SRP.");
            likelyFollowsSRP = false;
        }

        if (methodInteractions > methodCount * 2) {
            sb.append("Warning: High coupling between class methods (" + methodInteractions + " interactions), which might violate SRP.");
            likelyFollowsSRP = false;
        }

        if (fieldCount > 5) {
            sb.append("Warning: Class has many fields (" + fieldCount + "), which might violate SRP.");
            likelyFollowsSRP = false;
        }

        if (likelyFollowsSRP) {
            sb.append("Based on the heuristic, the class likely follows the Single Responsibility Principle.");
        }

        return sb.toString();
    }
}

