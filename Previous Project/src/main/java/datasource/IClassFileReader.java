package datasource;

import domain.MyClassNode;

import java.nio.file.Path;

public interface IClassFileReader {
    MyClassNode readClassFile(Path classFilePath);
    MyClassNode readBuiltInClass(String filePath);
}
