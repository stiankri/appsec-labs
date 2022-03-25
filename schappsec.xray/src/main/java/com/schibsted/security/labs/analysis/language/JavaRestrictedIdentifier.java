package com.schibsted.security.labs.analysis.language;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum JavaRestrictedIdentifier {
  VAR("var"),
  YIELD("yield");

  private static final Map<String, JavaRestrictedIdentifier> stringMap = new HashMap<>();

  static {
    for (var literal : JavaRestrictedIdentifier.values()) {
      stringMap.put(literal.toString(), literal);
    }
  }

  private final String literal;

  JavaRestrictedIdentifier(String literal) {
    this.literal = literal;
  }

  @Override
  public String toString() {
    return literal;
  }

  public static Optional<JavaRestrictedIdentifier> fromString(String literal) {
    return Optional.ofNullable(stringMap.get(literal));
  }
}
