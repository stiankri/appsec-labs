package com.schibsted.security.labs.jack.types;

public class PasswdEntry {
  public final String userName;
  public final int userId;
  public final int groupId;

  public PasswdEntry(String userName, int userId, int groupId) {
    this.userName = userName;
    this.userId = userId;
    this.groupId = groupId;
  }
}
