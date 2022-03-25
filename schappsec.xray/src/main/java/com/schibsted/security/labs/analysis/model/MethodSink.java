package com.schibsted.security.labs.analysis.model;

import com.schibsted.security.labs.analysis.model.basic.TokenIndex;

// TODO can occur in multiple positions
// TODO polymorphism
public record MethodSink(String fullyQualifiedName,
                         String localName,
                         int argumentPosition,
                         TokenIndex methodIndex,
                         TokenIndex argumentIndex) {

}
