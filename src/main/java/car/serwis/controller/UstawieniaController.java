package car.serwis.controller;

import car.serwis.database.dao.PracownikDao;
import car.serwis.database.dao.StanowiskoDao;
import car.serwis.database.model.Pracownik;
import car.serwis.database.model.Stanowisko;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import java.util.ResourceBundle;

public class UstawieniaController implements Initializable {
    @FXML
    private TableColumn<Pracownik, String> hasloPracownikColumn;

    @FXML
    private TableColumn<Pracownik, Long> idPracownikColumn;

    @FXML
    private TableColumn<Stanowisko, Long> idStanowiskoColumn;

    @FXML
    private TableColumn<Pracownik, String> imiePracownikColumn;

    @FXML
    private TableColumn<Pracownik, String> loginPracownikColumn;

    @FXML
    private TableColumn<Stanowisko, String> nazwaStanowiskoColumn;

    @FXML
    private TableColumn<Pracownik, String> nazwiskoPracownikColumn;

    @FXML
    private TableColumn<Pracownik, LocalDate> pracijeOdPracownikColumn;

    @FXML
    private TableView<Pracownik> pracownicyTable;

    @FXML
    private TextField searchPracownikBar;

    @FXML
    private TextField searchStanowiskoBar;

    @FXML
    private TableView<Stanowisko> stanowiskaTable;

    @FXML
    private TableColumn<Pracownik, String> stanowiskoPracownikColumn;

    @FXML
    private Button minimalizeButton;

    @FXML
    private Button exitButton;

    @FXML
    private BorderPane ustawieniaBorderPane;

    @FXML
    private Text pracownikInfo;


