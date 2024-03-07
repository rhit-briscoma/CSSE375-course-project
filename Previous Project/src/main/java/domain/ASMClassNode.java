package domain;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;


public class ASMClassNode extends MyClassNode {

ClassNode cNode;
Path classDirPath;

public ASMClassNode(ClassNode node, Path dirPath){
    this.cNode = node;
    this.classDirPath = dirPath;

}

@Override
public int access() {
    // TODO Auto-generated method stub
    return cNode.access;
}

@Override
public String name() {
    // TODO Auto-generated method stub
    return cNode.name;
}

@Override
public List<MyFieldNode> fields() {
    // TODO Auto-generated method stub
    List<MyFieldNode> myFieldNodes = new ArrayList<MyFieldNode>();
    for(FieldNode fNode: cNode.fields){
        MyFieldNode fieldNode = new ASMFieldNode(fNode);
        myFieldNodes.add(fieldNode);
    }
    return myFieldNodes;
}

@Override
public String signature() {
    // TODO Auto-generated method stub
    return cNode.signature;
}

@Override
public String superName() {
    // TODO Auto-generated method stub
    return cNode.superName;
}

@Override
public List<String> interfaces() {
    // TODO Auto-generated method stub
    return cNode.interfaces;
}

@Override
public List<MyMethodNode> methods() {
    // TODO Auto-generated method stub
    List<MyMethodNode> myMethodNodes = new ArrayList<MyMethodNode>();
    for(MethodNode mNode: cNode.methods){
        MyMethodNode methodNode = new ASMMethodNode(mNode);
        myMethodNodes.add(methodNode);
    }
    return myMethodNodes;
}

@Override
public Path classFilePath() {
    // TODO Auto-generated method stub
    return this.classDirPath;
}
    
}
