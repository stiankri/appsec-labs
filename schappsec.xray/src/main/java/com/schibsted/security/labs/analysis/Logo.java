package com.schibsted.security.labs.analysis;

import com.schibsted.security.labs.analysis.language.JavaKeyword;
import com.schibsted.security.labs.analysis.language.JavaReservedLiteral;
import com.schibsted.security.labs.analysis.language.JavaRestrictedIdentifier;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Logo {

  public static void main(String[] args) {
    var words = new ArrayList<>();
    words.addAll(stringValues(JavaKeyword.values()));
    words.addAll(stringValues(JavaReservedLiteral.values()));
    words.addAll(stringValues(JavaRestrictedIdentifier.values()));

    try {
      var secureRandom = SecureRandom.getInstanceStrong();

      for (int i = 0; i < 1000; i++) {
        int randomIndex = secureRandom.nextInt(words.size());
        var randomWord = words.get(randomIndex);
        System.out.print(randomWord);
      }
      System.out.println("");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static List<String> stringValues(Enum<?>[] myEnum) {
    return Arrays.stream(myEnum)
        .map(Enum::toString)
        .collect(Collectors.toList());
  }
}
