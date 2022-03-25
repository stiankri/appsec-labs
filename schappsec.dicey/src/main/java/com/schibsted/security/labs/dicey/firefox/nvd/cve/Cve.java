package com.schibsted.security.labs.dicey.firefox.nvd.cve;

import com.schibsted.security.labs.dicey.firefox.nvd.cve.description.Description;
import com.schibsted.security.labs.dicey.firefox.nvd.cve.problemtype.ProblemType;

public class Cve {
  private final String id;
  private final ProblemType problemType;
  private final Description description;

  public Cve(String id,
             ProblemType problemType,
             Description description) {
    this.id = id;
    this.problemType = problemType;
    this.description = description;
  }

  public String id() {
    return id;
  }

  public Description description() {
    return description;
  }
}
