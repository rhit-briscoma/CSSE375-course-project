package domain.StrategyPatternTest.LikelyContext;

public class HasStrategyField {
    private InterfaceStrat strategy;

    public void useStrategy() {
        strategy.getInt();
    }
}
