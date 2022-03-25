package com.schibsted.security.labs.analysis.model.curated;

import com.schibsted.security.labs.analysis.model.basic.FullyQualifiedClassName;
import com.schibsted.security.labs.analysis.model.basic.TokenIndex;
import com.schibsted.security.labs.analysis.parser.DataFlow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class IndexedFile {
  private final Clazz clazz;
  private final Map<TokenIndex, String> map = new HashMap<>();
  private final Map<TokenIndex, List<TokenIndex>> usages = new HashMap<>();
  private final Map<TokenIndex, String> links = new HashMap<>();
  private final Map<TokenIndex, List<String>> externalUsages = new HashMap<>();

  public IndexedFile(Clazz clazz, DataFlow dataFlow) {
    this.clazz = clazz;
    setupExternalUsages(clazz, dataFlow);

    for (var method : clazz.methods()) {
      map.put(method.tokenIndex(), method.name());

      for (var parameter : method.parameters()) {
        map.put(parameter.tokenIndex(), parameter.name());

        List<TokenIndex> usage = new ArrayList<>();
        for (var sink : parameter.sinks()) {
          map.put(sink.methodIndex(), sink.fullyQualifiedName());
          usage.add(sink.argumentIndex());
          links.put(sink.methodIndex(), sink.fullyQualifiedName());
        }

        usages.put(parameter.tokenIndex(), usage);
      }
    }
  }

  private void setupExternalUsages(Clazz clazz, DataFlow dataFlow) {
    var fqcn = new FullyQualifiedClassName(clazz.packageName(), clazz.className());

    for (var entry : dataFlow.getUsages().entrySet()) {
      var fqti = entry.getKey();
      if (fqti.fullyQualifiedClassName().equals(fqcn)) {
        var list = entry.getValue();
        externalUsages.put(fqti.tokenIndex(), list);
      }
    }
  }

  public Clazz clazz() {
    return clazz;
  }

  public Optional<String> get(TokenIndex tokenIndex) {
    return Optional.ofNullable(map.get(tokenIndex));
  }

  public Optional<String> getLink(TokenIndex tokenIndex) {
    return Optional.ofNullable(links.get(tokenIndex));
  }

  public List<TokenIndex> usages(TokenIndex tokenIndex) {
    var u = usages.get(tokenIndex);
    if (u != null) {
      return u;
    } else {
      return List.of();
    }
  }

  public List<String> externalUsages(TokenIndex tokenIndex) {
    var u = externalUsages.get(tokenIndex);
    if (u != null) {
      return u;
    } else {
      return List.of();
    }
  }
}
