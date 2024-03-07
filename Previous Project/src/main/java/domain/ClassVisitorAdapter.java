package domain;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassVisitorAdapter extends ClassVisitor {

    public ClassVisitorAdapter() {
        super(Opcodes.ASM7);
    }


    public void visit(int version, int access, String name, String descriptor, String signature, String[] exceptions) {
        super.visit(version, access, name, descriptor, signature, exceptions);
    }

    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

}