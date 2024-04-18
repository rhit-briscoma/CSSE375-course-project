package domain.checks.test_checks;

import domain.MyClassNode;
import domain.checks.Check;

public class TestCheck1 extends Check {

    @Override
    public String performCheck(MyClassNode node) {
        return "Test Check 1 result";
    }
    
}
