package com.schibsted.security.labs.jack.info.proc;

import com.schibsted.security.labs.jack.readers.file.FileReader;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public class Ns {
  public final List<String> namespaces;

  public Ns(List<String> namespaces) {
    this.namespaces = namespaces;
  }

  private static String getRawNamespace(int processId, String name) throws AccessDeniedException {
    return FileReader.readSymbolicLink("/proc/" + processId + "/ns/" + name);
  }

  public static Optional<Ns> read(int process) {
    try {
      var cgroup = getRawNamespace(process, "cgroup");
      var ipc = getRawNamespace(process, "ipc");
      var mnt = getRawNamespace(process, "mnt");
      var net = getRawNamespace(process, "net");
      var pid = getRawNamespace(process, "pid");
      var pid_for_children = getRawNamespace(process, "pid_for_children");
      var user = getRawNamespace(process, "user");
      var uts = getRawNamespace(process, "uts");

      var all = List.of(cgroup, ipc, mnt, net, pid, pid_for_children, user, uts);

      return Optional.of(new Ns(all));
    } catch (AccessDeniedException e) {
      return Optional.empty();
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (var ns : namespaces) {
      sb.append("  ");
      sb.append(ns);
      sb.append("\n");
    }

    return sb.toString();
  }
}
