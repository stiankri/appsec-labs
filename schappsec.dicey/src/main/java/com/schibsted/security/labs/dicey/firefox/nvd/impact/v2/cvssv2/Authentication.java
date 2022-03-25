package com.schibsted.security.labs.dicey.firefox.nvd.impact.v2.cvssv2;

import java.util.HashMap;
import java.util.Map;

public enum Authentication {
  MULTIPLE("M"),
  SINGLE("S"),
  NONE("N");

  private final static Map<String, Authentication> valueMap = new HashMap<>();

  static {
    for (var v : Authentication.values()) {
      valueMap.put(v.value, v);
    }
  }

  private final String value;

  Authentication(String value) {
    this.value = value;
  }

  public static Authentication fromValue(String value) {
    var result = valueMap.get(value);
    if (result == null) {
      throw new IllegalArgumentException(String.format("No value called '%s'", value));
    }
    return result;
  }
}

