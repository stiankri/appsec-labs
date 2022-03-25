package com.schibsted.security.labs.dicey.firefox.nvd.impact.v3.cvssv3;

import java.util.HashMap;
import java.util.Map;

public enum Scope {
  UNCHANGED("U"),
  CHANGED("C");

  private final static Map<String, Scope> valueMap = new HashMap<>();

  static {
    for (var s : Scope.values()) {
      valueMap.put(s.value, s);
    }
  }

  private final String value;

  Scope(String value) {
    this.value = value;
  }

  public static Scope fromValue(String value) {
    var result = valueMap.get(value);
    if (result == null) {
      throw new IllegalArgumentException(String.format("No value called '%s'", value));
    }
    return result;
  }
}
