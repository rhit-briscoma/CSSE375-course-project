
package domain.checks.design_pattern_checks;

import domain.MyClassNode;
import domain.MyFieldNode;
import domain.MyMethodNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

public class StrategyPattern extends PatternCheck {

    public StrategyPattern(){}

    @Override
    public String performCheck(MyClassNode classNode) {
        StringJoiner result = new StringJoiner("\n");
        Set<String> potentialStrategies = new HashSet<>();
        boolean isPotentialContext = false;

        if (classNode.interfaces() != null && !classNode.interfaces().isEmpty()) {
            for (String iface : classNode.interfaces()) {
                potentialStrategies.add(iface);
                result.add("Potential strategy interface found: " + iface);
            }
        }

        if (!potentialStrategies.isEmpty()) {
            for (String iface : potentialStrategies) {
                if (classNode.name().contains(iface)) {
                    result.add("Concrete strategy implementation detected: " + classNode.name());
                    return result.toString();
                }
            }
        }

        for (MyFieldNode field : classNode.fields()) {
            if (isInterfaceType(field.desc())) {
                result.add("Field with strategy interface type detected: " + field.desc());
                isPotentialContext = true;
            }
        }

        if (isPotentialContext) {
            for (MyMethodNode method : classNode.methods()) {
                if (isDelegatingToStrategy(method)) {
                    result.add("Method potentially delegating to a strategy: " + method.name());
                }
            }
        }

        if (!isPotentialContext && potentialStrategies.isEmpty()) {
            result.add("No strategy pattern detected in class: " + classNode.name());
        } else if (isPotentialContext) {
            result.add("Class likely represents a context in the Strategy Pattern: " + classNode.name());
        }

        return result.toString();
    }

    private boolean isInterfaceType(String desc) {
        return desc.contains("Interface");
    }

    private boolean isDelegatingToStrategy(MyMethodNode method) {
        for (AbstractInsnNode insn : method.instructions()) { 
            if (insn instanceof MethodInsnNode) {
                MethodInsnNode minsn = (MethodInsnNode) insn;
                if (minsn.owner.contains("Interface")) {
                    return true;
                }
            }
        }
        return false;
    }
}
