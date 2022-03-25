package com.schibsted.security.labs.analysis.gui;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class GuiConfig {
  public static final int FONT_SIZE = 17;
  public static final String FONT_FAMILY = "DejaVu Sans Mono";

  public static final double charWidth = 10.2;
  public static final double charHeight = 25.3;
  public static final double charHeightDiff = 8;

  public static String createLineNumberString(int line) {
    return String.format("%" + 4 + "s  ", line);
  }

  public static Font font() {
    return Font.font(FONT_FAMILY, FontPosture.REGULAR, FONT_SIZE);
  }

  public static Font fontUnderline() {
    return Font.font(FONT_FAMILY, FontPosture.REGULAR, FONT_SIZE);
  }
}
