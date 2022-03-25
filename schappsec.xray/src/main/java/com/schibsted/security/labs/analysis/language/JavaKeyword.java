package com.schibsted.security.labs.analysis.language;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum JavaKeyword {
  ABSTRACT("abstract"),
  ASSERT("assert"),
  BOOLEAN("boolean"),
  BREAK("break"),
  BYTE("byte"),
  CASE("case"),
  CATCH("catch"),
  CHAR("char"),
  CLASS("class"),
  CONST("const"),
  CONTINUE("continue"),
  DEFAULT("default"),
  DO("do"),
  DOUBLE("double"),
  ELSE("else"),
  ENUM("enum"),
  EXTENDS("extends"),
  FINAL("final"),
  FINALLY("finally"),
  FLOAT("float"),
  FOR("for"),
  IF("if"),
  GOTO("goto"),
  IMPLEMENTS("implements"),
  IMPORT("import"),
  INSTANCEOF("instanceof"),
  INT("int"),
  INTERFACE("interface"),
  LONG("long"),
  NATIVE("native"),
  NEW("new"),
  PACKAGE("package"),
  PRIVATE("private"),
  PROTECTED("protected"),
  PUBLIC("public"),
  RETURN("return"),
  SHORT("short"),
  STATIC("static"),
  STRICTFP("strictfp"),
  SUPER("super"),
  SWITCH("switch"),
  SYNCHRONIZED("synchronized"),
  THIS("this"),
  THROW("throw"),
  THROWS("throws"),
  TRANSIENT("transient"),
  TRY("try"),
  UNDERSCORE("_"),
  VOID("void"),
  VOLATILE("volatile"),
  WHILE("while");

  private static final Map<String, JavaKeyword> stringMap = new HashMap<>();

  static {
    for (var word : JavaKeyword.values()) {
      stringMap.put(word.toString(), word);
    }
  }

  private final String word;

  JavaKeyword(String word) {
    this.word = word;
  }

  @Override
  public String toString() {
    return word;
  }

  public static Optional<JavaKeyword> fromString(String word) {
    return Optional.ofNullable(stringMap.get(word));
  }
}
