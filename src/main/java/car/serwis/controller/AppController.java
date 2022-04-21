package car.serwis.controller;

import car.serwis.database.dao.StanowiskoDao;
import car.serwis.database.model.Stanowisko;
import car.serwis.helpers.ScenePath;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private TableView<Stanowisko> StanowiskoTable;

    @FXML
    private TableColumn<Stanowisko, Long> idStanowiskoColumn;

    @FXML
    private TableColumn<Stanowisko, String> nazwaStanowiskoColumn;

    @FXML
    private Button openSceneNewPracownik;

    @FXML
    private Button openSceneNewStanowisko;

    @FXML
    private TextField searchBar;

    @FXML
    private Button deleteStanowiskoButton;

    @FXML
    private Tab tabUstawienia;

    StanowiskoDao stanowiskoDao = new StanowiskoDao();
    ObservableList<Stanowisko> stanowiskaObservableList = FXCollections.observableArrayList();



    /* ------------ USTAWIENIA -------------------- */


    private void refresh(){
        tabUstawienia.setOnSelectionChanged((x) -> {
            fillTable();
            addTableSettings();
            setStanowiskaObservableList();
        });
    }



    public void refreshWindow(){
        fillTable();
        addTableSettings();
        setStanowiskaObservableList();
    }


    private void openAddStanowiskoWindow(){
        openSceneNewStanowisko.setOnAction((x) ->{
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            try {
                Parent parent = FXMLLoader.load(getClass().getResource(ScenePath.ADD_STANOWISKO.getPath()));
                Scene scene = new Scene(parent,600,400);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        refreshWindow();
    }

    private void openAddPracownikWindow(){
        openSceneNewPracownik.setOnAction((x) ->{
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            try {
                Parent parent = FXMLLoader.load(getClass().getResource(ScenePath.ADD_PRACOWNIK.getPath()));
                Scene scene = new Scene(parent,600,400);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setStanowiskaObservableList() {
        stanowiskaObservableList.clear();
        stanowiskaObservableList.addAll(stanowiskoDao.getStanowiska());
    }

    private void fillTable() {
        idStanowiskoColumn.setCellValueFactory(new PropertyValueFactory<>("idStanowisko"));
        nazwaStanowiskoColumn.setCellValueFactory(new PropertyValueFactory<>("nazwa"));

        nazwaStanowiskoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void addTableSettings() {
       StanowiskoTable.setItems(getSortedList());
       StanowiskoTable.setEditable(true);
       StanowiskoTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    private void deleteStanowisko() {
        deleteStanowiskoButton.setOnAction((x) -> {
            ObservableList<Stanowisko> selectedRows = StanowiskoTable.getSelectionModel().getSelectedItems();
            for (Stanowisko stanowisko : selectedRows) {
                stanowiskoDao.deleteStanowisko(stanowisko);
                //refreshWindow();
            }
        });
    }


    private SortedList<Stanowisko> getSortedList() {
        SortedList<Stanowisko> sortedList = new SortedList<>(getFilteredList());
        sortedList.comparatorProperty().bind(StanowiskoTable.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Stanowisko> getFilteredList() {
        FilteredList<Stanowisko> filteredList = new FilteredList<>(stanowiskaObservableList, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(stanowisko -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (stanowisko.getNazwa().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return stanowisko.getIdStanowisko().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        openAddStanowiskoWindow();
        fillTable();
        addTableSettings();
        setStanowiskaObservableList();
        openAddPracownikWindow();
        deleteStanowisko();
        //refresh();

    }
}



