package com.schibsted.security.labs.analysis.gui.elements;

import com.schibsted.security.labs.analysis.gui.ColorPalette;

public class MethodDeclarationElement extends VisualElement {

  public MethodDeclarationElement(int lineNumber, int positionInLine, String text) {
    super(lineNumber, positionInLine, text, ColorPalette.USED);
  }
}
