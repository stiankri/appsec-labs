package com.schibsted.security.labs.analysis.model.basic;

import java.util.List;
import java.util.Optional;

public record Method(String name, List<Parameter>parameters, TokenIndex tokenIndex) {

  public boolean hasParameter(String name) {
    return parameters.stream()
        .anyMatch(p -> p.name().equals(name));
  }

  public Optional<Parameter> getParameter(String name) {
    return parameters.stream()
        .filter(p -> p.name().equals(name)).findFirst();
  }
}
