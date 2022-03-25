package com.schibsted.security.labs.analysis.parser;

import com.schibsted.security.labs.analysis.model.curated.Clazz;
import com.schibsted.security.labs.analysis.model.curated.Method;
import org.antlr.v4.grammars.Java9Lexer;
import org.antlr.v4.grammars.Java9Parser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class MethodListenerTest {
    static Clazz clazz;

    @BeforeAll
    static void setup() {
        var path = Paths.get("src/test/java/com/schibsted/security/labs/analysis/testingground/TestClass.java");
        String code = FileManager.load(path);

        var lexer = new Java9Lexer(CharStreams.fromString(code));
        var tokens = new CommonTokenStream(lexer);
        var parser = new Java9Parser(tokens);
        var tree = parser.compilationUnit();

        var walker = new ParseTreeWalker();
        var listener = new MethodListener();

        walker.walk(listener, tree);
        clazz = listener.getClazz();
    }

    @Test
    void correctPackage() {
        assertEquals("com.schibsted.security.labs.analysis.testingground", clazz.packageName());
    }

    @Test
    void className() {
        assertEquals("TestClass", clazz.className());
    }

    @Test
    void correctMethodNames() {
        var name = clazz.methods().stream()
                .map(Method::name)
                .collect(Collectors.toList());

        assertEquals(List.of("singleParameter",
                "singleParameterSameClass",
                "singleParameterDifferentClass",
                "singleParameterDifferentPackage",
                "twoParameters"), name);
    }
}
