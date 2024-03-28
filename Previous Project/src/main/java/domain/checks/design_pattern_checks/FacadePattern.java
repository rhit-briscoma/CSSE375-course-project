package domain.checks.design_pattern_checks;

import domain.MyClassNode;
import domain.MyMethodNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;

public class FacadePattern extends PatternCheck {

    public FacadePattern(){}

    @Override
    public String performCheck(MyClassNode node) {
        StringBuilder sb = new StringBuilder();

        
        sb.append("Checking for Facade Pattern in class: ").append(node.name() + '\n');

        // Count the number of methods that delegate tasks (a simplified heuristic)
        int delegationMethods = 0;
        for (MyMethodNode method : node.methods()) {
            InsnList instructions = method.instructions(); 
            for (AbstractInsnNode insn : instructions) {
                if (insn instanceof MethodInsnNode) {
                    delegationMethods++;
                }
            }
        }

        // Check for facade pattern based on delegation methods count
        if (delegationMethods > 3) {
            sb.append("The class likely implements the Facade Pattern based on method delegation.");
        } else {
            sb.append("The class likely does not implement the Facade Pattern based on method delegation.");
        }

        return sb.toString();
    }
}
