package messaging.frontend;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import messaging.frontend.model.ViewModel;
import messaging.frontend.view.AddContactView;

public class MessagingApplication extends Application {
    private final ViewModel clientModel;

    public MessagingApplication() {
        this.clientModel = new ViewModel();
    }

    @Override
    public void start(Stage stage) {
        clientModel.start();

        var main = new HBox();
        var contactsColumn = new VBox();

        var contacts = new ListView<String>();
        contacts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            var newChat = clientModel.getChat(newValue);
            main.getChildren().setAll(contactsColumn, newChat);
        });

        contacts.setItems(clientModel.getContacts());
        contacts.setPrefHeight(1500);

        var addContact = new AddContactView(clientModel);
        var contactsLabel = new Text("Contacts");
        contactsLabel.setFont(ApplicationConfig.getFont());
        contactsColumn.getChildren().setAll(contactsLabel, contacts, addContact);
        contactsColumn.setAlignment(Pos.CENTER);

        contacts.getSelectionModel().select(0);
        var current = contacts.getSelectionModel().getSelectedItem();

        main.getChildren().setAll(contactsColumn, clientModel.getChat(current));

        var width = 670;
        var scene = new Scene(main, width, 500);
        stage.setScene(scene);
        stage.setTitle("messaging");
        stage.show();
        stage.setMinWidth(width);
        stage.setMaxWidth(width);
        stage.setMinHeight(400);
        stage.setOnCloseRequest((c) -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
