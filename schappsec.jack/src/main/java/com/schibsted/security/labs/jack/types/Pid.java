package com.schibsted.security.labs.jack.types;

public class Pid {
  public int pid;

  public Pid(int pid) {
    if (pid < 0 || pid > 2 << 16) {
      throw new RuntimeException("Invalid pid + " + pid);
    }

    this.pid = pid;
  }

  @Override
  public String toString() {
    return Integer.toString(pid);
  }
}
