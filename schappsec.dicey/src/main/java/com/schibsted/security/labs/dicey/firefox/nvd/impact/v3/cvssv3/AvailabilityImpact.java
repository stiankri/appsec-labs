package com.schibsted.security.labs.dicey.firefox.nvd.impact.v3.cvssv3;

import java.util.HashMap;
import java.util.Map;

public enum AvailabilityImpact {
  HIGH("H"),
  LOW("L"),
  NONE("N");

  private final static Map<String, AvailabilityImpact> valueMap = new HashMap<>();

  static {
    for (var v : AvailabilityImpact.values()) {
      valueMap.put(v.value, v);
    }
  }

  private final String value;

  AvailabilityImpact(String value) {
    this.value = value;
  }

  public static AvailabilityImpact fromValue(String value) {
    var result = valueMap.get(value);
    if (result == null) {
      throw new IllegalArgumentException(String.format("No value called '%s'", value));
    }
    return result;
  }
}
