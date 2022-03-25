package com.schibsted.security.labs.jack.info.proc;

import com.schibsted.security.labs.jack.types.CgroupCategory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Cgroup {
  public Map<CgroupCategory, String> cgroups;

  public Cgroup(Map<CgroupCategory, String> cgroups) {
    this.cgroups = cgroups;
  }

  public static Cgroup getCgroupForProc(int processId) {
    var cgroups = extractCgroupMap(processId);
    return new Cgroup(cgroups);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Cgroups\n");
    for (var entry : cgroups.entrySet()) {
      sb.append("  ");
      sb.append(entry.getKey());
      sb.append(": ");
      sb.append(entry.getValue());
      sb.append("\n");
    }

    return sb.toString();
  }

  private static Map<CgroupCategory, String> extractCgroupMap(int process) {
    Map<CgroupCategory, String> map = new HashMap<>();

    try {
      FileInputStream fis = new FileInputStream("/proc/" + process + "/cgroup");
      Scanner scanner = new Scanner(fis);

      while (scanner.hasNext()) {
        String line = scanner.nextLine();
        String[] parts = line.split(":");
        var category = CgroupCategory.fromInt(Integer.parseInt(parts[0]));
        var category2 = CgroupCategory.fromString(parts[1]);

        // FIXME
                /*
                if (category != category2) {
                    throw new RuntimeException("Categories does not match: " + category + " " + category2);
                }
                 */

        var value = parts[2];

        map.put(category2, value);
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return map;
  }
}
