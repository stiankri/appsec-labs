package com.schibsted.security.labs.analysis.parser;

import com.schibsted.security.labs.analysis.model.basic.TokenIndex;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.Optional;

public class TreeUtils {
  public static Optional<TerminalNode> extractTerminalNode(ParseTree node) {
    if (node instanceof TerminalNodeImpl c) {
      var line = c.symbol.getLine();
      var column = c.symbol.getCharPositionInLine() + 1;
      var text = c.getText();
      return Optional.of(new TerminalNode(text, new TokenIndex(line, column)));
    }

    for (int i = 0; i < node.getChildCount(); i++) {
      var child = node.getChild(i);

      var result = extractTerminalNode(child);
      if (result.isPresent()) {
        return result;
      }
    }
    return Optional.empty();
  }

  public static String extractDotSeparatedName(ParseTree node) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < node.getChildCount(); i++) {
      var child = node.getChild(i);
      var result = extractTerminalNode(child);

      result.ifPresent(t -> sb.append(t.value()));
    }
    return sb.toString();
  }
}
