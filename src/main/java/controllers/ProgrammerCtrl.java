package controllers;

import domain.Account;
import domain.Bug;
import domain.BugStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.BugService;
import utils.Observer;

import java.io.IOException;

public class ProgrammerCtrl implements Observer {

    private BugService bugService;
    Stage primaryStage;
    private Account account;

    ObservableList<Bug> model = FXCollections.observableArrayList();

    @FXML
    TableView<Bug> bugsExistent;

    @FXML
    TableColumn<Bug,String> name, description,date, status;

    public void setService(BugService bugService, Stage primaryStage, Account acc){
        this.bugService = bugService;
        this.primaryStage = primaryStage;
        this.account = acc;
        this.bugService.addObserver(this);
        initModel();
    }

    public void initialize(){
        name.setCellValueFactory(new PropertyValueFactory<Bug, String>("name"));
        description.setCellValueFactory(new PropertyValueFactory<Bug, String>("description"));
        date.setCellValueFactory(new PropertyValueFactory<Bug, String>("date"));
        status.setCellValueFactory(new PropertyValueFactory<Bug, String>("bugStatus"));
        bugsExistent.setItems(model);
    }

    private void initModel(){
        model.setAll(bugService.getBugsWithStatus(BugStatus.New));
    }

    @Override
    public void update() {
        initModel();
    }

    public void handleAssign(ActionEvent actionEvent) {
        Bug selectedBug = bugsExistent.getSelectionModel().getSelectedItem();
        if(selectedBug != null) {
            bugService.update(selectedBug.getId(), BugStatus.Assigned,
                    account.getEmployee().getId());
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmation", "Bug assigned!");
        }
        else
        {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", "No bug selected!");
        }
    }

    public void handleViewAssigned(ActionEvent actionEvent) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add bug");
        AnchorPane root;
        Scene scene = null;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/AssignedBugsView.fxml"));
        try {
            root=loader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AssignedBugsCtrl ctrl=loader.getController();
        ctrl.setService(bugService,dialogStage, account.getEmployee().getId());
        dialogStage.setScene(scene);
        dialogStage.show();
    }
}
