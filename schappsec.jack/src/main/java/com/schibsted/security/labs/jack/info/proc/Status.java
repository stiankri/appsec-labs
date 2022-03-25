package com.schibsted.security.labs.jack.info.proc;

import com.schibsted.security.labs.jack.Capabilities;
import com.schibsted.security.labs.jack.info.etc.Passwd;
import com.schibsted.security.labs.jack.types.Pid;
import com.schibsted.security.labs.jack.types.Seccomp;
import com.schibsted.security.labs.jack.types.State;
import com.schibsted.security.labs.jack.types.UserId;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class Status {
  public Capabilities inheritable;
  public Capabilities permitted;
  public Capabilities effective;
  public Capabilities bounding;
  public Capabilities ambient;
  public boolean noNewPrivs;
  public Pid processId;
  public Pid parentProcessId;
  // TODO make optional
  public Pid tracerProcessId;
  public String name;
  public State state;
  public UserId userId;
  // FIXME
  public UserId groupId;
  public int threads;
  public Seccomp seccomp;
  static Passwd passwd = Passwd.read();

  public Status(Capabilities inheritable,
                Capabilities permitted,
                Capabilities effective,
                Capabilities bounding,
                Capabilities ambient,
                boolean noNewPrivs,
                Pid processId,
                Pid parentProcessId,
                Pid tracerProcessId,
                String name,
                State state,
                UserId userId,
                UserId groupId,
                int threads,
                Seccomp seccomp) {
    this.inheritable = inheritable;
    this.permitted = permitted;
    this.effective = effective;
    this.bounding = bounding;
    this.ambient = ambient;
    this.noNewPrivs = noNewPrivs;
    this.processId = processId;
    this.parentProcessId = parentProcessId;
    this.tracerProcessId = tracerProcessId;
    this.name = name;
    this.state = state;
    this.userId = userId;
    this.groupId = groupId;
    this.threads = threads;
    this.seccomp = seccomp;
  }

  static Map<String, String> extractStatusMap(int process) {
    Map<String, String> map = new HashMap<>();

    try {
      FileInputStream fis = new FileInputStream("/proc/" + process + "/status");
      Scanner scanner = new Scanner(fis);

      while (scanner.hasNext()) {
        String line = scanner.nextLine();
        var endOfName = line.indexOf(":");
        var name = line.substring(0, endOfName);
        var value = line.substring(endOfName + 1).strip();

        map.put(name, value);
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return map;
  }

  static Capabilities capabilitiesFromRaw(Map<String, String> status, String fieldName) {
    var cap = status.get(fieldName);
    return Capabilities.fromBitMask(cap);
  }

  public static Status getStatusForProc(int processId) {
    var status = extractStatusMap(processId);

    var uid = UserId.fromString(status.get("Uid"));
    var gid = UserId.fromString(status.get("Gid"));

    var capInheritable = capabilitiesFromRaw(status, "CapInh");
    var capPermitted = capabilitiesFromRaw(status, "CapPrm");
    var capEffective = capabilitiesFromRaw(status, "CapEff");
    var capBounding = capabilitiesFromRaw(status, "CapBnd");
    var capAmbient = capabilitiesFromRaw(status, "CapAmb");

    var seccomp = Seccomp.fromInt(Integer.parseInt(status.get("Seccomp").strip()));
    var unmask = status.get("Umask");

    // TODO NStgid, NSpid, NSpgid
    int threads = Integer.parseInt(status.get("Threads"));

    var state = State.fromString(status.get("State"));

    // TODO SigBlk, SigIgn, SigCgt

    var parsedProcessId = new Pid(Integer.parseInt(status.get("Pid")));
    var parentProcessId = new Pid(Integer.parseInt(status.get("PPid")));
    var tracerProcessId = new Pid(Integer.parseInt(status.get("TracerPid")));

    var name = status.get("Name");

    boolean noNewPrivs = status.get("NoNewPrivs").strip().equals("1");
    return new Status(capInheritable,
        capPermitted,
        capEffective,
        capBounding,
        capAmbient,
        noNewPrivs,
        parsedProcessId,
        parentProcessId,
        tracerProcessId,
        name,
        state,
        uid,
        gid,
        threads,
        seccomp);
  }

  public static String printCapabilities(Capabilities capabilities) {
    if (capabilities.hasAll()) {
      return "all";
    } else if (capabilities.hasNone()) {
      return "none";
    } else {
      StringBuilder sb = new StringBuilder();
      for (var capability : capabilities.getCapabilities()) {
        sb.append(capability);
        sb.append(", ");
      }
      return sb.toString();
    }
  }

  public static String printPid(Pid pid) {
    return pid.pid == 0 ? "none" : Integer.toString(pid.pid);
  }

  // TODO cleanup
  public static String wrapUserId(int uid) {
    if (passwd.getByUid(uid) != null) {
      return String.format("%s (%d)", passwd.getByUid(uid).userName, uid);
    } else {
      return String.format("(%d)", uid);
    }
  }

  private static String wrapGroupId(int uid) {
    if (passwd.getByGid(uid) != null) {
      return String.format("%s (%d)", passwd.getByGid(uid).userName, uid);
    } else {
      return String.format("(%d)", uid);
    }
  }

  public static String printUserId(UserId userId) {
    return printUserId(userId, Status::wrapUserId);
  }

  public static String printGroupId(UserId userId) {
    return printUserId(userId, Status::wrapGroupId);
  }


  private static String printUserId(UserId userId, Function<Integer, String> wrapper) {
    if (userId.effective == userId.real &&
        userId.real == userId.filesystem &&
        userId.filesystem == userId.saved) {
      return wrapper.apply(userId.effective);
    } else {
      return String.format("%s %s %s %s",
          wrapper.apply(userId.real),
          wrapper.apply(userId.effective),
          wrapper.apply(userId.saved),
          wrapper.apply(userId.filesystem));
    }
  }

  @Override
  public String toString() {
    return "Name: " + name + "\n" +
        "Pid: " + processId + "\n" +
        "Parent Pid: " + printPid(parentProcessId) + "\n" +
        "Tracer Pid: " + printPid(tracerProcessId) + "\n" +
        "State: " + state + "\n" +
        "UserID: " + printUserId(userId, Status::wrapUserId) + "\n" +
        "GroupID: " + printUserId(groupId, Status::wrapGroupId) + "\n" +
        "Threads: " + threads + "\n" +
        "Capabilities\n" +
        "  Inheritable: " + printCapabilities(inheritable) + "\n" +
        "  Permitted: " + printCapabilities(permitted) + "\n" +
        "  Effective: " + printCapabilities(effective) + "\n" +
        "  Bounding: " + printCapabilities(bounding) + "\n" +
        "  Ambient: " + printCapabilities(ambient) + "\n" +
        "NoNewPrivs: " + this.noNewPrivs + "\n" +
        "Seccomp: " + this.seccomp + "\n";

  }
}
