package com.schibsted.security.labs.analysis.gui.elements;

import com.schibsted.security.labs.analysis.gui.ColorPalette;

public class ReservedLiteralElement extends VisualElement {
  public ReservedLiteralElement(int lineNumber, int positionInLine, String text) {
    super(lineNumber, positionInLine, text, ColorPalette.KEYWORD);
  }
}
