package com.schibsted.security.labs.dicey.firefox.nvd.impact.v3.cvssv3;

import java.util.HashMap;
import java.util.Map;

public enum ConfidentialityImpact {
  HIGH("H"),
  LOW("L"),
  NONE("N");

  private final static Map<String, ConfidentialityImpact> valueMap = new HashMap<>();

  static {
    for (var v : ConfidentialityImpact.values()) {
      valueMap.put(v.value, v);
    }
  }

  private final String value;

  ConfidentialityImpact(String value) {
    this.value = value;
  }

  public static ConfidentialityImpact fromValue(String value) {
    var result = valueMap.get(value);
    if (result == null) {
      throw new IllegalArgumentException(String.format("No value called '%s'", value));
    }
    return result;
  }
}
