package com.schibsted.security.labs.analysis.model.curated;

import java.util.List;

public record Clazz(String packageName, String className, List<Method>methods) {
}
