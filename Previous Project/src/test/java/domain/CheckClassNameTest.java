package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.checks.style_checks.CheckClassName;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckClassNameTest {

    @Test
    public void testCheckIfClassNameFromJavaTrue() throws ClassNotFoundException, IOException {
        CheckClassName checkClassName = new CheckClassName();
        assertTrue(checkClassName.result("java/lang/String"));
    }

    @Test
    public void testCheckIfClassNameFromJavaFalse() throws ClassNotFoundException, IOException {
        CheckClassName checkClassName = new CheckClassName();
        assertFalse(checkClassName.result("java/lang/string"));
    }


    @Test
    public void testCheckIfClassNameNotFromJavaTrue() throws ClassNotFoundException, IOException {
        CheckClassName checkClassNameGood = new CheckClassName();
        assertTrue(checkClassNameGood.result("domain/Good"));
    }

    @Test
    public void testCheckIfClassNameNotFromJavaFalse() throws ClassNotFoundException, IOException {
        CheckClassName checkClassNameBad = new CheckClassName();
        assertFalse(checkClassNameBad.result("domain/bad"));
    }

    @Test
    public void testCheckPascalCaseFalse() throws ClassNotFoundException, IOException {
        CheckClassName checkClassNameNotPascalCase = new CheckClassName();
        assertFalse(checkClassNameNotPascalCase.result("domain/Notpascalcase"));
    }

    @Test
    public void testCheckPascalCaseTrue() throws ClassNotFoundException, IOException {
        CheckClassName checkClassNamePascalCase = new CheckClassName();
        assertTrue(checkClassNamePascalCase.result("domain/AdhereAdit"));
    }

}
