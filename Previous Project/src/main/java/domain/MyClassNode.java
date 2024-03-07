package domain;

import java.nio.file.Path;
import java.util.List;

public abstract class MyClassNode {

    public MyClassNode() {
        
    }

    public abstract int access();
    public abstract String name();
    public abstract Path classFilePath();
    public abstract List<MyFieldNode> fields();
    public abstract String signature();
    public abstract String superName();
    public abstract List<String> interfaces();
    public abstract List<MyMethodNode> methods();


}
