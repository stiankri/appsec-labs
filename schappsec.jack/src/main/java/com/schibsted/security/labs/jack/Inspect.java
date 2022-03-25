package com.schibsted.security.labs.jack;

import com.schibsted.security.labs.jack.info.packagemanager.PackageManager;
import com.schibsted.security.labs.jack.info.proc.*;
import com.schibsted.security.labs.jack.readers.process.ProcessInfo;
import com.schibsted.security.labs.jack.info.sys.Lsms;

import java.util.ArrayList;

public class Inspect {
  public ProcessInfo inspect(int process) {
    var status = Status.getStatusForProc(process);
    var cmdline = Cmdline.read(process);
    var cgroup = Cgroup.getCgroupForProc(process);

    var attr = Attr.read(process);
    var lsms = Lsms.read();

    var ns = Ns.read(process);
    var namespaces = ns.isPresent() ? ns.get().namespaces : new ArrayList<String>();

    var packageManager = PackageManager.infer(cmdline);

    var fd = Fd.read(process);

    return new ProcessInfo(status,
        cmdline,
        attr,
        cgroup,
        lsms,
        namespaces,
        packageManager,
        fd);
  }
}
