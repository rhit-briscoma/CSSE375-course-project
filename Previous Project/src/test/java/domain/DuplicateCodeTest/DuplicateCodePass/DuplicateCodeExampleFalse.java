package domain.DuplicateCodeTest.DuplicateCodePass;

public class DuplicateCodeExampleFalse {
    public void countToTen() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }
    public void countToNine() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.println(i + j);
            }
        }
    }
}
