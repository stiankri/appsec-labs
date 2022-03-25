package com.schibsted.security.labs.dicey.firefox.nvd.impact.v3.cvssv3;

import java.util.HashMap;
import java.util.Map;

public enum IntegrityImpact {
  HIGH("H"),
  LOW("L"),
  NONE("N");

  private final static Map<String, IntegrityImpact> valueMap = new HashMap<>();

  static {
    for (var v : IntegrityImpact.values()) {
      valueMap.put(v.value, v);
    }
  }

  private final String value;

  IntegrityImpact(String value) {
    this.value = value;
  }

  public static IntegrityImpact fromValue(String value) {
    var result = valueMap.get(value);
    if (result == null) {
      throw new IllegalArgumentException(String.format("No value called '%s'", value));
    }
    return result;
  }
}
