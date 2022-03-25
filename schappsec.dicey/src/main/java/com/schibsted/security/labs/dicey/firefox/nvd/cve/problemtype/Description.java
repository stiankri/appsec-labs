package com.schibsted.security.labs.dicey.firefox.nvd.cve.problemtype;

import com.schibsted.security.labs.dicey.firefox.nvd.cve.description.Language;

public class Description {
  private final Language language;
  private final String value;

  public Description(String language, String value) {
    this.language = Language.fromValue(language);
    this.value = value;
  }

  public Language language() {
    return language;
  }

  public String value() {
    return value;
  }
}
