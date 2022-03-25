package com.schibsted.security.labs.analysis.model.basic;

import java.util.StringJoiner;

public record Import(String packageName, String className) {

  @Override
  public String toString() {
    var joiner = new StringJoiner(".");
    if (!packageName.isEmpty()) {
      joiner.add(packageName);
    }
    if (!className.isEmpty()) {
      joiner.add(className);
    }
    return joiner.toString();
  }
}
