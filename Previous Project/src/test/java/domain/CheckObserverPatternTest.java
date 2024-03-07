package domain;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;

import domain.checks.design_pattern_checks.CheckObserverPatternObserverPart;
import domain.checks.design_pattern_checks.CheckObserverPatternSubjectPart;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckObserverPatternTest {
    @Test
    public void testCheckObserverPartTrue() throws Exception {
        ClassReader cr = new ClassReader("domain/ExampleObserver");
        CheckObserverPatternObserverPart cv = new CheckObserverPatternObserverPart();
        cr.accept(cv, 0);
        assertTrue(cv.check());
    }

    @Test
    public void testCheckSubjectPartTrue() throws Exception {
        ClassReader cr = new ClassReader("domain/ExampleSubject");
        CheckObserverPatternSubjectPart sc = new CheckObserverPatternSubjectPart();
        cr.accept(sc, 0);
        assertTrue(sc.check());
    }

    @Test
    public void testCheckObserverFalse() throws Exception {
        ClassReader cr = new ClassReader("domain/ExampleNotObserver");
        CheckObserverPatternObserverPart cv = new CheckObserverPatternObserverPart();
        cr.accept(cv, 0);
        assertFalse(cv.check());
    }

    @Test
    public void testCheckSubjectFalse() throws Exception {
        ClassReader cr = new ClassReader("domain/ExampleNotSubject");
        CheckObserverPatternSubjectPart sc = new CheckObserverPatternSubjectPart();
        cr.accept(sc, 0);
        assertFalse(sc.check());
    }
}
