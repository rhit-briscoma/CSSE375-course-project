package datasource;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import domain.ASMClassNode;
import domain.MyClassNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClassFileReader implements IClassFileReader {
    @Override
    public MyClassNode readClassFile(Path classFilePath) {
        try {
            byte[] classBytes = Files.readAllBytes(classFilePath);
            ClassReader reader = new ClassReader(classBytes);
            ClassNode classNode = new ClassNode();
            reader.accept(classNode, ClassReader.EXPAND_FRAMES);
            MyClassNode myClassNode = new ASMClassNode(classNode, classFilePath);
            return myClassNode;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle exception appropriately
        }
    }

    @Override
    public MyClassNode readBuiltInClass(String filePath) {
        try{
            ClassReader reader = new ClassReader(filePath);
            ClassNode classNode = new ClassNode();
            reader.accept(classNode, ClassReader.EXPAND_FRAMES);
            MyClassNode myClassNode = new ASMClassNode(classNode, null);
            return myClassNode;
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
        
    }
}
