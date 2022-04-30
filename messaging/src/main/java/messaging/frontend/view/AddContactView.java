package messaging.frontend.view;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import messaging.frontend.model.ViewModel;

public class AddContactView extends HBox {
    public AddContactView(ViewModel clientModel) {
        var textField = new TextField();
        textField.setPrefWidth(300);
        var button = new Button("+");

        Runnable create = () -> {
            clientModel.createContact(textField.getText());
            textField.setText("");
        };

        button.setOnMouseClicked(f -> {
            create.run();
        });

        textField.setOnKeyPressed(f -> {
            if (f.getCode().equals(KeyCode.ENTER)) {
                create.run();
            }
        });

        setMinHeight(28);

        getChildren().setAll(textField, button);
    }
}
