package com.schibsted.security.labs.analysis;

import com.schibsted.security.labs.analysis.gui.CommentExtractor;
import com.schibsted.security.labs.analysis.gui.GuiVisitor;
import com.schibsted.security.labs.analysis.parser.FileManager;
import com.schibsted.security.labs.analysis.parser.MethodListener;
import org.antlr.v4.grammars.Java9Lexer;
import org.antlr.v4.grammars.Java9Parser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.nio.file.Paths;

public class HelloAntlr {
  public static void main(String[] args) {
    String code = FileManager.load(Paths.get("src/main/java/com/schibsted/security/labs/testingground/basic/HelloWorld.java"));

    var lexer = new Java9Lexer(CharStreams.fromString(code));
    var tokens = new CommonTokenStream(lexer);
    var parser = new Java9Parser(tokens);
    var tree = parser.compilationUnit();

    var walker = new ParseTreeWalker();
    var listener = new MethodListener();

    walker.walk(listener, tree);
    var clazz = listener.getClazz();

    var guiVisitor = new GuiVisitor();
    guiVisitor.visit(tree);

    CommentExtractor.extract(tokens);

    System.out.println("hello");
  }
}
