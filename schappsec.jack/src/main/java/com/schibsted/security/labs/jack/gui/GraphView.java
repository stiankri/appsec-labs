package com.schibsted.security.labs.jack.gui;

import com.schibsted.security.labs.jack.ProcessNode;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GraphView extends Canvas {
  double WIDTH = 1280;
  double HEIGHT = 720;
  private final NodeInfoView nodeInfoView;

  double previousScreenX;
  double previousScreenY;

  double screenOffsetX = 0;
  double screenOffsetY = 0;

  double zoom = 1;
  double zoomOffsetX = 0;
  double zoomOffsetY = 0;

  double width;
  double height;

  double scale;
  int widthOfInfoBox;

  ProcessNode root;
  NodeExtent nodeExtent;

  GraphicsContext gc;

  boolean isDragged = false;

  public GraphView(int width, int height, int widthOfInfoBox, NodeInfoView nodeInfoView, ProcessNode root) {
    super(width, height);
    this.width = width;
    this.height = height;
    this.nodeInfoView = nodeInfoView;
    this.widthOfInfoBox = widthOfInfoBox;
    this.root = root;
    nodeExtent = new NodeExtent(root);
    this.setFocusTraversable(true);

    this.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
      this.previousScreenX = event.getX();
      this.previousScreenY = event.getY();
    });

    this.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
      if (!isDragged) {
        var x = transformXToWorldSPace(event.getX());
        var y = transformYToWorldSPace(event.getY());
        var node = nodeExtent.selectNode(x, y);
        if (node.isPresent()) {
          nodeInfoView.update(node.get().pid);
        }
      }
      isDragged = false;
    });

    this.addEventFilter(ScrollEvent.SCROLL, event -> {
      var candidate = event.getDeltaY() / 400.0;
      if (candidate < 0) {
        candidate = Math.max(candidate, -zoom + .04);
      }
      zoom += candidate;
      updateZoomOffset();

      draw();
    });

    this.addEventFilter(MouseEvent.ANY, event -> this.requestFocus());

    this.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
      isDragged = true;
      screenOffsetX += scaleToWorldSpaceX(event.getX() - previousScreenX);
      screenOffsetY += scaleToWorldSpaceY(event.getY() - previousScreenY);
      previousScreenX = event.getX();
      previousScreenY = event.getY();

      draw();
    });

    this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode() == KeyCode.R) {
        resetView();
        draw();
      }
    });

    this.widthProperty().addListener((observable, oldValue, newValue) -> {
      this.width = newValue.floatValue() - this.widthOfInfoBox;
      updateScale();
      updateZoomOffset();
      draw();
    });

    this.heightProperty().addListener((observable, oldValue, newValue) -> {
      this.height = newValue.floatValue();
      updateScale();
      updateZoomOffset();
      draw();
    });

    resetView();
    draw();
  }

  public void resetView() {
    screenOffsetX = .5 - 50 / WIDTH;
    screenOffsetY = .1;
    zoom = 1;
    zoomOffsetX = 0;
    zoomOffsetY = 0;
  }

  private void updateScale() {
    if (this.width / this.height > WIDTH / HEIGHT) {
      scale = WIDTH / HEIGHT * this.height;
    } else {
      scale = this.width;
    }
  }

  private void updateZoomOffset() {
    zoomOffsetX = -scale * (zoom - 1) / 2.0;
    zoomOffsetY = -scale / WIDTH * HEIGHT * (zoom - 1) / 2.0;
  }

  private double transformXToScreenSpace(double x) {
    return (screenOffsetX + x) * scale * zoom + zoomOffsetX;
  }

  private double transformYToScreenSpace(double y) {
    return (screenOffsetY + y) * scale * zoom + zoomOffsetY;
  }

  private double transformXToWorldSPace(double x) {
    return (x - zoomOffsetX) / (scale * zoom) - screenOffsetX;
  }

  private double transformYToWorldSPace(double y) {
    return (y - zoomOffsetY) / (scale * zoom) - screenOffsetY;
  }

  private double scaleToScreenSpace(double value) {
    return value * scale * zoom;
  }

  private double scaleToWorldSpaceX(double value) {
    return value / (scale * zoom);
  }

  private double scaleToWorldSpaceY(double value) {
    return value / (scale * zoom);
  }

  private void draw() {
    gc = this.getGraphicsContext2D();
    gc.setFill(Color.BEIGE);
    gc.fillRect(0, 0, this.width, this.height);


    double x = 75 / WIDTH;
    double y = 75 / WIDTH;


    dfsDrawNode(root);

    gc.clearRect(this.width, 0, this.getWidth(), this.getHeight());
  }

  private void dfsDrawNode(ProcessNode node) {
    var parentX = nodeExtent.getX(node);
    var parentY = nodeExtent.getY(node);

    for (var child : node.children) {
      var childX = nodeExtent.getX(child);
      var childY = nodeExtent.getY(child);
      drawLine(parentX, parentY, childX, childY);

      dfsDrawNode(child);
    }
    drawNode(node, parentX, parentY);
  }

  private void drawLine(double startX, double startY, double endX, double endY) {
    gc.setFill(Color.BLACK);
    gc.setLineWidth(scaleToScreenSpace(2 / WIDTH));
    gc.strokeLine(transformXToScreenSpace(startX + 50 / WIDTH),
        transformYToScreenSpace(startY + 100 / WIDTH),
        transformXToScreenSpace(endX + 50 / WIDTH),
        transformYToScreenSpace(endY));
  }

  private void drawNode(ProcessNode node, double x, double y) {
    double scale = 100 / WIDTH;
    gc.setFill(Color.DARKGRAY);
    gc.fillRect(transformXToScreenSpace(x), transformYToScreenSpace(y), scaleToScreenSpace(scale), scaleToScreenSpace(scale));
    gc.setFill(Color.BLACK);

    Font font = Font.font(scaleToScreenSpace(.018));
    gc.setFont(font);
    gc.fillText(pad(Integer.toString(node.pid)), transformXToScreenSpace(x + 5 / WIDTH), transformYToScreenSpace(y + 50 / WIDTH));
  }

  private String pad(String s) {
    if (s.length() == 1) {
      return "    " + s;
    } else if (s.length() <= 3) {
      return "   " + s;
    } else if (s.length() == 4) {
      return "  " + s;
    } else if (s.length() == 5) {
      return " " + s;
    } else {
      return s;
    }
  }

  @Override
  public boolean isResizable() {
    return true;
  }
}
