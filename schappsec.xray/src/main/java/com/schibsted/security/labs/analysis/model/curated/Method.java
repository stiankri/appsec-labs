package com.schibsted.security.labs.analysis.model.curated;


import com.schibsted.security.labs.analysis.model.basic.TokenIndex;

import java.util.List;

public record Method(String name, String fullyQualifiedName, List<Parameter>parameters, TokenIndex tokenIndex) {
}
