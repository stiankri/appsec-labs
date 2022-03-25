package com.schibsted.security.labs.analysis.model.curated;


import com.schibsted.security.labs.analysis.model.MethodSink;
import com.schibsted.security.labs.analysis.model.basic.TokenIndex;

import java.util.List;

public record Parameter(String type, String name, TokenIndex tokenIndex, List<MethodSink>sinks) {
}
