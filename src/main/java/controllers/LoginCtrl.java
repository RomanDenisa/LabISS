package controllers;

import domain.Account;
import domain.validators.ValidatorException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.AccountService;
import service.BugService;
import service.ServiceException;

import java.io.IOException;

public class LoginCtrl {

    @FXML
    TextField email, password;

    Stage loginStage;

    private AccountService accServ;
    private BugService bugServ;

    public void setService(AccountService accServ,BugService bugServ, Stage primaryStage){
        this.accServ = accServ;
        this.bugServ = bugServ;
        this.loginStage = primaryStage;
        email.setText("");
        password.setText("");
    }

    public void handleLogin(ActionEvent actionEvent) {
        String email1 = email.getText();
        String pass1 = password.getText();
        try{
            Account acc = accServ.findAccount(email1,pass1);
            showMainView(acc);
        }
        catch(ValidatorException | ServiceException ex){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Error",ex.getMessage());
        }
    }

    private void showMainView(Account acc){
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Main View");
        AnchorPane root;
        Scene scene = null;
        switch(acc.getRole()){
            case Tester -> {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/TesterView.fxml"));
                try {
                    root=loader.load();
                    scene = new Scene(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TesterCtrl ctrl=loader.getController();
                ctrl.setService(bugServ,dialogStage,acc);
            }
            case Programmer -> {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/ProgrammerView.fxml"));
                try {
                    root=loader.load();
                    scene = new Scene(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProgrammerCtrl ctrl=loader.getController();
                ctrl.setService(bugServ,dialogStage,acc);
            }
            case Admin -> {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/AdminView.fxml"));
                try {
                    root=loader.load();
                    scene = new Scene(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AdminCtrl ctrl=loader.getController();
                ctrl.setService(bugServ);
            }
        }

        dialogStage.setScene(scene);
        dialogStage.show();
    }
}
