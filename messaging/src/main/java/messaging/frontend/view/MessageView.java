package messaging.frontend.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import messaging.frontend.ApplicationConfig;

public class MessageView extends HBox {
    private final boolean isMine;

    public MessageView(String message, boolean isMine) {
        this.isMine = isMine;

        var text = new Label(message);
        text.setWrapText(true);
        text.setFont(ApplicationConfig.getFont());
        text.setTextAlignment(TextAlignment.LEFT);

        var spacer = new Region();
        spacer.setPrefWidth(10);

        var wrapper = new HBox(text);
        var padding = 5;
        wrapper.setPadding(new Insets(padding, padding, padding, padding));
        var backgroundColor = isMine ? Color.LIGHTBLUE : Color.LIGHTGREY;
        var style = new BackgroundFill(backgroundColor, new CornerRadii(8), null);
        wrapper.setBackground(new Background(style));
        wrapper.setMaxWidth(270);

        if (isMine) {
            setAlignment(Pos.CENTER_RIGHT);
            getChildren().setAll(wrapper, spacer);
        } else {
            setAlignment(Pos.CENTER_LEFT);
            getChildren().setAll(spacer, wrapper);
        }
    }

    public boolean isMine() {
        return isMine;
    }
}
