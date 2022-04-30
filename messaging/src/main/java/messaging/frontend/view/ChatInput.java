package messaging.frontend.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import messaging.frontend.model.ViewModel;
import messaging.protocol.Config;

import java.util.function.UnaryOperator;

public class ChatInput extends HBox {
    private final TextArea textArea;
    public ChatInput(ViewModel clientModel, String alias, MessageListView messageList) {
        var maxLength = Config.MAX_MESSAGE_LENGTH;
        var remainingChars = new Text(String.valueOf(maxLength));

        textArea = new TextArea();
        textArea.setPrefWidth(390);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(4);

        UnaryOperator<TextFormatter.Change> ensureMaxLength = c -> {
            if (c.isContentChange()) {
                if (c.getControlNewText().length() > maxLength) {
                    c.setText(c.getControlNewText().substring(0, maxLength));
                    c.setRange(0, c.getControlText().length());
                    remainingChars.setText("0");
                } else {
                    remainingChars.setText(String.valueOf(maxLength - c.getControlNewText().length()));
                }
            }
            return c;
        };
        textArea.setTextFormatter(new TextFormatter<>(ensureMaxLength));

        Runnable sendMessage = () -> {
            var message = textArea.getText().stripTrailing();
            if (!message.isEmpty() && clientModel.remainingSend(alias) > 0) {
                messageList.add(new MessageView(message, true));
                clientModel.sendMessage(alias, message);
            }
            textArea.setText("");
        };

        textArea.setOnKeyPressed(f -> {
            if (f.getCode().equals(KeyCode.ENTER)) {
                if (f.isShiftDown()) {
                    textArea.appendText("\n");
                } else {
                    sendMessage.run();
                }
            }
        });

        var button = new Button(">");
        button.setOnMouseClicked(f -> {
            sendMessage.run();
        });

        var vBox = new VBox(remainingChars, button);
        vBox.setAlignment(Pos.CENTER);

        getChildren().addAll(textArea, vBox);
    }
}
