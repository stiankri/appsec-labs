package com.schibsted.security.labs.testingground.basic;

import com.schibsted.security.labs.testingground.basic.packageone.ExternalOne;
import com.schibsted.security.labs.testingground.MyAnnotation;

/**
 * javadoc goes here
 */
public class HelloWorld {
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

  /**
   * more javadoc
   *
   * @param p1 input
   */
  public void singleParameterSameClass(String p1) {
    // TODO something
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

  public void singleParameterNested(String p1) {
    External.singleParameter(External.nested(p1));
  }

  @MyAnnotation
  public void singleParameterAnnotation(@MyAnnotation String p1) {
    External.singleParameter(p1);
  }

  public static void main(String[] args) {
    System.out.println(args[0]);
  }
}
