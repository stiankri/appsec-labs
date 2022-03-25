package com.schibsted.security.labs.jack;

import java.util.ArrayList;
import java.util.List;

public class ProcessNode {
  public int pid;
  public int parent;
  public List<ProcessNode> children = new ArrayList<>();

  public ProcessNode(int pid) {
    this.pid = pid;
  }

  @Override
  public String toString() {
    return Integer.toString(pid);
  }
}
