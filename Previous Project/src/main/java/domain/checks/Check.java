package domain.checks;

import org.objectweb.asm.tree.ClassNode;

import domain.MyClassNode;

public abstract class Check {

    protected MyClassNode node = null;

    public abstract String performCheck(MyClassNode node);

    protected void print(String string){
        System.out.println(string);
    }
}
