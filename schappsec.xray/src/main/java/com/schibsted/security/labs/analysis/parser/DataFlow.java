package com.schibsted.security.labs.analysis.parser;

import com.schibsted.security.labs.analysis.model.MethodSink;
import com.schibsted.security.labs.analysis.model.basic.FullyQualifiedClassName;
import com.schibsted.security.labs.analysis.model.curated.Clazz;
import com.schibsted.security.labs.analysis.model.curated.FullyQualifiedTokenIndex;
import com.schibsted.security.labs.analysis.model.curated.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class DataFlow {
  private Map<FullyQualifiedTokenIndex, List<String>> usages = new HashMap<>();
  private Map<String, Method> methods = new HashMap<>();

  public DataFlow(Map<FullyQualifiedClassName, Clazz> classes) {
    prepare(classes);

    for (var entry : classes.entrySet()) {
      var fullyQualifiedClassName = entry.getKey();
      var clazz = entry.getValue();

      for (var method : clazz.methods()) {
        for (var parameter : method.parameters()) {
          for (var sink : parameter.sinks()) {
            var fullyQualifiedTokenIndex = new FullyQualifiedTokenIndex(fullyQualifiedClassName, parameter.tokenIndex());
            usages.put(fullyQualifiedTokenIndex, resolve(sink));
          }
        }
      }
    }
  }

  public Map<FullyQualifiedTokenIndex, List<String>> getUsages() {
    return usages;
  }

  private void prepare(Map<FullyQualifiedClassName, Clazz> classes) {
    for (var clazz : classes.values()) {
      for (var method : clazz.methods()) {
        methods.put(method.fullyQualifiedName(), method);
      }
    }
  }

  public List<String> resolve(MethodSink sink) {
    Queue<MethodSink> queue = new LinkedList<>();
    queue.add(sink);

    List<String> result = new ArrayList<>();
    while (!queue.isEmpty()) {
      var currentSink = queue.poll();

      var method = methods.get(currentSink.fullyQualifiedName());
      if (method != null) {
        var parameter = method.parameters().get(currentSink.argumentPosition());
        queue.addAll(parameter.sinks());

        StringBuilder sb = new StringBuilder();
        sb.append(currentSink.localName());
        sb.append("(");
        boolean first = true;
        int count = 0;
        for (var param : method.parameters()) {
          if (!first) {
            sb.append(", ");
          }
          sb.append(param.type());
          sb.append(" ");
          if (count == currentSink.argumentPosition()) {
            sb.append("[");
            sb.append(param.name());
            sb.append("]");
          } else {
            sb.append(param.name());
          }

          first = false;
          count++;
        }
        sb.append(")");
        result.add(sb.toString());
      } else {
        result.add(currentSink.localName() + "()");
      }
    }

    return result;
  }
}
