package com.schibsted.security.labs.jack.types;

import java.util.HashMap;
import java.util.Map;

public enum Lsm {
  CAPABILITY,
  YAMA,
  APPARMOR,
  SELINUX;

  private static Map<String, Lsm> stringMap = new HashMap<>();

  static {
    for (var lsm : Lsm.values()) {
      stringMap.put(lsm.name().toLowerCase(), lsm);
    }
  }

  public static Lsm fromString(String name) {
    return stringMap.get(name);
  }
}
