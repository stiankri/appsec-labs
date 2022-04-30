package messaging.frontend.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MessageListView extends VBox {
    private List<MessageView> messages = new ArrayList<>();
    private final VBox vBox;
    private final ScrollPane scrollPane;

    public MessageListView() {
        this.vBox = new VBox();
        this.scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVvalue(1.0);
        scrollPane.setPrefHeight(1500);
        getChildren().add(scrollPane);
    }

    public void add(MessageView message) {
        if (messages.isEmpty()) {
            var verticalSpace = new Region();
            verticalSpace.setPrefHeight(5);
            vBox.getChildren().add(verticalSpace);
        }

        if (!messages.isEmpty() && messages.get(messages.size()-1).isMine() != message.isMine()) {
            var verticalSpace = new Region();
            verticalSpace.setPrefHeight(15);
            vBox.getChildren().add(verticalSpace);
        }

        vBox.getChildren().add(message);

        var verticalSpace = new Region();
        verticalSpace.setPrefHeight(5);
        vBox.getChildren().add(verticalSpace);

        messages.add(message);

        scrollPane.applyCss();
        scrollPane.layout();
        scrollPane.setVvalue(scrollPane.getVmax());
    }
}
