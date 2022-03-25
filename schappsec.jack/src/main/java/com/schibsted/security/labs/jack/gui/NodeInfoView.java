package com.schibsted.security.labs.jack.gui;

import com.schibsted.security.labs.jack.Inspect;
import com.schibsted.security.labs.jack.info.proc.Status;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class NodeInfoView extends VBox {

  public NodeInfoView(int width) {
    this.setMinSize(width, 0);
    this.setMaxWidth(width);
    this.update(1);
  }

  public void update(int value) {
    var inspect = new Inspect();
    var processInfo = inspect.inspect(value);

    this.getChildren().setAll(
        section("General"),
        kv("Name", processInfo.name),
        kv("cmdline", processInfo.cmdline),
        kv("State", processInfo.state),
        kv("PID", processInfo.processId),
        kv("Parent PID", Status.printPid(processInfo.parentProcessId)),
        kv("Tracer PID", Status.printPid(processInfo.tracerProcessId)),
        kv("Threads", processInfo.threads),
        section("Other security"),
        kv("User", Status.printUserId(processInfo.userId)),
        kv("Group", Status.printGroupId(processInfo.groupId)),
        kv("NoNewPrivs", processInfo.noNewPrivileges),
        kv("Seccomp", processInfo.seccomp),
        section("LSM"),
        kv("Current", processInfo.currentAttr),
        kv("Available LSMs", processInfo.lsms),
        section("Capabilities"),
        kv("Inheritable", Status.printCapabilities(processInfo.inheritable)),
        kv("Permitted", Status.printCapabilities(processInfo.permitted)),
        kv("Effective", Status.printCapabilities(processInfo.effective)),
        kv("Bounding", Status.printCapabilities(processInfo.bounding)),
        kv("Ambient", Status.printCapabilities(processInfo.ambient))
    );

    this.getChildren().add(section("Cgroups"));
    for (var entry : processInfo.cgroups.entrySet()) {
      this.getChildren().add(kv(entry.getKey().toString(), entry.getValue()));
    }

    this.getChildren().add(section("Namespaces"));
    for (var entry : processInfo.namespaces) {
      this.getChildren().add(kv("", entry));
    }

    if (processInfo.packageManager.isPresent()) {
      this.getChildren().add(section("Package"));
      this.getChildren().add(kv(processInfo.packageManager.get().packageType.toString(),
          processInfo.packageManager.get().packageName));
    }

    this.getChildren().addAll(
        section("File Descriptors"),
        kv("sockets", processInfo.fd.sockets),
        kv("pipes", processInfo.fd.pipes)
    );
  }

  private Text section(String name) {
    var sectionName = new Text(name);
    sectionName.setStyle("-fx-font-size: 15");
    return sectionName;
  }

  private TextFlow kv(String key, Object value) {
    TextFlow flow = new TextFlow();

    Text keyField = new Text("  " + key + ": ");
    keyField.setStyle("-fx-font-weight: bold");

    Text valueField = new Text(value.toString());
    valueField.setStyle("-fx-font-weight: regular");

    flow.getChildren().addAll(keyField, valueField);
    return flow;
  }
}
