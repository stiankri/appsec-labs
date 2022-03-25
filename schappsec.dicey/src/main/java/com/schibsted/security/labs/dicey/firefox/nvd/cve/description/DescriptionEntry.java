package com.schibsted.security.labs.dicey.firefox.nvd.cve.description;

public class DescriptionEntry {
  private final Language language;
  private final String description;

  public DescriptionEntry(String language, String description) {
    this.language = Language.fromValue(language);
    this.description = description;
  }

  public Language language() {
    return language;
  }

  public String description() {
    return description;
  }
}
