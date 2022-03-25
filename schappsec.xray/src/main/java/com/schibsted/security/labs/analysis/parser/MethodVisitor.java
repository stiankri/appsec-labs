package com.schibsted.security.labs.analysis.parser;

import com.schibsted.security.labs.analysis.model.MethodSink;
import com.schibsted.security.labs.analysis.model.basic.Import;
import com.schibsted.security.labs.analysis.model.basic.Method;
import com.schibsted.security.labs.analysis.model.basic.Package;
import com.schibsted.security.labs.analysis.model.basic.TokenIndex;
import org.antlr.v4.grammars.Java9BaseVisitor;
import org.antlr.v4.grammars.Java9Parser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;

public class MethodVisitor extends Java9BaseVisitor<Integer> {

  private final Method method;
  private final Map<String, Import> imports;
  private final Package currentPackage;
  private final String currentClass;
  private Map<String, List<MethodSink>> sinks = new HashMap<>();

  public MethodVisitor(Method method, Package currentPackage, Map<String, Import> imports, String currentClass) {
    this.method = method;
    this.currentPackage = currentPackage;
    this.imports = imports;
    this.currentClass = currentClass;
  }

  public Map<String, List<MethodSink>> getSinks() {
    return sinks;
  }

  @Override
  public Integer visitMethodInvocation(Java9Parser.MethodInvocationContext ctx) {
    var line = ctx.getStart().getLine();
    var column = ctx.getStart().getCharPositionInLine() + 1;

    String methodPrefix = "";
    String methodName;
    TokenIndex tokenIndex;
    List<TerminalNode> args;
    if (ctx.getChildCount() == 6) {
      // Java9Parser.TypeNameContext
      methodPrefix = extract(ctx.getChild(0));
      // Java9Parser.IdentifierContext
      var terminalNode = TreeUtils.extractTerminalNode(ctx.getChild(2)).get();
      methodName = terminalNode.value();
      tokenIndex = terminalNode.tokenIndex();
      args = extractArguments(ctx.getChild(4));
    } else {
      // Java9Parser.IdentifierContext
      var terminalNode = TreeUtils.extractTerminalNode(ctx.getChild(0)).get();
      methodName = terminalNode.value();
      tokenIndex = terminalNode.tokenIndex();
      args = extractArguments(ctx.getChild(2));
    }

    // Java9Parser.ArgumentListContext
    // Java9Parser.ExpressionContext

    int position = 0;
    for (var arg : args) {
      if (method.hasParameter(arg.value())) {
        if (!sinks.containsKey(arg.value())) {
          sinks.put(arg.value(), new ArrayList<>());
        }
        var list = sinks.get(arg.value());
        var joiner = new StringJoiner(".");
        if (!methodPrefix.isEmpty()) {
          joiner.add(methodPrefix);
        }
        joiner.add(methodName);
        var localName = joiner.toString();

        String[] parts = localName.split("\\.");
        var importTuple = imports.get(parts[0]);
        var prefix = (importTuple != null) ? importTuple.packageName() : currentPackage.packageName();
        if (methodPrefix.isEmpty()) {
          prefix = String.format("%s.%s", currentPackage.packageName(), currentClass);
        }

        var fullyQualifiedName = String.format("%s.%s", prefix, localName);

        list.add(new MethodSink(fullyQualifiedName,
            localName,
            position,
            tokenIndex,
            arg.tokenIndex()));
      }

      position++;
    }

    return super.visitMethodInvocation(ctx);
  }

  private String extract(ParseTree node) {
    if (node instanceof Java9Parser.TypeNameContext) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < node.getChildCount(); i++) {
        var child = node.getChild(i);
        var result = TreeUtils.extractTerminalNode(child);

        result.ifPresent(t -> sb.append(t.value()));
      }
      return sb.toString();
    }
    return "";
  }

  private List<TerminalNode> extractArguments(ParseTree node) {
    List<TerminalNode> result = new ArrayList<>();

    for (int i = 0; i < node.getChildCount(); i++) {
      var child = node.getChild(i);

      if (child instanceof Java9Parser.ExpressionContext) {
        var argument = TreeUtils.extractTerminalNode(child);
        argument.ifPresent(result::add);
      }
    }

    return result;
  }

}
