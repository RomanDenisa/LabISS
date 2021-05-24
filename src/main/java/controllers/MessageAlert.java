package controllers;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MessageAlert {

    static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }
}
