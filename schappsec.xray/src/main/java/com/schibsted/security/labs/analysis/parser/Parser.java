package com.schibsted.security.labs.analysis.parser;

import com.schibsted.security.labs.analysis.model.basic.FullyQualifiedClassName;
import com.schibsted.security.labs.analysis.model.curated.Clazz;
import org.antlr.v4.grammars.Java9Lexer;
import org.antlr.v4.grammars.Java9Parser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Parser {

  private Parser() {
  }

  public static Map<FullyQualifiedClassName, Clazz> parseDirectory(Path directory) {
    var paths = FileManager.listFilesRecursively(directory);

    Map<FullyQualifiedClassName, Clazz> classes = new HashMap<>();
    for (var path : paths) {
      var clazz = parseSingleFile(path);

      var fqcn = new FullyQualifiedClassName(clazz.packageName(), clazz.className());
      classes.put(fqcn, clazz);
    }
    return classes;
  }

  public static Clazz parseSingleFile(Path path) {
    String code = FileManager.load(path);

    var lexer = new Java9Lexer(CharStreams.fromString(code));
    var tokens = new CommonTokenStream(lexer);
    var parser = new Java9Parser(tokens);
    var tree = parser.compilationUnit();

    var walker = new ParseTreeWalker();
    var listener = new MethodListener();

    walker.walk(listener, tree);
    return listener.getClazz();
  }
}
