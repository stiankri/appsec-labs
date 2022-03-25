package com.schibsted.security.labs.analysis.language;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum JavaReservedLiteral {
  TRUE("true"),
  FALSE("false"),
  NULL("null");

  private static final Map<String, JavaReservedLiteral> stringMap = new HashMap<>();

  static {
    for (var literal : JavaReservedLiteral.values()) {
      stringMap.put(literal.toString(), literal);
    }
  }

  private final String literal;

  JavaReservedLiteral(String literal) {
    this.literal = literal;
  }

  @Override
  public String toString() {
    return literal;
  }

  public static Optional<JavaReservedLiteral> fromString(String literal) {
    return Optional.ofNullable(stringMap.get(literal));
  }

  public static boolean isJavaReservedLiteral(String value) {
    return stringMap.get(value) != null;
  }
}
