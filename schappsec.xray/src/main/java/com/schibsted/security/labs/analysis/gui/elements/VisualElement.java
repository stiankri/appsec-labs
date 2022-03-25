package com.schibsted.security.labs.analysis.gui.elements;

import com.schibsted.security.labs.analysis.model.basic.TokenIndex;
import javafx.scene.paint.Color;
import com.schibsted.security.labs.analysis.gui.ColorPalette;

public abstract class VisualElement implements Comparable<VisualElement> {
  protected final String text;
  private final ColorPalette color;
  private final int lineNumber;
  private final int positionInLine;

  public VisualElement(int lineNumber, int positionInLine, String text, ColorPalette color) {
    this.lineNumber = lineNumber;
    this.positionInLine = positionInLine;
    this.text = text;
    this.color = color;
  }

  public String text() {
    return text;
  }

  public Color color() {
    return color.color();
  }

  public int line() {
    return lineNumber;
  }

  public int column() {
    return positionInLine;
  }

  public TokenIndex tokenIndex() {
    return new TokenIndex(lineNumber, positionInLine);
  }

  public int compareTo(VisualElement other) {
    if (lineNumber == other.lineNumber) {
      return Integer.compare(positionInLine, other.positionInLine);
    } else {
      return Integer.compare(lineNumber, other.lineNumber);
    }
  }
}
