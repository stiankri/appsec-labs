package com.schibsted.security.labs.jack.types;

import java.util.HashMap;
import java.util.Map;

public enum Seccomp {
  SECCOMP_MODE_DISABLED(0),
  SECCOMP_MODE_STRICT(1),
  SECCOMP_MODE_FILTER(2);

  private int index;
  private static Map<Integer, Seccomp> intMap = new HashMap<>();

  static {
    for (var type : Seccomp.values()) {
      intMap.put(type.index, type);
    }
  }

  Seccomp(int index) {
    this.index = index;
  }

  public static Seccomp fromInt(int index) {
    return intMap.get(index);
  }
}
