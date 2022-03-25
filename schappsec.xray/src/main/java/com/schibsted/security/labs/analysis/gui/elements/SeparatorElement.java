package com.schibsted.security.labs.analysis.gui.elements;

import com.schibsted.security.labs.analysis.gui.ColorPalette;

public class SeparatorElement extends VisualElement {
  public SeparatorElement(int lineNumber, int positionInLine, String text) {
    super(lineNumber, positionInLine, text, ColorPalette.KEYWORD);
  }
}
