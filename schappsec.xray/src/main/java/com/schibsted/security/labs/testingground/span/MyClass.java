package com.schibsted.security.labs.testingground.span;

import com.schibsted.security.labs.testingground.MyAnnotation;

import java.util.List;
import java.util.function.Function;

@MyAnnotation
public final class MyClass<T2> extends Object implements MyInterface, Comparable<T2> {
  public static String CONSTANT = "foo";
  protected String foo;
  private final String foz;

  /**
   * Documentation
   *
   * @param foz input
   */
  public MyClass(String foz) {
    this.foz = foz;
    var intLiteral = 2;
    var longLiteral = 2L;
    var doubleLiteral = 2.0;
    var booleanLiteral = true;
    var stringLiteral = "foo";
    var characterLiteral = 'a';
    var list = List.of(1, 2, 3);

    Function<String, String> foo = (var a) -> a.toUpperCase() + "foo";

    var myAnonymousClass = new MyInterface() {
      @Override
      public void doAction() {

      }
    };

    for (int i = 0; i < 10; i++) {
      System.out.println(i);
    }

    for (var element : list) {
      System.out.println(element);
    }

    var myEnum = MyEnum.BAR;

    switch (myEnum) {
      case BAR:
        System.out.println("bar");
        break;
      case FOO:
        System.out.println("foo");
        break;
    }

    var switchExpression = switch (myEnum) {
      case BAR -> 1;
      case FOO -> 2;
    };
  }

  @MyAnnotation
  private void privateMethod(@MyAnnotation T2 variable) {

  }

  public <T> void genericMethod(T variable) {

  }

  @Override
  public void doAction() {

  }

  @Override
  public int compareTo(T2 o) {
    return 0;
  }

  public void methodWithClass() {
    class ClassInMethod {

    }
  }

  public static class MyStaticSubclass {

  }

  public class MySubclass {

  }

  public static void main(String[] args) {

  }

}
