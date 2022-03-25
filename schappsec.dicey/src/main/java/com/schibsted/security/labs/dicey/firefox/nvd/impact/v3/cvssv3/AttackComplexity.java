package com.schibsted.security.labs.dicey.firefox.nvd.impact.v3.cvssv3;

import java.util.HashMap;
import java.util.Map;

public enum AttackComplexity {
  LOW("L"),
  HIGH("H");

  private final static Map<String, AttackComplexity> valueMap = new HashMap<>();

  static {
    for (var v : AttackComplexity.values()) {
      valueMap.put(v.value, v);
    }
  }

  private final String value;

  AttackComplexity(String value) {
    this.value = value;
  }

  public static AttackComplexity fromValue(String value) {
    var result = valueMap.get(value);
    if (result == null) {
      throw new IllegalArgumentException(String.format("No value called '%s'", value));
    }
    return result;
  }
}
