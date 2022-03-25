package com.schibsted.security.labs.analysis.gui.elements;

import com.schibsted.security.labs.analysis.gui.ColorPalette;

public class BasicElement extends VisualElement {
  public BasicElement(int lineNumber, int positionInLine, String text) {
    super(lineNumber, positionInLine, text, ColorPalette.MAIN);
  }
}
