package domain;

public abstract class MyFieldNode {

    public MyFieldNode() {

    }

    public abstract int access();
    public abstract String name();
    public abstract String desc();
    public abstract String signature();
    public abstract Object value();   

}