package com.schibsted.security.labs.jack.readers.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProcessReader {

  public static List<String> run(String... command) {
    List<String> result = new ArrayList<>();
    try {
      ProcessBuilder builder = new ProcessBuilder(command);
      Process process = builder.start();

      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      result = reader.lines().collect(Collectors.toList());

      process.waitFor();
    } catch (IOException | InterruptedException e) {
      return result;
    }
    return result;
  }
}
