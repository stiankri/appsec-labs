package com.schibsted.security.labs.jack.info.proc;

import com.schibsted.security.labs.jack.readers.file.FileReader;

public class Attr {
  public final String current;

  public Attr(String current) {
    this.current = current;
  }

  public static Attr read(int processId) {
    var current = FileReader.readFileAsString("/proc/" + processId + "/attr/current");
    return new Attr(current);
  }
}
