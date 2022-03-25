package com.schibsted.security.labs.jack.types;

import java.util.HashMap;
import java.util.Map;

public enum State {
  RUNNING("R (running)"),
  SLEEPING("S (sleeping)"),
  DISK_SLEEP("D (disk sleep)"),
  STOPPED("T (stopped)"),
  TRACING_STOP("T (tracing stop)"),
  ZOMBIE("Z (zombie)"),
  DEAD("X (dead)");

  private String name;
  private static Map<String, State> stringMap = new HashMap<>();

  static {
    for (var state : State.values()) {
      stringMap.put(state.name, state);
    }
  }

  State(String name) {
    this.name = name;
  }

  public static State fromString(String name) {
    return stringMap.get(name);
  }
}
