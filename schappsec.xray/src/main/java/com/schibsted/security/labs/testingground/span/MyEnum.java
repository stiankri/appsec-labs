package com.schibsted.security.labs.testingground.span;

public enum MyEnum {
  FOO("foo"),
  BAR("bar");

  String value;

  MyEnum(String value) {
    this.value = value;
  }
}
