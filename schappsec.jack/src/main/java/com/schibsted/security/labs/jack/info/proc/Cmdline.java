package com.schibsted.security.labs.jack.info.proc;

import com.schibsted.security.labs.jack.readers.file.FileReader;

public class Cmdline {
  public final String cmdline;

  public Cmdline(String cmdline) {
    this.cmdline = cmdline;
  }

  public static Cmdline read(int processId) {
    var cmdline = FileReader.readFileAsString("/proc/" + processId + "/cmdline");

    // FIXME why are there sometimes zeros?
    cmdline = cmdline.replace('\0', ' ');

    return new Cmdline(cmdline);
  }
}
