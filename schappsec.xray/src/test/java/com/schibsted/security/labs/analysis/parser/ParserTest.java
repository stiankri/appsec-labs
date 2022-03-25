package com.schibsted.security.labs.analysis.parser;

import com.schibsted.security.labs.analysis.model.basic.FullyQualifiedClassName;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    void basic() {
        var directory = Paths.get("src/test/java/com/schibsted/security/labs/analysis/testingground");

        var classes = Parser.parseDirectory(directory);
        assertEquals(6, classes.size());

        var classIdentifier = new FullyQualifiedClassName("com.schibsted.security.labs.analysis.testingground", "TestClass");
        var clazz = classes.get(classIdentifier);

        assertEquals(clazz.className(), classIdentifier.className());
    }
}
