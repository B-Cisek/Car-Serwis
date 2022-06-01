package car.serwis.controller;

import car.serwis.database.dao.ZlecenieDao;
import car.serwis.database.model.*;
import car.serwis.helpers.CurrentPracownik;
import car.serwis.helpers.UpdateStatus;
import car.serwis.helpers.WindowManagement;
import car.serwis.helpers.ZlecenieStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WarsztatController implements Initializable {


    @FXML
    private TableColumn<Zlecenie, String> dataPrzyjeciaMechanikZlecenia;

    @FXML
    private TableColumn<Zlecenie, String> dataPrzyjeciaWszystkieZlecenia;

    @FXML
    private Button exitButton;

    @FXML
    private BorderPane warsztatBorderPane;

    @FXML
    private Button minimalizeButton;

    @FXML
    private TableColumn<Zlecenie, Long> idMechanikZlecenia;

    @FXML
    private TableColumn<Zlecenie, Long> idWszystkieZlecenia;

    @FXML
    private TableView<Zlecenie> mechanikZleceniaTableView;

    @FXML
    private TableColumn<Zlecenie, String> opisMechanikZlecenia;

    @FXML
    private TableColumn<Zlecenie, String> opisWszystkieZlecenia;

    @FXML
    private Text pracownikInfo;

    @FXML
    private TableColumn<Zlecenie, String> pracownikMechanikZlecenia;

    @FXML
    private TableColumn<Zlecenie, String> pracownikWszystkieZlecenia;

    @FXML
    private Button przyjmijZlecenie;

    @FXML
    private TableColumn<Zlecenie, String> samochodMechanikZlecenia;

    @FXML
    private TableColumn<Zlecenie, String> samochodWszystkieZlecenia;

    @FXML
    private TableColumn<Zlecenie, String> statusMechanikZlecenia;

    @FXML
    private TableColumn<Zlecenie, String> statusWszystkieZlecenia;

    @FXML
    private TableView<Zlecenie> wszystkieZleceniaTableView;

    @FXML
    private Button zmienStatus;

    @FXML
    private TextField searchMechanikZlecenia;

    @FXML
    private TextField searchWszystkieZlecenia;

    ZlecenieDao zlecenieDao = new ZlecenieDao();
    ObservableList<Zlecenie> wszystkieZleceniaObservableList = FXCollections.observableArrayList();
    ObservableList<Zlecenie> mechanikZleceniaObservableList = FXCollections.observableArrayList();
    WindowManagement windowManagement = new WindowManagement();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        windowManagement.initializeMinimalizeButton(minimalizeButton,warsztatBorderPane);
        windowManagement.initializeExitButton(exitButton,warsztatBorderPane);
        CurrentPracownik.setPracownikInfo(pracownikInfo);
        setObservableList();
        fillTables();
        addTableSettings();
    }


    private void setObservableList() {
        wszystkieZleceniaObservableList.clear();
        wszystkieZleceniaObservableList.addAll(zlecenieDao.getZlecenie());

        mechanikZleceniaObservableList.clear();
        mechanikZleceniaObservableList.addAll(zlecenieDao.getZleceniaForPracownik());

    }



    private void fillTables() {
        // WSZYSTKIE ZLECENIA
        idWszystkieZlecenia.setCellValueFactory(new PropertyValueFactory<>("idZlecenie"));
        dataPrzyjeciaWszystkieZlecenia.setCellValueFactory(new PropertyValueFactory<>("dataPrzyjecia"));
        opisWszystkieZlecenia.setCellValueFactory(new PropertyValueFactory<>("opisZlecenie"));
        samochodWszystkieZlecenia.setCellValueFactory(new PropertyValueFactory<>("samochod"));
        pracownikWszystkieZlecenia.setCellValueFactory(new PropertyValueFactory<>("pracownik"));
        statusWszystkieZlecenia.setCellValueFactory(new PropertyValueFactory<>("status"));



        // MECHANIK ZLECENIA
        idMechanikZlecenia.setCellValueFactory(new PropertyValueFactory<>("idZlecenie"));
        dataPrzyjeciaMechanikZlecenia.setCellValueFactory(new PropertyValueFactory<>("dataPrzyjecia"));
        opisMechanikZlecenia.setCellValueFactory(new PropertyValueFactory<>("opisZlecenie"));
        samochodMechanikZlecenia.setCellValueFactory(new PropertyValueFactory<>("samochod"));
        pracownikMechanikZlecenia.setCellValueFactory(new PropertyValueFactory<>("pracownik"));
        statusMechanikZlecenia.setCellValueFactory(new PropertyValueFactory<>("status"));

        opisMechanikZlecenia.setCellFactory(TextFieldTableCell.forTableColumn());
        statusMechanikZlecenia.setCellValueFactory(statusMechanikZlecenia.getCellValueFactory());



    }


    private void addTableSettings() {
        mechanikZleceniaTableView.setEditable(true);
        mechanikZleceniaTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        mechanikZleceniaTableView.setItems(getSortedListZleceniaWszystkie());

        wszystkieZleceniaTableView.setEditable(true);
        wszystkieZleceniaTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        wszystkieZleceniaTableView.setItems(getSortedListZleceniaMechanik());
    }

    private SortedList<Zlecenie> getSortedListZleceniaWszystkie() {
        SortedList<Zlecenie> sortedList = new SortedList<>(getFilteredListZleceniaWszystkie());
        sortedList.comparatorProperty().bind(wszystkieZleceniaTableView.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Zlecenie> getFilteredListZleceniaWszystkie() {
        FilteredList<Zlecenie> filteredList = new FilteredList<>(wszystkieZleceniaObservableList, b -> true);
        searchWszystkieZlecenia.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(zlecenie -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (zlecenie.getDataPrzyjecia().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else if (zlecenie.getOpisZlecenie().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }  else if (zlecenie.getStatus().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else if (zlecenie.getSamochod().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else if (zlecenie.getPracownik().toString().contains(lowerCaseFilter)){
                        return true;
                    }else{
                        return zlecenie.getIdZlecenie().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
    }

    private SortedList<Zlecenie> getSortedListZleceniaMechanik() {
        SortedList<Zlecenie> sortedList = new SortedList<>(getFilteredListZleceniaMechanik());
        sortedList.comparatorProperty().bind(mechanikZleceniaTableView.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Zlecenie> getFilteredListZleceniaMechanik() {
        FilteredList<Zlecenie> filteredList = new FilteredList<>(mechanikZleceniaObservableList, b -> true);
        searchMechanikZlecenia.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(zlecenie -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (zlecenie.getDataPrzyjecia().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else if (zlecenie.getOpisZlecenie().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }  else if (zlecenie.getStatus().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else if (zlecenie.getSamochod().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else if (zlecenie.getPracownik().toString().contains(lowerCaseFilter)){
                        return true;
                    }else{
                        return zlecenie.getIdZlecenie().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
    }

    @FXML
    private void changeStatus(TableColumn.CellEditEvent<Zlecenie, ZlecenieStatus> editEvent) {
        Zlecenie selectedZlecenie = mechanikZleceniaTableView.getSelectionModel().getSelectedItem();
        selectedZlecenie.setStatus(ZlecenieStatus.valueOf(editEvent.getNewValue().toString()));
        zlecenieDao.updateZlecenie(selectedZlecenie);
    }

    @FXML
    private void addUpdateStatus(ActionEvent event) throws IOException {
        NewWindowController.getUpdateStatus();
        if(UpdateStatus.isStatusUpdated()) {
            refreshScreen(event);
            UpdateStatus.setIsStatusUpdated(false);
        }
    }

    @FXML
    void updateStatus(ActionEvent event) throws IOException {
        ObservableList<Zlecenie> selectedRows = mechanikZleceniaTableView.getSelectionModel().getSelectedItems();
        NewWindowController.getUpdateStatus();
        for (Zlecenie zlecenie : selectedRows) {
            zlecenieDao.updateZlecenie(zlecenie);
        }
        refreshScreen(event);
    }










    @FXML
    void showPulpitScreen(ActionEvent event) throws IOException {
        SceneController.getPulpitScene(event);
    }

    @FXML
    void showZleceniaScreen(ActionEvent event) throws IOException {
        SceneController.getZleceniaScene(event);
    }


    @FXML
    void showKsiegowoscScreen(ActionEvent event) throws IOException {
        SceneController.getKsiegowoscScene(event);
    }

    @FXML
    void showMagazynScreen(ActionEvent event) throws IOException {
        SceneController.getMagazynScene(event);
    }

    @FXML
    void showUstawieniaScreen(ActionEvent event) throws IOException {
        SceneController.getUstawieniaScene(event);
    }

    @FXML
    void showPomocScreen(ActionEvent event) throws IOException {
        SceneController.getPomocScene(event);
    }

    @FXML
    void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getWarsztatScene(event);
    }
}
