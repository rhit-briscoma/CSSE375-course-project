package domain.checks.test_checks;

import domain.MyClassNode;
import domain.checks.Check;

public class TestCheck2 extends Check {

    @Override
    public String performCheck(MyClassNode node) {
        return "Test Check 2 result";
    }
    
}
