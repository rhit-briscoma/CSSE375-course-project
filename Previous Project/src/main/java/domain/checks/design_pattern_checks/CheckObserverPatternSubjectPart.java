package domain.checks.design_pattern_checks;
import org.objectweb.asm.MethodVisitor;

import domain.ClassVisitorAdapter;


public class CheckObserverPatternSubjectPart extends ClassVisitorAdapter {
    private boolean hasAddObserverMethod = false;
    private boolean hasRemoveObserverMethod = false;

    public CheckObserverPatternSubjectPart() {
        super();
    }


    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (name.equals("addObserver")) {
            hasAddObserverMethod = true;
        }
        else if (name.equals("removeObserver")) {
            hasRemoveObserverMethod = true;
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    public boolean check() {
        return hasAddObserverMethod && hasRemoveObserverMethod;
    }
}
