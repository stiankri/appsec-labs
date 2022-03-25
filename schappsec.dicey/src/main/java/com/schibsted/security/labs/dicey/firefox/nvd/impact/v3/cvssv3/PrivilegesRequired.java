package com.schibsted.security.labs.dicey.firefox.nvd.impact.v3.cvssv3;

import java.util.HashMap;
import java.util.Map;

public enum PrivilegesRequired {
  NONE("N"),
  LOW("L"),
  HIGH("H");

  private final static Map<String, PrivilegesRequired> valueMap = new HashMap<>();

  static {
    for (var v : PrivilegesRequired.values()) {
      valueMap.put(v.value, v);
    }
  }

  private final String value;

  PrivilegesRequired(String value) {
    this.value = value;
  }

  public static PrivilegesRequired fromValue(String value) {
    var result = valueMap.get(value);
    if (result == null) {
      throw new IllegalArgumentException(String.format("No value called '%s'", value));
    }
    return result;
  }
}
