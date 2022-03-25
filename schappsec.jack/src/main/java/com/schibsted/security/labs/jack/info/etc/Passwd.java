package com.schibsted.security.labs.jack.info.etc;

import com.schibsted.security.labs.jack.types.PasswdEntry;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Passwd {
  Map<Integer, PasswdEntry> gidMap = new HashMap<>();
  Map<Integer, PasswdEntry> uidMap = new HashMap<>();
  Map<String, PasswdEntry> usernameMap = new HashMap<>();

  public Passwd(List<PasswdEntry> entries) {
    for (var entry : entries) {
      gidMap.put(entry.groupId, entry);
      uidMap.put(entry.userId, entry);
      usernameMap.put(entry.userName, entry);
    }
  }

  // FIXME optional
  public PasswdEntry getByGid(int gid) {
    return gidMap.get(gid);
  }

  public PasswdEntry getByUid(int gid) {
    return uidMap.get(gid);
  }

  public PasswdEntry getByUsername(String name) {
    return usernameMap.get(name);
  }

  private static List<PasswdEntry> extractPasswd() {
    List<PasswdEntry> entries = new ArrayList<>();

    try {
      FileInputStream fis = new FileInputStream("/etc/passwd");
      Scanner scanner = new Scanner(fis);

      while (scanner.hasNext()) {
        String line = scanner.nextLine();
        String[] parts = line.split(":");
        var userName = parts[0];
        int userId = Integer.parseInt(parts[2]);
        int groupId = Integer.parseInt(parts[3]);

        entries.add(new PasswdEntry(userName, userId, groupId));
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return entries;
  }

  public static Passwd read() {
    return new Passwd(extractPasswd());
  }
}
