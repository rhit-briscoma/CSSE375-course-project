package domain;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.List;

public class ASMMethodNode extends MyMethodNode {

    MethodNode node;

    public ASMMethodNode(MethodNode mNode) {
        this.node = mNode;
    }

    @Override
    public int access() {
        // TODO Auto-generated method stub
        return node.access;
    }

    @Override
    public String name() {
        // TODO Auto-generated method stub
        return node.name;
    }

    @Override
    public String desc() {
        // TODO Auto-generated method stub
        return node.desc;
    }

    @Override
    public String signature() {
        // TODO Auto-generated method stub
        return node.signature;
    }

    @Override
    public List<String> exceptions() {
        // TODO Auto-generated method stub
        return node.exceptions;
    }

    @Override
    public InsnList instructions() {
        // TODO Auto-generated method stub
        return node.instructions;
    }

    @Override
    public int maxLocals() {
        // TODO Auto-generated method stub
        return node.maxLocals;
    }

    @Override
    public int maxStack() {
        return node.maxStack;
    }

}
