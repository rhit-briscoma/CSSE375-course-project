package datasource;

import java.util.Set;

public interface WordLoader {
    public void loadSet();
    public Set<String> getWords();
}
