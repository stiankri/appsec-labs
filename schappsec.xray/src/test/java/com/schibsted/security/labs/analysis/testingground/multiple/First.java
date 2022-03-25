package com.schibsted.security.labs.analysis.testingground.multiple;

import com.schibsted.security.labs.analysis.testingground.multiple.one.Second;

public class First {
    public static void singleParameter(String p1) {
        Second.singleParameter(p1);
    }
}
