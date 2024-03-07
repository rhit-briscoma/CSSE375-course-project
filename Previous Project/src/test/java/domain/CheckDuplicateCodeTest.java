package domain;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.MethodNode;

import domain.checks.design_principle_checks.CheckDuplicateCode;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CheckDuplicateCodeTest {
    @Test
    public void testAreMethodsNotIdenticalFromJava() throws IOException {
        CheckDuplicateCode checkDuplicateCode = new CheckDuplicateCode();
        assertFalse(checkDuplicateCode.checkDuplicates()); //duplicate code false
    }

    @Test
    public void testAreMethodsIdenticalNotFromJava() throws IOException {
        CheckDuplicateCode checkDuplicateCode = new CheckDuplicateCode();
        assertTrue(checkDuplicateCode.checkDuplicates());
    }
    @Test
    public void testAreMethodsNotIdenticalNotFromJava() throws IOException {
        CheckDuplicateCode checkDuplicateCode = new CheckDuplicateCode();
        assertFalse(checkDuplicateCode.checkDuplicates());
    }

    @Test
    public void testClassAdit() throws IOException {
        CheckDuplicateCode checkDuplicateCode = new CheckDuplicateCode();
        assertTrue(checkDuplicateCode.checkDuplicates());
    }

}
