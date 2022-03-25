package com.schibsted.security.labs.jack.info.packagemanager;

import com.schibsted.security.labs.jack.info.proc.Cmdline;
import com.schibsted.security.labs.jack.readers.process.ProcessReader;

import java.util.List;
import java.util.Optional;

public class PackageManager {
  public PackageType packageType;
  public String packageName;

  PackageManager(PackageType packageType, String packageName) {
    this.packageType = packageType;
    this.packageName = packageName;
  }

  public static Optional<PackageManager> infer(Cmdline cmdline) {
    String[] parts = cmdline.cmdline.split(" ");
    var program = parts[0];

    if (program.startsWith("/snap/")) {
      String[] snapParts = program.split("/");
      return Optional.of(new PackageManager(PackageType.SNAP, snapParts[2]));
    }

    var apt = apt(program);
    if (apt.isPresent()) {
      return Optional.of(new PackageManager(PackageType.DPKG, apt.get()));
    }

    return Optional.empty();
  }

  private static Optional<String> apt(String command) {
    List<String> result = ProcessReader.run("/usr/bin/dpkg", "-S", command);

    // FIXME make more strict
    if (result.size() == 1) {
      String[] parts = result.get(0).split(": ");
      return Optional.of(parts[0]);
    } else {
      return Optional.empty();
    }
  }
}
