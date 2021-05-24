package controllers;

import domain.ReportDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.BugService;

public class AdminCtrl
{
    private BugService bugService;

    ObservableList<ReportDTO> model = FXCollections.observableArrayList();

    @FXML
    TableView<ReportDTO> reportsTable;

    @FXML
    TableColumn<ReportDTO,String> firstName, lastName, activity;

    @FXML
    ComboBox<Integer> monthPick;

    public void setService(BugService bugService){
        this.bugService = bugService;
        initModel();
    }

    public void initialize(){
        firstName.setCellValueFactory(new PropertyValueFactory<ReportDTO, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<ReportDTO, String>("lastName"));
        activity.setCellValueFactory(new PropertyValueFactory<ReportDTO, String>("activity"));
        reportsTable.setItems(model);
    }

    private void initModel(){
        monthPick.getItems().addAll(1,2,3,4,5,6,7,8,9,
                10,11,12);
    }


    public void handleMostBugsFound(ActionEvent actionEvent) {
        reportsTable.getColumns().get(2).setText("BugsFound");
        if(monthPick.getSelectionModel().getSelectedItem() != null){
            model.setAll(bugService.getActivityTesters(monthPick.getSelectionModel().getSelectedItem()));
        }
        else{
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", "No month selected!");
        }
    }

    public void handleMostBugsSolved(ActionEvent actionEvent) {
        reportsTable.getColumns().get(2).setText("BugsSolved");
        if(monthPick.getSelectionModel().getSelectedItem() != null){
            model.setAll(bugService.getActivityProgrammers(monthPick.getSelectionModel().getSelectedItem()));
        }
        else{
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", "No month selected!");
        }

    }
}
