package car.serwis.controller;

import car.serwis.database.dao.PracownikDao;
import car.serwis.database.dao.StanowiskoDao;
import car.serwis.database.model.Pracownik;
import car.serwis.database.model.Stanowisko;
import car.serwis.helpers.UpdateStatus;
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

    PracownikDao pracownikDao = new PracownikDao();
    StanowiskoDao stanowiskoDao = new StanowiskoDao();
    ObservableList<Stanowisko> stanowiskaObservableList = FXCollections.observableArrayList();
    ObservableList<Pracownik> pracownicyObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setStanowiskaObservableList();
        setPracownicyObservableList();
        fillTableStanowiska();
        fillTablePracownicy();
        addTableSettingsStanowisko();
        addTableSettingsPracownik();
        pracownicyTable.setPlaceholder(new Label("Brak danych!"));
        stanowiskaTable.setPlaceholder(new Label("Brak danych!"));
    }


    private void setStanowiskaObservableList() {
        stanowiskaObservableList.clear();
        stanowiskaObservableList.addAll(stanowiskoDao.getStanowiska());
    }

    private void setPracownicyObservableList() {
        pracownicyObservableList.clear();
        pracownicyObservableList.addAll(pracownikDao.getPracownicy());
    }

    private void fillTableStanowiska() {
        idStanowiskoColumn.setCellValueFactory(new PropertyValueFactory<>("idStanowisko"));
        nazwaStanowiskoColumn.setCellValueFactory(new PropertyValueFactory<>("nazwa"));

        nazwaStanowiskoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void fillTablePracownicy() {
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

    private void addTableSettingsStanowisko() {
        stanowiskaTable.setEditable(true);
        stanowiskaTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        stanowiskaTable.setItems(getSortedListStanowiska());
    }

    private void addTableSettingsPracownik() {
        pracownicyTable.setEditable(true);
        pracownicyTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        pracownicyTable.setItems(getSortedListPracownicy());
    }


    private SortedList<Stanowisko> getSortedListStanowiska() {
        SortedList<Stanowisko> sortedList = new SortedList<>(getFilteredListStanowiska());
        sortedList.comparatorProperty().bind(stanowiskaTable.comparatorProperty());
        return sortedList;
    }

    private SortedList<Pracownik> getSortedListPracownicy() {
        SortedList<Pracownik> sortedList = new SortedList<>(getFilteredListPracownicy());
        sortedList.comparatorProperty().bind(pracownicyTable.comparatorProperty());
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

                    if (stanowisko.getNazwa().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return stanowisko.getIdStanowisko().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
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
                    }  else if (pracownik.getLogin().toLowerCase().contains(lowerCaseFilter)) {
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
    void deleteStanowisko(ActionEvent event) throws IOException {
        ObservableList<Stanowisko> selectedRows = stanowiskaTable.getSelectionModel().getSelectedItems();
        for (Stanowisko stanowisko : selectedRows) {
            stanowiskoDao.deleteStanowisko(stanowisko);
        }
        refreshScreen(event);
    }

    @FXML
    void deletePracownik(ActionEvent event) throws IOException {
        ObservableList<Pracownik> selectedRows = pracownicyTable.getSelectionModel().getSelectedItems();
        for (Pracownik pracownik : selectedRows) {
            pracownikDao.deletePracownik(pracownik);
        }
        refreshScreen(event);
    }



    @FXML
    private void changeNazwaStanowiska(TableColumn.CellEditEvent<Stanowisko, String> editEvent) {
        Stanowisko selectedNazwa = stanowiskaTable.getSelectionModel().getSelectedItem();
        selectedNazwa.setNazwa(editEvent.getNewValue().toString());
        stanowiskoDao.updateStanowisko(selectedNazwa);
    }


    @FXML
    private void addStanowiskoWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewStanowiskoWindow();
        if(UpdateStatus.isStanowiskoAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsStanowiskoAdded(false);
        }
    }

    @FXML
    private void addPracownikWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewPracownikWindow();
        if(UpdateStatus.isPracownikAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsPracownikAdded(false);
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
