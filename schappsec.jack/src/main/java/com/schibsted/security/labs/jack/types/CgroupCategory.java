package com.schibsted.security.labs.jack.types;

import java.util.HashMap;
import java.util.Map;

public enum CgroupCategory {
  BASE(0, ""),
  SYSTEMD(1, "name=systemd"),
  CPU(2, "cpu,cpuacct"),
  FREEZER(3, "freezer"),
  NET(4, "net_cls,net_prio"),
  DEVICES(5, "devices"),
  BLKIO(6, "blkio"),
  PIDS(7, "pids"),
  CPUSET(8, "cpuset"),
  RDMA(9, "rdma"),
  HUGETLB(10, "hugetlb"),
  PERF_EVENT(11, "perf_event"),
  MEMORY(12, "memory"),
  MISC(13, "misc");

  private int index;
  private String name;
  private static Map<String, CgroupCategory> stringMap = new HashMap<>();
  private static Map<Integer, CgroupCategory> intMap = new HashMap<>();

  static {
    for (var group : CgroupCategory.values()) {
      stringMap.put(group.getName(), group);
      intMap.put(group.getIndex(), group);
    }
  }

  CgroupCategory(int index, String name) {
    this.index = index;
    this.name = name;
  }

  public static CgroupCategory fromString(String name) {
    return stringMap.get(name);
  }

  public static CgroupCategory fromInt(int index) {
    return intMap.get(index);
  }

  public int getIndex() {
    return this.index;
  }

  public String getName() {
    return this.name;
  }
}
