package controllers;

import domain.Bug;
import domain.BugStatus;
import domain.validators.ValidatorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.BugService;
import service.ServiceException;
import utils.Observer;

public class FixedBugsCtrl implements Observer {

    private BugService bugService;
    Stage primaryStage;
    private Integer testerId;

    ObservableList<Bug> model = FXCollections.observableArrayList();

    @FXML
    TableView<Bug> fixedBugs;

    @FXML
    TableColumn<Bug,String> name, description,date, status;

    @FXML
    TextField nameField;

    @FXML
    TextArea descriptionField,additionalInfoField;

    public void setService(BugService bugService, Stage primaryStage, Integer testerId){
        this.bugService = bugService;
        this.primaryStage = primaryStage;
        this.testerId = testerId;
        this.bugService.addObserver(this);
        initModel();
        nameField.setText("");
        descriptionField.setText("");
        additionalInfoField.setText("");
    }

    public void initialize(){
        name.setCellValueFactory(new PropertyValueFactory<Bug, String>("name"));
        description.setCellValueFactory(new PropertyValueFactory<Bug, String>("description"));
        date.setCellValueFactory(new PropertyValueFactory<Bug, String>("date"));
        status.setCellValueFactory(new PropertyValueFactory<Bug, String>("bugStatus"));
        fixedBugs.getSelectionModel().selectedItemProperty().addListener(e->handleSelection());
        fixedBugs.setItems(model);
    }

    private void handleSelection() {
        Bug selectedBug = fixedBugs.getSelectionModel().getSelectedItem();
        if(selectedBug != null) {
            nameField.setText(selectedBug.getName());
            descriptionField.setText(selectedBug.getDescription());
            additionalInfoField.setText(selectedBug.getAdditionalInfo());
        }
        else{
            nameField.setText("");
            descriptionField.setText("");
            additionalInfoField.setText("");
        }
    }

    private void initModel(){
        model.setAll(bugService.getBugsFixedFoundByTester(testerId));
    }

    public void handleUpdate(ActionEvent actionEvent) {
        Bug selectedBug = fixedBugs.getSelectionModel().getSelectedItem();
        if(selectedBug != null) {
            if(!additionalInfoField.getText().equals("")) {
                try {
                    bugService.update(selectedBug.getId(), BugStatus.Reopened, additionalInfoField.getText());
                    MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Success", "Additional info updated!");
                } catch (ValidatorException | ServiceException ex) {
                    MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", ex.getMessage());
                }
            }
            else{
                MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", "Additional info data missing!");
            }
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

    public void handleDelete(ActionEvent actionEvent) {
        Bug selectedBug = fixedBugs.getSelectionModel().getSelectedItem();
        if(selectedBug != null) {
            bugService.update(selectedBug.getId(), BugStatus.Solved);
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmation", "Bug now added to solved list!");
        }
        else
        {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", "No bug selected!");
        }
    }
}
