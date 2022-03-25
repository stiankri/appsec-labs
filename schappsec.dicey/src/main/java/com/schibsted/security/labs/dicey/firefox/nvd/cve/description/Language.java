package com.schibsted.security.labs.dicey.firefox.nvd.cve.description;

import java.util.HashMap;
import java.util.Map;

public enum Language {
  ENGLISH("en");

  private final static Map<String, Language> valueMap = new HashMap<>();

  static {
    for (var v : Language.values()) {
      valueMap.put(v.value, v);
    }
  }

  private final String value;

  Language(String value) {
    this.value = value;
  }

  public static Language fromValue(String value) {
    var result = valueMap.get(value);
    if (result == null) {
      throw new IllegalArgumentException(String.format("No value called '%s'", value));
    }
    return result;
  }
}
