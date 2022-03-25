package com.schibsted.security.labs.dicey.firefox.nvd.impact.v3.cvssv3;

import java.util.HashMap;
import java.util.Map;

public enum UserInteraction {
  NONE("N"),
  REQUIRED("R");

  private final static Map<String, UserInteraction> valueMap = new HashMap<>();

  static {
    for (var v : UserInteraction.values()) {
      valueMap.put(v.value, v);
    }
  }

  private final String value;

  UserInteraction(String value) {
    this.value = value;
  }

  public static UserInteraction fromValue(String value) {
    var result = valueMap.get(value);
    if (result == null) {
      throw new IllegalArgumentException(String.format("No value called '%s'", value));
    }
    return result;
  }
}
