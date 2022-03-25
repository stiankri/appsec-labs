package com.schibsted.security.labs.analysis.model.basic;

public record TokenIndex(int line, int column) implements Comparable<TokenIndex> {

  public TokenIndex {
    if (line < 1) {
      throw new IllegalArgumentException(String.format("Line must be positive, got '%s'", line));
    }
    if (column < 1) {
      throw new IllegalArgumentException(String.format("Column must be positive, got '%s'", line));
    }
  }

  @Override
  public int compareTo(TokenIndex o) {
    if (line == o.line) {
      return Integer.compare(column, o.column);
    } else {
      return Integer.compare(line, o.line);
    }
  }
}
