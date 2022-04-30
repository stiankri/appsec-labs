package messaging.frontend.view;

import javafx.scene.layout.VBox;
import messaging.frontend.model.ViewModel;

public class ChatView extends VBox {
  private final MessageListView messageList;
  private final KeyMaterialView keyMaterialView;

  public ChatView(ViewModel clientModel, String alias) {
    this.messageList = new MessageListView();

    setPrefWidth(420);
    setPrefHeight(800);
    ChatInput chatInput = new ChatInput(clientModel, alias, messageList);

    this.keyMaterialView = new KeyMaterialView(clientModel, alias);
    getChildren().addAll(messageList, chatInput, keyMaterialView);
  }

  public void addMessage(MessageView message) {
    messageList.add(message);
  }

  public void setRemainingReceive(int receiveLeft) {
    keyMaterialView.setRemainingReceive(receiveLeft);
  }

  public void setRemainingSend(int sendLeft) {
    keyMaterialView.setRemainingSend(sendLeft);
  }
}
