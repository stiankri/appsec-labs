package com.schibsted.security.labs.analysis.testingground;

import com.schibsted.security.labs.testingground.basic.External;
import com.schibsted.security.labs.testingground.basic.packageone.ExternalOne;

public class TestClass {
    public void singleParameter(String p1) {

    }

    public void singleParameterSameClass(String p1) {
        singleParameter(p1);
    }

    public void singleParameterDifferentClass(String p1) {
        External.singleParameter(p1);
    }

    public void singleParameterDifferentPackage(String p1) {
        ExternalOne.singeParameter(p1);
    }

    public void twoParameters(String p1, String p2) {
        External.twoParameters(p1, p2);
    }

}
