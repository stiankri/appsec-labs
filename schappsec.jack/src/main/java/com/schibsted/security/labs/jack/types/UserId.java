package com.schibsted.security.labs.jack.types;

public class UserId {
  public final int real;
  public final int effective;
  public final int saved;
  public final int filesystem;

  public UserId(int real, int effective, int saved, int filesystem) {
    this.real = real;
    this.effective = effective;
    this.saved = saved;
    this.filesystem = filesystem;
  }

  private static int process(String id) {
    int value = Integer.parseInt(id);

    if (value < 0 || value > 1 << 16) {
      throw new RuntimeException("Invalid id");
    }

    return value;
  }

  public static UserId fromString(String string) {
    String[] parts = string.split("\t");

    return new UserId(process(parts[0]),
        process(parts[1]),
        process(parts[2]),
        process(parts[3]));
  }
}
