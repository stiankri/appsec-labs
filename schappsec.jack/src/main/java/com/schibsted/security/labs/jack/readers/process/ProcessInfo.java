package com.schibsted.security.labs.jack.readers.process;

import com.schibsted.security.labs.jack.Capabilities;
import com.schibsted.security.labs.jack.info.packagemanager.PackageManager;
import com.schibsted.security.labs.jack.info.proc.*;
import com.schibsted.security.labs.jack.info.sys.Lsms;
import com.schibsted.security.labs.jack.types.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProcessInfo {
  public String name;

  public Capabilities inheritable;
  public Capabilities permitted;
  public Capabilities effective;
  public Capabilities bounding;
  public Capabilities ambient;

  public boolean noNewPrivileges;
  public Pid processId;
  public Pid parentProcessId;
  public Pid tracerProcessId;

  public State state;

  public UserId userId;
  public UserId groupId;
  public int threads;
  public Seccomp seccomp;

  public String cmdline;

  public String currentAttr;

  public List<String> namespaces;

  public Lsms lsms;
  public Map<CgroupCategory, String> cgroups;

  public Optional<PackageManager> packageManager;
  public Fd fd;

  public ProcessInfo(Status status,
                     Cmdline cmdline,
                     Attr attr,
                     Cgroup cgroup,
                     Lsms lsms,
                     List<String> namespaces,
                     Optional<PackageManager> packageManager,
                     Fd fd) {
    this.name = status.name;
    this.inheritable = status.inheritable;
    this.permitted = status.permitted;
    this.effective = status.effective;
    this.bounding = status.bounding;
    this.ambient = status.ambient;

    this.noNewPrivileges = status.noNewPrivs;
    this.processId = status.processId;
    this.parentProcessId = status.parentProcessId;
    this.tracerProcessId = status.tracerProcessId;

    this.state = status.state;

    this.userId = status.userId;
    this.groupId = status.groupId;
    this.threads = status.threads;
    this.seccomp = status.seccomp;

    this.cmdline = cmdline.cmdline;

    this.currentAttr = attr.current;

    this.cgroups = cgroup.cgroups;
    this.lsms = lsms;
    this.namespaces = namespaces;

    this.packageManager = packageManager;
    this.fd = fd;
  }
}
