package com.schibsted.security.labs.analysis;

import com.schibsted.security.labs.analysis.model.basic.FullyQualifiedClassName;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.schibsted.security.labs.analysis.gui.CodeView;
import com.schibsted.security.labs.analysis.gui.DataFlowView;
import com.schibsted.security.labs.analysis.gui.Integrator;

import java.util.List;
import java.util.Stack;

public class XrayApplication extends Application {
  private HBox hBox;
  private Integrator integrator;

  private Stack<CodeView> codeViewStack = new Stack<>();

  @Override
  public void start(Stage stage) {
    hBox = new HBox(new HBox(), new HBox());
    integrator = new Integrator();

    var fqcn = new FullyQualifiedClassName("com.schibsted.security.labs.analysis.testingground.multiple", "Main");
    set(fqcn);
    setUsages(List.of());

    var scene = new Scene(hBox, 2000, 1000);
    stage.setScene(scene);
    stage.setTitle("x-ray");
    stage.show();
  }

  public void setUsages(List<String> usages) {
    var currentDataFlowView = new DataFlowView(900, 1500, usages);

    hBox.getChildren().set(1, currentDataFlowView);
  }

  public void set(FullyQualifiedClassName fullyQualifiedClassName) {
    var codeView = integrator.getCodeView(fullyQualifiedClassName);

    if (codeView.isPresent()) {
      this.set(codeView.get());
      codeView.get().setParent(this);
      codeViewStack.push(codeView.get());
    }
  }

  public void back() {
    if (codeViewStack.size() > 1) {
      codeViewStack.pop();
      set(codeViewStack.peek());
    }
  }

  private void set(CodeView codeView) {
    ScrollPane scrollPane = new ScrollPane(codeView);
    hBox.getChildren().set(0, scrollPane);
  }

  public static void main(String[] args) {
    launch();
  }
}
