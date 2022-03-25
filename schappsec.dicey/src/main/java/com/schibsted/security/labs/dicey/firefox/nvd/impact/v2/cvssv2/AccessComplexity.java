package com.schibsted.security.labs.dicey.firefox.nvd.impact.v2.cvssv2;

import java.util.HashMap;
import java.util.Map;

public enum AccessComplexity {
  HIGH("H"),
  MEDIUM("M"),
  LOW("L");

  private final static Map<String, AccessComplexity> valueMap = new HashMap<>();

  static {
    for (var v : AccessComplexity.values()) {
      valueMap.put(v.value, v);
    }
  }

  private final String value;

  AccessComplexity(String value) {
    this.value = value;
  }

  public static AccessComplexity fromValue(String value) {
    var result = valueMap.get(value);
    if (result == null) {
      throw new IllegalArgumentException(String.format("No value called '%s'", value));
    }
    return result;
  }
}

