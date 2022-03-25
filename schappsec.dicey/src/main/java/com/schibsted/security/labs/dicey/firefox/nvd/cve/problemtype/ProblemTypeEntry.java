package com.schibsted.security.labs.dicey.firefox.nvd.cve.problemtype;

import java.util.List;

public class ProblemTypeEntry {
  private final List<Description> descriptions;

  public ProblemTypeEntry(List<Description> descriptions) {
    this.descriptions = descriptions;
  }

  public List<Description> descriptions() {
    return descriptions;
  }
}
