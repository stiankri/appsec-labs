package com.schibsted.security.labs.jack.info.proc;

import com.schibsted.security.labs.jack.readers.file.FileReader;

public class Stat {
  public final int parentPid;

  public Stat(int parentPid) {
    this.parentPid = parentPid;
  }

  public static Stat read(int processId) {
    var raw = FileReader.readFileAsString("/proc/" + processId + "/stat");
    var sub = raw.substring(raw.lastIndexOf(")") + 2);
    String[] parts = sub.split(" ");

    return new Stat(Integer.parseInt(parts[1]));
  }
}
