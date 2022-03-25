package com.schibsted.security.labs.analysis.gui;

import javafx.scene.canvas.Canvas;

import java.util.List;

public class DataFlowView extends Canvas {
  private List<String> usages;
  private double width;
  private double height;

  public DataFlowView(double width, double height, List<String> usages) {
    super(width, height);
    this.width = width;
    this.height = height;
    this.usages = usages;

    this.setFocusTraversable(true);

    draw();
  }

  private void draw() {
    var gc = this.getGraphicsContext2D();
    gc.setFont(GuiConfig.font());
    gc.setFill(ColorPalette.GUI_BACKGROUND.color());
    gc.fillRect(0, 0, width, height);

    var line = 1;
    for (var usage : usages) {
      gc.setFill(ColorPalette.GUI_INFO.color());
      gc.fillText(usage, 10 + GuiConfig.charWidth * 2 * line, line * GuiConfig.charHeight - GuiConfig.charHeightDiff);

      line++;
    }
  }
}
