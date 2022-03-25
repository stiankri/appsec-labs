package com.schibsted.security.labs.dicey.firefox.nvd.cve.problemtype;

import java.util.List;

public class ProblemType {
  private final List<ProblemTypeEntry> problemTypes;

  public ProblemType(List<ProblemTypeEntry> problemTypeEntries) {
    this.problemTypes = problemTypeEntries;
  }

  public List<ProblemTypeEntry> problemTypes() {
    return problemTypes;
  }
}
