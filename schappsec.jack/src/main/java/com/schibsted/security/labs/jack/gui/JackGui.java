package com.schibsted.security.labs.jack.gui;

import com.schibsted.security.labs.jack.ProcessTree;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class JackGui extends Application {
  boolean isFullscreen = false;

  @Override
  public void start(Stage stage) {
    int width = 1600;
    int height = 900;
    int widthOfInfoBox = 500;
    var nodeInfo = new NodeInfoView(widthOfInfoBox);

    var root = ProcessTree.getTree();
    var graphView = new GraphView(width, height, widthOfInfoBox, nodeInfo, root);
    var hBox = new HBox();
    var pane = new Pane(graphView);
    graphView.widthProperty().bind(hBox.widthProperty());
    graphView.heightProperty().bind(hBox.heightProperty());

    HBox.setHgrow(pane, Priority.ALWAYS);
    HBox.setHgrow(nodeInfo, Priority.ALWAYS);

    hBox.getChildren().addAll(pane, nodeInfo);
    Scene scene = new Scene(hBox, width, height);
    stage.setScene(scene);
    stage.setFullScreenExitHint("");
    stage.setTitle("JACK");

    hBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode() == KeyCode.F) {
        if (isFullscreen) {
          isFullscreen = false;
        } else {
          isFullscreen = true;
        }
        stage.setFullScreen(isFullscreen);
      }
    });

    stage.show();
  }

  public void run() {
    launch();
  }

  public static void main(String[] args) {
    launch();
  }
}
