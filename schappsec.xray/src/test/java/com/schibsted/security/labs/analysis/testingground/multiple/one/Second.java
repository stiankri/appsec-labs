package com.schibsted.security.labs.analysis.testingground.multiple.one;

public class Second {
    public static void singleParameter(String p1) {
        Third.singleParameter(p1);
    }

    public static void twoParameters(String p1, String p2) {
        Third.singleParameter(p1);
        Fourth.singleParameter(p2);
    }
}
