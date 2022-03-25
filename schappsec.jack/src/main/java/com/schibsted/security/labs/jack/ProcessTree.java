package com.schibsted.security.labs.jack;

import com.schibsted.security.labs.jack.info.proc.Stat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProcessTree {

  public static void print(ProcessNode root) {
    root.children.sort(Comparator.comparingInt(a -> a.pid));

    for (var child : root.children) {
      print(child);
    }
  }

  public static ProcessNode getTree() {

    try (var files = Files.walk(Paths.get("/proc/"), 1)) {

      Map<Integer, ProcessNode> processes = files.map(f -> f.getFileName().toString())
          .filter(f -> Character.isDigit(f.charAt(0)))
          .map(f -> new ProcessNode(Integer.parseInt(f)))
          .collect(Collectors.toMap(f -> f.pid, Function.identity()));

      // FIXME will break if process tree changes during read
      for (var process : processes.values()) {
        var stat = Stat.read(process.pid);
        process.parent = stat.parentPid;

        if (process.parent != 0) {
          processes.get(process.parent).children.add(process);
        }
      }

      return processes.get(1);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    var root = ProcessTree.getTree();
    print(root);
  }
}
