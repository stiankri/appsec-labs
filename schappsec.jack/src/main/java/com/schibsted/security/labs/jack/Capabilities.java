package com.schibsted.security.labs.jack;

import com.schibsted.security.labs.jack.types.Capability;

import java.util.HashSet;
import java.util.Set;

public class Capabilities {
  private Set<Capability> capabilities = new HashSet<>();

  public Capabilities(Set<Capability> capabilities) {
    this.capabilities = capabilities;
  }

  public Set<Capability> getCapabilities() {
    return capabilities;
  }

  public boolean hasNone() {
    return this.capabilities.size() == 0;
  }

  public boolean hasAll() {
    return this.capabilities.size() == Capability.values().length;
  }

  public static Capabilities fromBitMask(String bitmask) {
    long mask = Long.parseLong(bitmask, 16);

    Set<Capability> capabilities = new HashSet<>();

    for (var capability : Capability.values()) {
      long bit = 1L << capability.getValue();
      if ((bit & mask) > 0) {
        capabilities.add(capability);
      }
    }

    return new Capabilities(capabilities);
  }
}
