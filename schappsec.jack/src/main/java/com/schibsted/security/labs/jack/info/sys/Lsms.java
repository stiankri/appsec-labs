package com.schibsted.security.labs.jack.info.sys;

import com.schibsted.security.labs.jack.readers.file.FileReader;
import com.schibsted.security.labs.jack.types.Lsm;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Lsms {
  public final Set<Lsm> lsms;

  public Lsms(Set<Lsm> lsms) {
    this.lsms = Collections.unmodifiableSet(lsms);
  }

  public static Lsms read() {
    var rawLsms = FileReader.readFileAsString("/sys/kernel/security/lsm");
    String[] parts = rawLsms.split(",");

    Set<Lsm> lsms = new HashSet<>();
    for (var lsm : parts) {
      lsms.add(Lsm.fromString(lsm));
    }

    return new Lsms(lsms);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (var lsm : lsms) {
      sb.append(lsm);
      sb.append(", ");
    }

    return sb.toString();
  }
}
