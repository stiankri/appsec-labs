package com.schibsted.security.labs.analysis.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {
  public static String load(Path path) {
    var file = path.toFile();
    try {
      return Files.readString(file.toPath());
    } catch (Exception e) {
      throw new RuntimeException(String.format("Failed to read file %s", file.toURI()));
    }
  }

  public static List<Path> listFilesRecursively(Path path) {
    try {
      return Files.walk(path)
          .filter(Files::isRegularFile)
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException("Failed to list files", e);
    }
  }
}
