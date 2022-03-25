package com.schibsted.security.labs.analysis.gui;

import com.schibsted.security.labs.analysis.gui.elements.VisualElement;
import com.schibsted.security.labs.analysis.model.curated.Clazz;
import com.schibsted.security.labs.analysis.parser.FileManager;
import com.schibsted.security.labs.analysis.parser.MethodListener;
import org.antlr.v4.grammars.Java9Lexer;
import org.antlr.v4.grammars.Java9Parser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class GuiParser {
  private final List<VisualElement> elements;
  private final IndexedElements indexedElements;

  // FIXME redundant
  private final Clazz clazz;

  public GuiParser(Path path) {
    String code = FileManager.load(path);

    var lexer = new Java9Lexer(CharStreams.fromString(code));
    var tokens = new CommonTokenStream(lexer);
    var parser = new Java9Parser(tokens);
    var tree = parser.compilationUnit();

    var guiVisitor = new GuiVisitor();
    guiVisitor.visit(tree);

    elements = guiVisitor.elements();
    elements.addAll(CommentExtractor.extract(tokens));
    Collections.sort(elements);

    var maxLine = guiVisitor.maxLine();
    var maxColumn = guiVisitor.maxColumn();
    indexedElements = new IndexedElements(elements, maxLine, maxColumn);

    var walker = new ParseTreeWalker();
    var listener = new MethodListener();

    walker.walk(listener, tree);
    clazz = listener.getClazz();
  }

  public List<VisualElement> elements() {
    return elements;
  }

  public IndexedElements indexedElements() {
    return indexedElements;
  }

  public Clazz clazz() {
    return clazz;
  }
}