    PracownikDao pracownikDao = new PracownikDao();
    StanowiskoDao stanowiskoDao = new StanowiskoDao();
    ObservableList<Stanowisko> stanowiskaObservableList = FXCollections.observableArrayList();
    ObservableList<Pracownik> pracownicyObservableList = FXCollections.observableArrayList();
    WindowManagement windowManagement = new WindowManagement();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        windowManagement.initializeExitButton(exitButton,ustawieniaBorderPane);
        windowManagement.initializeMinimalizeButton(minimalizeButton,ustawieniaBorderPane);
        CurrentPracownik.setPracownikInfo(pracownikInfo);
        setObservableList();
        fillTable();
        addTableSettings();
        pracownicyTable.setPlaceholder(new Label("Brak danych!"));
        stanowiskaTable.setPlaceholder(new Label("Brak danych!"));
    }


    private void setObservableList() {
        stanowiskaObservableList.clear();
        stanowiskaObservableList.addAll(stanowiskoDao.getStanowiska());

        pracownicyObservableList.clear();
        pracownicyObservableList.addAll(pracownikDao.displayRecords());
    }


    private void fillTable() {
        // STANOWISKO
        idStanowiskoColumn.setCellValueFactory(new PropertyValueFactory<>("idStanowisko"));
        nazwaStanowiskoColumn.setCellValueFactory(new PropertyValueFactory<>("nazwaStanowiska"));
        nazwaStanowiskoColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // PRACOWNIK
        idPracownikColumn.setCellValueFactory(new PropertyValueFactory<>("idPracownik"));
        imiePracownikColumn.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwiskoPracownikColumn.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        loginPracownikColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        hasloPracownikColumn.setCellValueFactory(new PropertyValueFactory<>("haslo"));
        pracijeOdPracownikColumn.setCellValueFactory(new PropertyValueFactory<>("pracujeOd"));
        stanowiskoPracownikColumn.setCellValueFactory(new PropertyValueFactory<>("stanowisko"));
        imiePracownikColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nazwiskoPracownikColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        loginPracownikColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        hasloPracownikColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void addTableSettings() {
        stanowiskaTable.setEditable(true);
        stanowiskaTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        stanowiskaTable.setItems(getSortedListStanowiska());

        pracownicyTable.setEditable(true);
        pracownicyTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        pracownicyTable.setItems(getSortedListPracownicy());
    }

    // ############### PRACOWNIK ###############
    private SortedList<Pracownik> getSortedListPracownicy() {
        SortedList<Pracownik> sortedList = new SortedList<>(getFilteredListPracownicy());
        sortedList.comparatorProperty().bind(pracownicyTable.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Pracownik> getFilteredListPracownicy() {
        FilteredList<Pracownik> filteredList = new FilteredList<>(pracownicyObservableList, b -> true);
        searchPracownikBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(pracownik -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (pracownik.getImie().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (pracownik.getNazwisko().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (pracownik.getLogin().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (pracownik.getHaslo().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return pracownik.getIdPracownik().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
    }

    @FXML
    private void addPracownikWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewPracownikWindow();
        if (UpdateStatus.isPracownikAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsPracownikAdded(false);
        }
    }

    @FXML
    void deletePracownik(ActionEvent event) throws IOException {
        ObservableList<Pracownik> selectedRows = pracownicyTable.getSelectionModel().getSelectedItems();

        if (pracownicyTable.getSelectionModel().getSelectedItems().isEmpty()){
            AlertPopUp.successAlert("Nie wybrano pracownika do usunięcia!");
        }else{
            for (Pracownik pracownik : selectedRows) {
                pracownikDao.deletePracownik(pracownik);
            }
            refreshScreen(event);
            AlertPopUp.successAlert("Pracownik usunięty!");
        }
    }

    @FXML
    private void changeImiePracownik(TableColumn.CellEditEvent<Stanowisko, String> editEvent) {
        Pracownik selectedPracownik = pracownicyTable.getSelectionModel().getSelectedItem();
        selectedPracownik.setImie(editEvent.getNewValue().toString());
        pracownikDao.updatePracownik(selectedPracownik);
        AlertPopUp.successAlert("Zmieniono imie pracownika!");
    }

    @FXML
    private void changeNazwiskoPracownik(TableColumn.CellEditEvent<Stanowisko, String> editEvent) {
        Pracownik selectedPracownik = pracownicyTable.getSelectionModel().getSelectedItem();
        selectedPracownik.setNazwisko(editEvent.getNewValue().toString());
        pracownikDao.updatePracownik(selectedPracownik);
        AlertPopUp.successAlert("Zmieniono nazwisko pracownika!");
    }

    @FXML
    private void changeLoginPracownik(TableColumn.CellEditEvent<Stanowisko, String> editEvent) {
        Pracownik selectedPracownik = pracownicyTable.getSelectionModel().getSelectedItem();
        selectedPracownik.setLogin(editEvent.getNewValue().toString());
        pracownikDao.updatePracownik(selectedPracownik);
        AlertPopUp.successAlert("Zmieniono login pracownika!");
    }

    @FXML
    private void changeHasloPracownik(TableColumn.CellEditEvent<Stanowisko, String> editEvent) {
        Pracownik selectedPracownik = pracownicyTable.getSelectionModel().getSelectedItem();
        selectedPracownik.setHaslo(editEvent.getNewValue().toString());
        pracownikDao.updatePracownik(selectedPracownik);
        AlertPopUp.successAlert("Zmieniono hasło pracownika!");
    }


    // ################# STANOWISKO ####################
    private SortedList<Stanowisko> getSortedListStanowiska() {
        SortedList<Stanowisko> sortedList = new SortedList<>(getFilteredListStanowiska());
        sortedList.comparatorProperty().bind(stanowiskaTable.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Stanowisko> getFilteredListStanowiska() {
        FilteredList<Stanowisko> filteredList = new FilteredList<>(stanowiskaObservableList, b -> true);
        searchStanowiskoBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(stanowisko -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (stanowisko.getNazwaStanowiska().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return stanowisko.getIdStanowisko().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
    }

    @FXML
    private void addStanowiskoWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewStanowiskoWindow();
        if (UpdateStatus.isStanowiskoAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsStanowiskoAdded(false);
        }
    }

    @FXML
    void deleteStanowisko(ActionEvent event) throws IOException {
        ObservableList<Stanowisko> selectedRows = stanowiskaTable.getSelectionModel().getSelectedItems();

        if (stanowiskaTable.getSelectionModel().getSelectedItems().isEmpty()){
            AlertPopUp.successAlert("Nie wybrano stanowiska do usunięcia!");
        }else {
            for (Stanowisko stanowisko : selectedRows) {
                stanowiskoDao.deleteStanowisko(stanowisko);
            }
            refreshScreen(event);
            AlertPopUp.successAlert("Stanowisko usunięte!");
        }
    }

    @FXML
    private void changeNazwaStanowiska(TableColumn.CellEditEvent<Stanowisko, String> editEvent) {
        Stanowisko selectedStanowisko = stanowiskaTable.getSelectionModel().getSelectedItem();
        selectedStanowisko.setNazwaStanowiska(editEvent.getNewValue().toString());
        stanowiskoDao.updateStanowisko(selectedStanowisko);
        AlertPopUp.successAlert("Zmieniono nazwę stanowiska!");
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
    void showWarsztatScreen(ActionEvent event) throws IOException {
        SceneController.getWarsztatScene(event);
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
    void showPomocScreen(ActionEvent event) throws IOException {
        SceneController.getPomocScene(event);
    }

    @FXML
    void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getUstawieniaScene(event);
    }
}
