package com.schibsted.security.labs.analysis.gui;

import javafx.scene.paint.Color;

// FIXME
public enum ColorPalette {
  BACKGROUND(25, 25, 25),
  HIGHLIGHTED_LINE(30, 30, 30),
  HIGHLIGHTED_SYMBOL(35, 35, 35),
  COLLAPSE_LINE(30, 30, 30),
  LINE_NUMBER(40, 40, 40),
  LINE_SEPARATOR(34, 34, 34),
  SIDEBAR(20, 20, 20),
  MAIN(70, 70, 70), // class, method, package name, import name, method invocation, etc.
  GUI_INFO(75, 75, 75),
  GUI_BACKGROUND(25, 25, 25),
  UNUSED(50, 50, 50), // class, method
  USED(100, 50, 50), // class, method
  KEYWORD(80, 80, 30), // and separator, var, etc
  NUMBER(50, 50, 80),
  STRING(50, 80, 50),
  FIELD(60, 60, 60),
  ANNOTATION(70, 70, 0),
  JAVADOC(50, 80, 50),
  COMMENT(50, 80, 50),
  TODO(50, 80, 50),
  HYPERLINK(50, 50, 100);
  // more javadoc colors
  // generics

  private final int red;
  private final int green;
  private final int blue;

  ColorPalette(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  public double red() {
    return red / 100.0;
  }

  public double green() {
    return green / 100.0;
  }

  public double blue() {
    return blue / 100.0;
  }

  public Color color() {
    return Color.color(red(), green(), blue());
  }
}
