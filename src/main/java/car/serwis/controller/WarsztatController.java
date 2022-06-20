package car.serwis.controller;

import car.serwis.database.dao.ZlecenieDao;
import car.serwis.database.model.Zlecenie;
import car.serwis.helpers.AlertPopUp;
import car.serwis.helpers.CurrentPracownik;
import car.serwis.helpers.UpdateStatus;
import car.serwis.helpers.WindowManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class WarsztatController implements Initializable {

    @FXML
    private TableColumn<Zlecenie, LocalDate> dataPrzyjeciaMojeZleceniaTableColumn;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Zlecenie, Long> idMojeZleceniaTableColumn;

    @FXML
    private Button minimalizeButton;

    @FXML
    private TableView<Zlecenie> mojeZleceniaTableView;

    @FXML
    private TableColumn<Zlecenie, String> opisMojeZleceniaTableColumn;

    @FXML
    private Text pracownikInfo;

    @FXML
    private TableColumn<Zlecenie, String> samochodMojeZleceniaTableColumn;

    @FXML
    private TextField searchMojeZleceniaTableColumn;

    @FXML
    private TableColumn<Zlecenie, Enum> statusMojeZleceniaTableColumn;

    @FXML
    private BorderPane warsztatBorderPane;

    @FXML
    private TableColumn<Zlecenie, LocalDate> dataPrzyjeciaWszystkieZleceniaTableColumn;

    @FXML
    private TableColumn<Zlecenie, Long> idWszystkieZleceniaTableColumn;

    @FXML
    private TableColumn<Zlecenie, String> opisWszystkieZleceniaTableColumn;

    @FXML
    private Button przyjmijZlecenieButton;

    @FXML
    private TableColumn<Zlecenie, String> samochodWszystkieZleceniaTableColumn;

    @FXML
    private TextField searchWszystkieZleceniaTableColumn;

    @FXML
    private TableColumn<Zlecenie, Enum> statusWszystkieZleceniaTableColumn;

    @FXML
    private TableView<Zlecenie> wszystkieZleceniaTableView;

    @FXML
    private Button zmienStatusButton;

    WindowManagement windowManagement = new WindowManagement();
    ZlecenieDao zlecenieDao = new ZlecenieDao();
    ObservableList<Zlecenie> mojeZleceniaObservableList = FXCollections.observableArrayList();
    ObservableList<Zlecenie> wszystkieZleceniaObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        windowManagement.initializeExitButton(exitButton, warsztatBorderPane);
        windowManagement.initializeMinimalizeButton(minimalizeButton, warsztatBorderPane);
        CurrentPracownik.setPracownikInfo(pracownikInfo);
        setObservableList();
        fillTables();
        addTableSettings();
    }

    private void setObservableList() {
        mojeZleceniaObservableList.clear();
        mojeZleceniaObservableList.addAll(zlecenieDao.getMechanikZlecenia());

        wszystkieZleceniaObservableList.clear();
        wszystkieZleceniaObservableList.addAll(zlecenieDao.getDostepneZlecenia());

    }


    private void fillTables() {
        // MOJE ZLECENIA
        idMojeZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("idZlecenie"));
        dataPrzyjeciaMojeZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataPrzyjecia"));
        opisMojeZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("opisZlecenie"));
        statusMojeZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        samochodMojeZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("samochod"));

        // WSZYSTKIE ZLECENIA
        idWszystkieZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("idZlecenie"));
        dataPrzyjeciaWszystkieZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataPrzyjecia"));
        opisWszystkieZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("opisZlecenie"));
        statusWszystkieZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        samochodWszystkieZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("samochod"));

    }

    private void addTableSettings() {
        mojeZleceniaTableView.setEditable(true);
        mojeZleceniaTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        mojeZleceniaTableView.setItems(getSortedListMojeZlecenia());

        wszystkieZleceniaTableView.setEditable(true);
        wszystkieZleceniaTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        wszystkieZleceniaTableView.setItems(getSortedListWszystkieZlecenia());

    }

    // #################### MOJE ZLECENIA ####################

    private SortedList<Zlecenie> getSortedListMojeZlecenia() {
        SortedList<Zlecenie> sortedList = new SortedList<>(getFilteredListMojeZlecenia());
        sortedList.comparatorProperty().bind(mojeZleceniaTableView.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Zlecenie> getFilteredListMojeZlecenia() {
        FilteredList<Zlecenie> filteredList = new FilteredList<>(mojeZleceniaObservableList, b -> true);
        searchMojeZleceniaTableColumn.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(zlecenie -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (zlecenie.getDataPrzyjecia().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (zlecenie.getOpisZlecenie().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (zlecenie.getStatus().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (zlecenie.getSamochod().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return zlecenie.getIdZlecenie().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
    }

    @FXML
    private void changeStatusWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewStatusWindow();
        if (UpdateStatus.isStatusUpdated()) {
            refreshScreen(event);
            UpdateStatus.setIsStatusUpdated(false);
        }
    }

    // ############ WSZYSTKIE ZLECENIA ##################

    private SortedList<Zlecenie> getSortedListWszystkieZlecenia() {
        SortedList<Zlecenie> sortedList = new SortedList<>(getFilteredListWszyskieZlecenia());
        sortedList.comparatorProperty().bind(wszystkieZleceniaTableView.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Zlecenie> getFilteredListWszyskieZlecenia() {
        FilteredList<Zlecenie> filteredList = new FilteredList<>(wszystkieZleceniaObservableList, b -> true);
        samochodWszystkieZleceniaTableColumn.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(zlecenie -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (zlecenie.getDataPrzyjecia().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (zlecenie.getOpisZlecenie().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (zlecenie.getStatus().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (zlecenie.getSamochod().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return zlecenie.getIdZlecenie().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
    }

    @FXML
    public void przyjmijZlecenie(ActionEvent event) throws IOException {
        ObservableList<Zlecenie> selectedRows = wszystkieZleceniaTableView.getSelectionModel().getSelectedItems();
        if (wszystkieZleceniaTableView.getSelectionModel().getSelectedItems().isEmpty()){
            AlertPopUp.successAlert("Nie wybrano zlecenia!");
        }else {
            for (Zlecenie zlecenie : selectedRows) {
                zlecenie.setPracownik(CurrentPracownik.getCurrentPracownik());
                zlecenieDao.updateZlecenie(zlecenie);
            }
            refreshScreen(event);
            AlertPopUp.successAlert("Zlecenie przyjęte!");
        }
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
