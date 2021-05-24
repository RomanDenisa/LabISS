package controllers;

import domain.validators.ValidatorException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.BugService;
import service.ServiceException;

public class AddBugCtrl{

    private BugService bugServ;
    private Stage stage;
    private Integer tId;

    @FXML
    TextField name;
    @FXML
    TextArea description;

    public void setService(BugService bugServ, Stage stage, Integer tId){
        this.bugServ = bugServ;
        this.stage = stage;
        this.tId = tId;
        this.name.setText("");
        this.description.setText("");
    }


    public void handleAdd(ActionEvent actionEvent) {
        String name1 = name.getText();
        String description1 = description.getText();
        try{
            bugServ.add(name1,description1,tId);
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"Success","Bug added succesffully!");
        }
        catch(ValidatorException | ServiceException ex){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Error",ex.getMessage());
        }
        stage.hide();
    }
}
