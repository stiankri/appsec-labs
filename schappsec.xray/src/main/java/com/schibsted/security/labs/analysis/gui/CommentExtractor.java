package com.schibsted.security.labs.analysis.gui;

import com.schibsted.security.labs.analysis.gui.elements.LineCommentElement;
import com.schibsted.security.labs.analysis.gui.elements.MultiLineCommentElement;
import com.schibsted.security.labs.analysis.gui.elements.TodoElement;
import com.schibsted.security.labs.analysis.gui.elements.VisualElement;
import org.antlr.v4.grammars.Java9Lexer;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;
import java.util.List;

public class CommentExtractor {
  public static List<VisualElement> extract(CommonTokenStream tokens) {
    List<VisualElement> result = new ArrayList<>();

    for (int i = 0; i < tokens.getNumberOfOnChannelTokens(); i++) {
      var token = tokens.get(i);
      var text = token.getText();
      var lineNumber = token.getLine();
      var column = token.getCharPositionInLine() + 1;

      if (token.getType() == Java9Lexer.COMMENT) {
        result.add(new MultiLineCommentElement(lineNumber, column, text));
      } else if (token.getType() == Java9Lexer.LINE_COMMENT) {
        result.addAll(extractLineComment(lineNumber, column, text));
      }
    }

    return result;
  }

  private static List<VisualElement> extractLineComment(int lineNumber, int column, String text) {
    var todoPosition = text.toLowerCase().indexOf("todo");
    var fixmePosition = text.toLowerCase().indexOf("fixme");

    int position;
    if (todoPosition != -1 && fixmePosition != -1) {
      position = Integer.min(todoPosition, fixmePosition);
    } else {
      position = Integer.max(todoPosition, fixmePosition);
    }

    if (position != -1) {
      var first = text.substring(0, position);
      var second = text.substring(position);

      return List.of(new LineCommentElement(lineNumber, column, first),
          new TodoElement(lineNumber, column + position, second));
    } else {
      return List.of(new LineCommentElement(lineNumber, column, text));
    }
  }
}
