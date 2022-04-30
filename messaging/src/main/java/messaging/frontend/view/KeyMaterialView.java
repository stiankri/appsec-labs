package messaging.frontend.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import messaging.frontend.ApplicationConfig;
import messaging.frontend.model.ViewModel;

public class KeyMaterialView extends HBox {
    Text sendValue;
    Text receiveValue;
    public KeyMaterialView(ViewModel clientModel, String alias) {
        var sendLabel = new Text("send: ");
        sendLabel.setFont(ApplicationConfig.getFont());

        this.sendValue = new Text();
        sendValue.setFont(ApplicationConfig.getFont());

        var receiveLabel = new Text(" receive: ");
        receiveLabel.setFont(ApplicationConfig.getFont());

        this.receiveValue = new Text();
        receiveValue.setFont(ApplicationConfig.getFont());

        var button = new Button("generate");
        button.setOnMouseClicked(f -> {
            clientModel.resetKeyMaterial(alias);
        });

        setRemainingSend(clientModel.remainingSend(alias));
        setRemainingReceive(clientModel.remainingReceive(alias));

        var spacer = new Region();
        spacer.setPrefWidth(10);
        getChildren().setAll(sendLabel, sendValue, receiveLabel, receiveValue, spacer, button);
        setAlignment(Pos.CENTER);
    }

    public void setRemainingReceive(int receiveLeft) {
        receiveValue.setText(Integer.toString(receiveLeft));
    }

    public void setRemainingSend(int sendLeft) {
        sendValue.setText(Integer.toString(sendLeft));
    }
}
