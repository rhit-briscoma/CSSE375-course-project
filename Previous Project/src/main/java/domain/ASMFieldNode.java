package domain;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class ASMFieldNode extends MyFieldNode {

    FieldNode node;

    public ASMFieldNode(FieldNode fNode){
        this.node = fNode;
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
    public Object value() {
        // TODO Auto-generated method stub
        return node.value;
    }
    
}
