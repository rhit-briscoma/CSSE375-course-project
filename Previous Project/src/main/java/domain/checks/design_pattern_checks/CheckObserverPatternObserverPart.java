package domain.checks.design_pattern_checks;

import org.objectweb.asm.MethodVisitor;

import domain.ClassVisitorAdapter;

public class CheckObserverPatternObserverPart extends ClassVisitorAdapter {

    private boolean hasUpdateMethod = false;
    private boolean isObserverInterface = false;



    public CheckObserverPatternObserverPart() {
        super();
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        for (String interfaceName: interfaces) {
            if (interfaceName.contains("Observer")) {
                isObserverInterface = true;
            }
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }

    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (name.equals("update")) {
            hasUpdateMethod = true;
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    public boolean check() {
        return hasUpdateMethod && isObserverInterface;
    }

}
