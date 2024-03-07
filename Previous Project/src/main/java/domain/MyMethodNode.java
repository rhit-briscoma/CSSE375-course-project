package domain;

import java.util.List;

import org.objectweb.asm.tree.InsnList;

public abstract class MyMethodNode {


    public MyMethodNode(){

}

public abstract int access();
public abstract String name();
public abstract String desc();
public abstract String signature();
public abstract List<String> exceptions();
public abstract InsnList instructions();
public abstract int maxLocals();

    public abstract int maxStack();
}