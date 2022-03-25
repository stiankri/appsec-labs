package com.schibsted.security.labs.dicey.firefox.nvd.impact.v3.cvssv3;

import java.util.HashMap;
import java.util.Map;

public enum AttackVector {
  NETWORK("N"),
  ADJACENT("A"),
  LOCAL("L"),
  PHYSICAL("P");

  private final static Map<String, AttackVector> valueMap = new HashMap<>();

  static {
    for (var v : AttackVector.values()) {
      valueMap.put(v.value, v);
    }
  }

  private final String value;

  AttackVector(String value) {
    this.value = value;
  }

  public static AttackVector fromValue(String value) {
    var result = valueMap.get(value);
    if (result == null) {
      throw new IllegalArgumentException(String.format("No value called '%s'", value));
    }
    return result;
  }
}

