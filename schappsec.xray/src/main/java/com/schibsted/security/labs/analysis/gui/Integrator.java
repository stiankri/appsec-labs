package com.schibsted.security.labs.analysis.gui;

import com.schibsted.security.labs.analysis.model.basic.FullyQualifiedClassName;
import com.schibsted.security.labs.analysis.model.curated.IndexedFile;
import com.schibsted.security.labs.analysis.parser.DataFlow;
import com.schibsted.security.labs.analysis.parser.FileManager;
import com.schibsted.security.labs.analysis.parser.Parser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Integrator {
  private final Map<FullyQualifiedClassName, CodeView> codeViews = new HashMap<>();

  public Integrator() {
    var directory = Paths.get("src/test/java/com/schibsted/security/labs/analysis/testingground/multiple");

    var elements = getElements(directory);
    var classes = Parser.parseDirectory(directory);
    var dataFlow = new DataFlow(classes);

    for (var fqcn : elements.keySet()) {

      var indexedElements = elements.get(fqcn);

      var clazz = classes.get(fqcn);
      var indexedFile = new IndexedFile(clazz, dataFlow);

      var codeView = new CodeView(1100, 1500, indexedElements, indexedFile);

      codeViews.put(fqcn, codeView);
    }
  }

  public static Map<FullyQualifiedClassName, IndexedElements> getElements(Path directory) {
    var paths = FileManager.listFilesRecursively(directory);

    Map<FullyQualifiedClassName, IndexedElements> result = new HashMap<>();
    for (var path : paths) {
      var guiParser = new GuiParser(path);
      var indexedElements = guiParser.indexedElements();
      var clazz = guiParser.clazz();

      var fqcn = new FullyQualifiedClassName(clazz.packageName(), clazz.className());
      result.put(fqcn, indexedElements);
    }

    return result;
  }

  public Optional<CodeView> getCodeView(FullyQualifiedClassName fullyQualifiedClassName) {
    return Optional.ofNullable(codeViews.get(fullyQualifiedClassName));
  }
}
