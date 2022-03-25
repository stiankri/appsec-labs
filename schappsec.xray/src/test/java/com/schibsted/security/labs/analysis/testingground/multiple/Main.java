package com.schibsted.security.labs.analysis.testingground.multiple;

import com.schibsted.security.labs.analysis.testingground.multiple.one.Second;
import com.schibsted.security.labs.testingground.MyAnnotation;

/**
 * javadoc goes here
 */
public class Main {
    public static final int MY_FIELD_CONSTANT = 1;

    public void singleParameter(String p1) {
        // my inline comment
        var stringLiteral = "mystring";
        var intLiteral = 1337;
        var longLiteral = 2048L;
        var hexLiteral = 0xff;
        var var = "var";
        var trueLiteral = true;
        var falseLiteral = false;
        Object nullLiteral = null;
        var constant = MY_FIELD_CONSTANT;
    }

    public void singleParameterSameClass(String p1) {
        // TODO something
        singleParameter(p1);
    }

    public void singleParameterDifferentClass(String p1) {
        First.singleParameter(p1);
    }

    public void singleParameterDifferentPackage(String p1) {
        Second.singleParameter(p1);
    }

    public void twoParametersDifferentPackage(String p1, String p2) {
        Second.twoParameters(p1, p2);
    }

    @MyAnnotation
    public void singleParameterDifferentPackageAnnotation(@MyAnnotation String p1) {
        Second.singleParameter(p1);
    }
}
