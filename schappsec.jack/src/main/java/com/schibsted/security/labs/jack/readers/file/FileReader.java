package com.schibsted.security.labs.jack.readers.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {
  public static String readFileAsString(String path) {
    try {
      return new String(Files.readAllBytes(Paths.get(path))).strip();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String readSymbolicLink(String path) throws AccessDeniedException {
    try {
      File file = new File(path);
      Path link = file.toPath();
      return Files.readSymbolicLink(link).toFile().getName();
    } catch (AccessDeniedException e) {
      throw e;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
