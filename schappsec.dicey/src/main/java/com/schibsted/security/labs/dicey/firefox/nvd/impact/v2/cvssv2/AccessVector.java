package com.schibsted.security.labs.dicey.firefox.nvd.impact.v2.cvssv2;

import java.util.HashMap;
import java.util.Map;

public enum AccessVector {
  NETWORK("N"),
  ADJACENT("A"),
  LOCAL("L"),
  PHYSICAL("P");

  private final static Map<String, AccessVector> valueMap = new HashMap<>();

  static {
    for (var v : AccessVector.values()) {
      valueMap.put(v.value, v);
    }
  }

  private final String value;

  AccessVector(String value) {
    this.value = value;
  }

  public static AccessVector fromValue(String value) {
    var result = valueMap.get(value);
    if (result == null) {
      throw new IllegalArgumentException(String.format("No value called '%s'", value));
    }
    return result;
  }
}
