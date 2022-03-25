package com.schibsted.security.labs.analysis.parser;

import com.schibsted.security.labs.analysis.model.basic.Import;
import com.schibsted.security.labs.analysis.model.basic.TokenIndex;
import com.schibsted.security.labs.analysis.model.basic.Package;
import com.schibsted.security.labs.analysis.model.curated.Clazz;
import com.schibsted.security.labs.analysis.model.curated.Method;
import com.schibsted.security.labs.analysis.model.curated.Parameter;

import org.antlr.v4.grammars.Java9BaseListener;
import org.antlr.v4.grammars.Java9Parser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MethodListener extends Java9BaseListener {
  com.schibsted.security.labs.analysis.model.basic.Method currentMethod;
  Package currentPackage;
  String currentClass;
  List<Method> methods = new ArrayList<>();
  Clazz clazz;
  Map<String, Import> imports = new HashMap<>();

  public Clazz getClazz() {
    return clazz;
  }

  @Override
  public void enterPackageName(Java9Parser.PackageNameContext ctx) {
    if (ctx.parent instanceof Java9Parser.PackageDeclarationContext) {
      var packageName = ctx.getText();

      currentPackage = new Package(packageName);
    }
  }

  @Override
  public void enterImportDeclaration(Java9Parser.ImportDeclarationContext ctx) {
    var importTuple = extractImport(ctx);
    imports.put(importTuple.className(), importTuple);
  }

  @Override
  public void enterClassDeclaration(Java9Parser.ClassDeclarationContext ctx) {
    currentClass = extractClassName(ctx);
  }

  @Override
  public void exitClassDeclaration(Java9Parser.ClassDeclarationContext ctx) {
    clazz = new Clazz(currentPackage.packageName(), currentClass, methods);
  }

  @Override
  public void enterMethodDeclarator(Java9Parser.MethodDeclaratorContext ctx) {
    var line = ctx.getStart().getLine();
    var column = ctx.getStart().getCharPositionInLine() + 1;
    var tokenIndex = new TokenIndex(line, column);

    var methodName = ctx.identifier().Identifier().toString();
    var parameters = extractParameters(ctx.getChild(2));
    currentMethod = new com.schibsted.security.labs.analysis.model.basic.Method(methodName, parameters, tokenIndex);
  }

  @Override
  public void enterMethodBody(Java9Parser.MethodBodyContext ctx) {
    var visitor = new MethodVisitor(currentMethod, currentPackage, imports, currentClass);
    visitor.visitMethodBody(ctx);
    var sinks = visitor.getSinks();

    List<Parameter> parameters = new ArrayList<>();
    for (var parameter : currentMethod.parameters()) {
      var sinksForParameter = sinks.getOrDefault(parameter.name(), new ArrayList<>());
      parameters.add(new Parameter(parameter.type(),
          parameter.name(),
          parameter.tokenIndex(),
          sinksForParameter));
    }

    methods.add(new Method(currentMethod.name(),
        fullyQualifiedMethodName(currentPackage, currentClass, currentMethod),
        parameters,
        currentMethod.tokenIndex()));
  }

  private String fullyQualifiedMethodName(Package currentPackage, String currentClass, com.schibsted.security.labs.analysis.model.basic.Method method) {
    return String.format("%s.%s.%s", currentPackage.packageName(),
        currentClass,
        method.name());
  }

  List<com.schibsted.security.labs.analysis.model.basic.Parameter> extractParameters(ParseTree node) {
    List<com.schibsted.security.labs.analysis.model.basic.Parameter> result = new ArrayList<>();
    for (int i = 0; i < node.getChildCount(); i++) {
      var child = node.getChild(i);
      if (child instanceof Java9Parser.LastFormalParameterContext
          || child instanceof Java9Parser.FormalParametersContext) {
        var formalParameter = child.getChild(0);

        Optional<TerminalNode> type;
        Optional<TerminalNode> name;

        if (formalParameter.getChildCount() == 3) {
          type = TreeUtils.extractTerminalNode(formalParameter.getChild(1));
          name = TreeUtils.extractTerminalNode(formalParameter.getChild(2));
        } else {
          type = TreeUtils.extractTerminalNode(formalParameter.getChild(0));
          name = TreeUtils.extractTerminalNode(formalParameter.getChild(1));
        }

        result.add(new com.schibsted.security.labs.analysis.model.basic.Parameter(type.get().value(),
            name.get().value(),
            name.get().tokenIndex()));
      }
    }

    return result;
  }

  String extractClassName(ParseTree parent) {
    var node = parent.getChild(0);

    for (int i = 0; i < node.getChildCount(); i++) {
      var child = node.getChild(i);

      if (child instanceof Java9Parser.IdentifierContext identifier) {
        return identifier.Identifier().toString();
      }
    }

    throw new RuntimeException("Unable to get classname");
  }

  Import extractImport(ParseTree parent) {
    var node = parent.getChild(0).getChild(1);

    String packageName = node.getChild(0).getText();
    String className = node.getChild(2).getText();

    return new Import(packageName, className);
  }
}
