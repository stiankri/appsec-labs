package com.schibsted.security.labs.analysis.gui;

import com.schibsted.security.labs.analysis.language.JavaKeyword;
import com.schibsted.security.labs.analysis.language.JavaReservedLiteral;
import com.schibsted.security.labs.analysis.gui.elements.AnnotationElement;
import com.schibsted.security.labs.analysis.gui.elements.BasicElement;
import com.schibsted.security.labs.analysis.gui.elements.KeywordElement;
import com.schibsted.security.labs.analysis.gui.elements.MethodDeclarationElement;
import com.schibsted.security.labs.analysis.gui.elements.NumberLiteralElement;
import com.schibsted.security.labs.analysis.gui.elements.ReservedLiteralElement;
import com.schibsted.security.labs.analysis.gui.elements.RestrictedIdentifierElement;
import com.schibsted.security.labs.analysis.gui.elements.SeparatorElement;
import com.schibsted.security.labs.analysis.gui.elements.StringLiteralElement;
import com.schibsted.security.labs.analysis.gui.elements.VisualElement;
import org.antlr.v4.grammars.Java9BaseVisitor;
import org.antlr.v4.grammars.Java9Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GuiVisitor extends Java9BaseVisitor<Integer> {
  private List<VisualElement> elements = new ArrayList<>();
  private int maxLine = -1;
  private int maxColumn = -1;

  public List<VisualElement> elements() {
    return elements;
  }

  public int maxLine() {
    return maxLine;
  }

  public int maxColumn() {
    return maxColumn;
  }

  @Override
  public Integer visitTerminal(TerminalNode node) {
    var element = extract(node);
    element.ifPresent(elements::add);
    return 0;
  }

  private Optional<VisualElement> extract(TerminalNode node) {
    var text = node.getText();
    var lineNumber = node.getSymbol().getLine();
    var column = node.getSymbol().getCharPositionInLine() + 1;

    if (lineNumber > maxLine) {
      maxLine = lineNumber;
    }

    if (column > maxColumn) {
      maxColumn = column;
    }

    // TODO make sharper
    if (hasParent(node, Java9Parser.MarkerAnnotationContext.class, 3)) {
      return Optional.of(new AnnotationElement(lineNumber, column, text));
    }

    if (text.equals(",") || text.equals(";")) {
      return Optional.of(new SeparatorElement(lineNumber, column, text));
    }

    if (hasParent(node, Java9Parser.LiteralContext.class, 1)) {
      if (JavaReservedLiteral.isJavaReservedLiteral(text)) {
        return Optional.of(new ReservedLiteralElement(lineNumber, column, text));
      } else if (text.startsWith("\"") && text.endsWith("\"")) {
        return Optional.of(new StringLiteralElement(lineNumber, column, text));
      } else {
        return Optional.of(new NumberLiteralElement(lineNumber, column, text));
      }
    }

    var keyword = JavaKeyword.fromString(text);
    if (keyword.isPresent()) {
      return Optional.of(new KeywordElement(lineNumber, column, text));
    }

    if (hasParent(node, Java9Parser.IdentifierContext.class, 1)
        && hasParent(node, Java9Parser.UnannClassType_lfno_unannClassOrInterfaceTypeContext.class, 2)
        && text.contains("var")) {
      return Optional.of(new RestrictedIdentifierElement(lineNumber, column, text));
    }

    // TODO reintroduce when proper tracing is in place
        /*
        if (hasParent(node, Java9Parser.IdentifierContext.class, 1)
                && hasParent(node, Java9Parser.FieldDeclarationContext.class, 5)) {
            return Optional.of(new FieldElement(lineNumber, column, text));
        }
         */

    if (hasParent(node, Java9Parser.IdentifierContext.class, 1)
        && hasParent(node, Java9Parser.MethodDeclaratorContext.class, 2)) {
      return Optional.of(new MethodDeclarationElement(lineNumber, column, text));
    }

    if (text.equals("<EOF>") && hasParent(node, Java9Parser.OrdinaryCompilationContext.class, 1)) {
      return Optional.empty();
    }

    return Optional.of(new BasicElement(lineNumber, column, text));
  }

  boolean hasParent(ParseTree parseTree, Class<?> clazz, int limit) {
    var parent = parseTree.getParent();

    int count = 0;
    while (parent != null && count < limit) {
      if (parent.getClass().equals(clazz)) {
        return true;
      }
      parent = parent.getParent();
      count++;
    }
    return false;
  }
}
