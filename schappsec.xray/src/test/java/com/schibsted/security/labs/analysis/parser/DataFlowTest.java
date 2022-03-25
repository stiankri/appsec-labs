package com.schibsted.security.labs.analysis.parser;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class DataFlowTest {

    @Test
    void basic() {
        var directory = Paths.get("src/test/java/com/schibsted/security/labs/analysis/testingground/multiple");

        var classes = Parser.parseDirectory(directory);

        var dataFlow = new DataFlow(classes);

        var usages = dataFlow.getUsages();
    }
}
