package com.schibsted.security.labs.dicey.firefox.nvd.cve.description;

import java.util.List;

public class Description {
  private final List<DescriptionEntry> descriptions;

  public Description(List<DescriptionEntry> descriptions) {
    this.descriptions = descriptions;
  }

  public List<DescriptionEntry> descriptions() {
    return descriptions;
  }
}
