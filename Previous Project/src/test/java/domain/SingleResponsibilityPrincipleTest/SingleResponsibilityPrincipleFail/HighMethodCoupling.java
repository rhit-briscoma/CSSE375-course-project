package domain.SingleResponsibilityPrincipleTest.SingleResponsibilityPrincipleFail;

public class HighMethodCoupling {
    public void mainMethod() {
        sideMethod();
        sideMethod();
        sideMethod();
        sideMethod();
        sideMethod();
    }

    public void sideMethod() {
        mainMethod();
        mainMethod();
        mainMethod();
        mainMethod();
        mainMethod();
    }
}
