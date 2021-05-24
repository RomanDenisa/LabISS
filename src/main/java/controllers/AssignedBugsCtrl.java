package controllers;

import domain.Account;
import domain.Bug;
import domain.BugStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.BugService;
import utils.Observer;

public class AssignedBugsCtrl implements Observer {

    private BugService bugService;
    Stage primaryStage;
    private Integer programmerId;

    ObservableList<Bug> model = FXCollections.observableArrayList();

    @FXML
    TableView<Bug> assignedBugs;

    @FXML
    TextArea aditionalInfo;

    @FXML
    TableColumn<Bug,String> name, description,date, status;

    public void setService(BugService bugService, Stage primaryStage, Integer programmerId){
        this.bugService = bugService;
        this.primaryStage = primaryStage;
        this.programmerId = programmerId;
        this.bugService.addObserver(this);
        initModel();
    }

    public void initialize(){
        name.setCellValueFactory(new PropertyValueFactory<Bug, String>("name"));
        description.setCellValueFactory(new PropertyValueFactory<Bug, String>("description"));
        date.setCellValueFactory(new PropertyValueFactory<Bug, String>("date"));
        status.setCellValueFactory(new PropertyValueFactory<Bug, String>("bugStatus"));
        assignedBugs.getSelectionModel().selectedItemProperty().addListener(e->handleSelection());
        assignedBugs.setItems(model);
    }

    private void handleSelection() {
        Bug selectedBug = assignedBugs.getSelectionModel().getSelectedItem();
        if(selectedBug != null) {
            aditionalInfo.setText(selectedBug.getAdditionalInfo());
        }
        else{
            aditionalInfo.setText("");
        }
    }

    private void initModel(){
        model.setAll(bugService.getBugsFoundByProgrammer(programmerId));
    }

    public void handleFixBug(ActionEvent actionEvent) {
        Bug selectedBug = assignedBugs.getSelectionModel().getSelectedItem();
        if(selectedBug != null) {
            bugService.update(selectedBug.getId(), BugStatus.Fixed,
                    programmerId);
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmation", "Bug fixed!");
        }
        else
        {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", "No bug selected!");
        }
    }

    @Override
    public void update() {
        initModel();
    }
}
