package com.schibsted.security.labs.analysis.gui;

import com.schibsted.security.labs.analysis.XrayApplication;
import com.schibsted.security.labs.analysis.gui.elements.VisualElement;
import com.schibsted.security.labs.analysis.model.basic.FullyQualifiedClassName;
import com.schibsted.security.labs.analysis.model.basic.TokenIndex;
import com.schibsted.security.labs.analysis.model.curated.IndexedFile;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Optional;

import static com.schibsted.security.labs.analysis.gui.GuiConfig.*;

public class CodeView extends Canvas {
  GraphicsContext gc;

  double width;
  double height;
  List<VisualElement> elements;
  IndexedElements indexedElements;
  IndexedFile indexedFile;
  double codeStart = 100.0;
  double sidebarWidth = 90.0;

  Optional<TokenIndex> selected = Optional.empty();
  Optional<TokenIndex> hover = Optional.empty();

  boolean controlPressed = false;
  boolean isLink = false;

  XrayApplication parent;

  public CodeView(double width,
                  double height,
                  IndexedElements indexedElements,
                  IndexedFile indexedFile) {
    super(width, height);
    this.width = width;
    this.height = height;
    this.indexedElements = indexedElements;
    this.indexedFile = indexedFile;
    this.elements = indexedElements.elements();
    this.setFocusTraversable(true);
    this.addEventFilter(MouseEvent.ANY, e -> this.requestFocus());

    this.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
      var line = line(event.getY());
      var column = column(event.getX());
      selected = Optional.of(new TokenIndex(line, column));

      if (event.isControlDown()) {
        var index = getSelected();
        if (index.isPresent()) {
          var link = indexedFile.getLink(index.get());
          if (link.isPresent()) {
            int p1 = link.get().lastIndexOf(".");
            var base = link.get().substring(0, p1);
            int p2 = base.lastIndexOf(".");
            var packageName = base.substring(0, p2);
            var className = base.substring(p2 + 1);
            var fqcn = new FullyQualifiedClassName(packageName, className);
            if (parent != null) {
              parent.set(fqcn);
            }
          }
        }
      } else {
        Optional<TokenIndex> selectedTokenIndex = getSelected();

        if (selectedTokenIndex.isPresent()) {
          var externalUsages = indexedFile.externalUsages(selectedTokenIndex.get());
          if (parent != null) {
            parent.setUsages(externalUsages);
          }
        }
      }
      draw();
    });

    KeyCombination keyCombination = new KeyCodeCombination(KeyCode.LEFT, KeyCombination.SHIFT_DOWN, KeyCombination.ALT_DOWN);

    this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode() == KeyCode.CONTROL) {
        controlPressed = true;
      } else if (event.getCode() == KeyCode.UP) {
        // TODO next symbol instead
        selected = decreaseLine(selected);
      } else if (event.getCode() == KeyCode.DOWN) {
        selected = increaseLine(selected);
      } else if (event.getCode() == KeyCode.LEFT) {
        selected = decreaseColumn(selected);
      } else if (event.getCode() == KeyCode.RIGHT) {
        selected = increaseColumn(selected);
      } else if (event.getCode() == KeyCode.R) {
        System.out.println("reset (no op)");
      }
      if (keyCombination.match(event)) {
        if (parent != null) {
          parent.back();
        }
      }
      draw();
    });

    this.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
      controlPressed = event.isControlDown();

      if (event.isControlDown()) {
        var line = line(event.getY());
        var column = column(event.getX());
        hover = Optional.of(new TokenIndex(line, column));

        isLink = false;

        var index = getHover();
        if (index.isPresent()) {
          var link = indexedFile.getLink(index.get());
          if (link.isPresent()) {
            isLink = true;
          }
        }

        draw();
      }
    });

    this.addEventFilter(MouseEvent.MOUSE_EXITED, event -> {
      controlPressed = false;
      draw();
    });

    draw();
  }

  int increase(int current) {
    if (current == -1) {
      return current;
    } else {
      return current + 1;
    }
  }

  public void setParent(XrayApplication parent) {
    this.parent = parent;
  }

  Optional<TokenIndex> decreaseColumn(Optional<TokenIndex> tokenIndex) {
    return tokenIndex.map(t -> new TokenIndex(t.line(), decrease(t.column())));
  }

  Optional<TokenIndex> increaseColumn(Optional<TokenIndex> tokenIndex) {
    return tokenIndex.map(t -> new TokenIndex(t.line(), increase(t.column())));
  }

  Optional<TokenIndex> decreaseLine(Optional<TokenIndex> tokenIndex) {
    return tokenIndex.map(t -> new TokenIndex(decrease(t.line()), t.column()));
  }

  Optional<TokenIndex> increaseLine(Optional<TokenIndex> tokenIndex) {
    return tokenIndex.map(t -> new TokenIndex(increase(t.line()), t.column()));
  }

  int decrease(int current) {
    if (current > 1) {
      return current - 1;
    } else {
      return current;
    }
  }

  int column(double x) {
    return (int) Math.floor((x - codeStart) / charWidth) + 1;
  }

  int line(double y) {
    return (int) Math.floor((y) / charHeight) + 1;
  }

  Optional<TokenIndex> getSelected() {
    return selected.flatMap(selected -> indexedElements.getElement(selected).map(VisualElement::tokenIndex));
  }

  Optional<TokenIndex> getHover() {
    return hover.flatMap(hover -> indexedElements.getElement(hover).map(VisualElement::tokenIndex));
  }

  private void highlight(GraphicsContext gc, VisualElement element) {
    var line = element.line();
    var column = element.column();
    var text = element.text();

    gc.setFill(ColorPalette.HIGHLIGHTED_SYMBOL.color());
    gc.fillRect(codeStart + (column - 1) * charWidth, (line - 1) * charHeight, text.length() * charWidth, charHeight);
  }

  private void draw() {
    gc = this.getGraphicsContext2D();

    gc.setFill(ColorPalette.BACKGROUND.color());
    gc.fillRect(0, 0, this.width, this.height);

    gc.setFont(GuiConfig.font());

    int maxY = 0;

    gc.setFill(ColorPalette.HIGHLIGHTED_LINE.color());
    if (selected.isPresent()) {
      var line = selected.get().line();
      gc.fillRect(sidebarWidth + 1, (line - 1) * charHeight, this.width, charHeight);
    }

    var selectedTokenIndex = getSelected();
    if (selectedTokenIndex.isPresent()) {
      var usages = indexedFile.usages(selectedTokenIndex.get());
      for (var usage : usages) {
        var element = indexedElements.getElement(usage);
        if (element.isPresent()) {
          highlight(gc, element.get());
        }
      }
    }

    if (selected.isPresent()) {
      var selectedElement = indexedElements.getElement(selected.get());
      if (selectedElement.isPresent()) {
        highlight(gc, selectedElement.get());
      }
    }

    for (var element : elements) {
      var line = element.line();
      var column = element.column();

      maxY = Integer.max(maxY, line);

      gc.setFill(element.color());

      var lineOffset = 0;
      for (var part : element.text().split("\\n")) {
        if (lineOffset == 0) {
          drawText(gc, element, part, line, column);
        } else {
          drawText(gc, element, part, line + lineOffset, 0);
        }
        lineOffset++;
      }
    }

    gc.setFill(ColorPalette.SIDEBAR.color());
    gc.fillRect(0, 0, sidebarWidth, this.height);

    gc.setFill(ColorPalette.LINE_SEPARATOR.color());
    gc.fillRect(sidebarWidth, 0, 1, this.height);

    for (int i = 1; i <= maxY; i++) {
      gc.setFill(ColorPalette.LINE_NUMBER.color());
      gc.fillText(GuiConfig.createLineNumberString(i), 10, charHeight * i - charHeightDiff);
    }
  }

  void drawText(GraphicsContext gc, VisualElement element, String text, int line, int column) {
    gc.setFont(GuiConfig.font());

    if (controlPressed && isLink && intersectionHover(line, column, text)) {
      gc.setFill(ColorPalette.HYPERLINK.color());
      gc.fillRect(charWidth * (column - 1) + codeStart, charHeight * line - charHeightDiff + 2, text.length() * charWidth, 1);
    } else {
      gc.setFill(element.color());
    }

    gc.fillText(text, charWidth * (column - 1) + codeStart, charHeight * line - charHeightDiff);
  }

  boolean intersectionHover(int line, int column, String text) {
    if (hover.isPresent()) {
      return hover.get().line() == line
          && hover.get().column() >= column
          && hover.get().column() < (column + text.length());
    } else {
      return false;
    }
  }
}
