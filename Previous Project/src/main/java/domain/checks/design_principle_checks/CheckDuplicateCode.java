package domain.checks.design_principle_checks;

import domain.MyClassNode;
import domain.MyMethodNode;

public class CheckDuplicateCode extends PrincipleCheck {

    public CheckDuplicateCode(){
    }

    public boolean checkDuplicates() {
        for (int i = 0; i < this.node.methods().size(); i++) {
            for (int j = i + 1; j < this.node.methods().size(); j++) {
                MyMethodNode method1 = this.node.methods().get(i);
                MyMethodNode method2 = this.node.methods().get(j);
                if (!areAllInstructionsEqual(method1, method2)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean areAllInstructionsEqual(MyMethodNode method1, MyMethodNode method2) {
        if (!isInitMethod(method1) && !isInitMethod(method2)) {
            return method1.desc().equals(method2.desc()) &&
                    method1.access() == method2.access() &&
                    method1.maxLocals() == method2.maxLocals() &&
                    method1.maxStack() == method2.maxStack() &&
                    method1.exceptions().equals(method2.exceptions()) &&
                    method1.instructions().size() == method2.instructions().size();
        }
        return true;
    }

    private boolean isInitMethod(MyMethodNode method) {
        return "<init>".equals(method.name());
    }

    @Override
    public String performCheck(MyClassNode node) {
        this.node = node;
        if (checkDuplicates())
            return "Class " + this.node.name() + " has duplicate code. " +
                    "Please refactor to remove the duplicate code";
        else
            return "Class " + this.node.name() + " does not have duplicate code.";
    }

}
