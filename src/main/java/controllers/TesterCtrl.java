package controllers;

import domain.Account;
import domain.Bug;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.BugService;
import utils.Observer;

import java.io.IOException;

public class TesterCtrl implements Observer {

    private BugService bugService;
    Stage primaryStage;
    private Account account;

    ObservableList<Bug> model = FXCollections.observableArrayList();

    @FXML
    TableView<Bug> bugsFoundTable;

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
        bugsFoundTable.setItems(model);
    }
    private void initModel(){
        model.setAll(bugService.getBugsFoundByTester(account.getEmployee().getId()));
    }

    public void handleAdd(ActionEvent actionEvent) {
        showAddBugView();
    }

    private void showAddBugView(){
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add bug");
        AnchorPane root;
        Scene scene = null;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/AddBug.fxml"));
        try {
            root=loader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddBugCtrl ctrl=loader.getController();
        ctrl.setService(bugService,dialogStage, account.getEmployee().getId());
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @Override
    public void update() {
        initModel();
    }

    public void handleViewFixed(ActionEvent actionEvent) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("View fixed bugs");
        AnchorPane root;
        Scene scene = null;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/BugsThatNeedCheckingView.fxml"));
        try {
            root=loader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FixedBugsCtrl ctrl=loader.getController();
        ctrl.setService(bugService,dialogStage, account.getEmployee().getId());
        dialogStage.setScene(scene);
        dialogStage.show();
    }
}
