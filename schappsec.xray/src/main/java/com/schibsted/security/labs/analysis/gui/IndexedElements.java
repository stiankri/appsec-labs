package com.schibsted.security.labs.analysis.gui;

import com.schibsted.security.labs.analysis.gui.elements.MultiLineCommentElement;
import com.schibsted.security.labs.analysis.gui.elements.VisualElement;
import com.schibsted.security.labs.analysis.model.basic.TokenIndex;

import java.util.List;
import java.util.Optional;

public class IndexedElements {
  private final VisualElement[][] grid;
  private final int maxLine;
  private final int maxColumn;
  private final List<VisualElement> elements;

  public IndexedElements(List<VisualElement> elements, int maxLine, int maxColumn) {
    this.maxLine = maxLine;
    this.maxColumn = maxColumn;
    this.elements = elements;

    grid = new VisualElement[maxLine + 1][maxColumn + 1];

    for (var element : elements) {
      var line = element.line();
      var column = element.column();

      if (!(element instanceof MultiLineCommentElement)) {
        for (int i = 0; i < element.text().length(); i++) {
          grid[line][column + i] = element;
        }
      }
    }
  }

  List<VisualElement> elements() {
    return elements;
  }

  Optional<VisualElement> getElement(TokenIndex tokenIndex) {
    var line = tokenIndex.line();
    var column = tokenIndex.column();

    if (line >= 1 && line <= maxLine && column >= 1 && column <= maxColumn) {
      return Optional.ofNullable(grid[line][column]);
    } else {
      return Optional.empty();
    }
  }
}
