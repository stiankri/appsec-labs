package com.schibsted.security.labs.analysis.model.curated;


import com.schibsted.security.labs.analysis.model.basic.FullyQualifiedClassName;
import com.schibsted.security.labs.analysis.model.basic.TokenIndex;

public record FullyQualifiedTokenIndex(FullyQualifiedClassName fullyQualifiedClassName, TokenIndex tokenIndex) {
}
