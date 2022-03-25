package com.schibsted.security.labs.analysis.gui.elements;

import com.schibsted.security.labs.analysis.gui.ColorPalette;

public class RestrictedIdentifierElement extends VisualElement {
  public RestrictedIdentifierElement(int lineNumber, int positionInLine, String text) {
    super(lineNumber, positionInLine, text, ColorPalette.KEYWORD);
  }
}
