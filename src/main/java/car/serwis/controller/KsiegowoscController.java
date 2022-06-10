package car.serwis.controller;

import car.serwis.database.dao.FakturaDao;
import car.serwis.database.model.Faktura;
import car.serwis.database.model.Kontrahent;
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

public class KsiegowoscController implements Initializable {
    @FXML
    private TableColumn<Faktura, LocalDate> dataFakturaTableColumn;

    @FXML
    private Button exitButton;

    @FXML
    private TableView<Faktura> fakturaTableView;

    @FXML
    private TableColumn<Faktura, Long> idFakturaTableColumn;

    @FXML
    private TableColumn<Faktura, String> kontrahentFakturaTableColumn;

    @FXML
    private BorderPane ksiegowoscBorderPane;

    @FXML
    private TableColumn<Faktura, String> miejsceFakturaTableColumn;

    @FXML
    private Button minimalizeButton;

    @FXML
    private TableColumn<Faktura, String> numerFakturaTableColumn;

    @FXML
    private Text pracownikInfo;

    @FXML
    private TextField searchBarFaktura;

    ObservableList<Faktura> fakturaObservableList = FXCollections.observableArrayList();
    WindowManagement windowManagement = new WindowManagement();
    FakturaDao fakturaDao = new FakturaDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        windowManagement.initializeExitButton(exitButton,ksiegowoscBorderPane);
        windowManagement.initializeMinimalizeButton(minimalizeButton,ksiegowoscBorderPane);
        CurrentPracownik.setPracownikInfo(pracownikInfo);
        setObservableList();
        fillTables();
        addTableSettings();
    }

    @FXML
    private void addFakturaWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewFakturaWindow();
        if (UpdateStatus.isFakturaAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsFakturaAdded(false);
        }
    }

    private void setObservableList() {
        fakturaObservableList.clear();
        fakturaObservableList.addAll(fakturaDao.displayRecords());

    }

    private void fillTables() {
        // Faktura
        idFakturaTableColumn.setCellValueFactory(new PropertyValueFactory<>("idFaktura"));
        numerFakturaTableColumn.setCellValueFactory(new PropertyValueFactory<>("numerFaktury"));
        miejsceFakturaTableColumn.setCellValueFactory(new PropertyValueFactory<>("miejsceWystawienia"));
        dataFakturaTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataWystawienia"));
        kontrahentFakturaTableColumn.setCellValueFactory(new PropertyValueFactory<>("kontrahent"));



    }

    private void addTableSettings() {
        fakturaTableView.setEditable(true);
        fakturaTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        fakturaTableView.setItems(getSortedListFaktura());


    }

    private SortedList<Faktura> getSortedListFaktura() {
        SortedList<Faktura> sortedList = new SortedList<>(getFilteredListFaktura());
        sortedList.comparatorProperty().bind(fakturaTableView.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Faktura> getFilteredListFaktura() {
        FilteredList<Faktura> filteredList = new FilteredList<>(fakturaObservableList, b -> true);
        searchBarFaktura.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(faktura -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (faktura.getNumerFaktury().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (faktura.getDataWystawienia().toString().contains(lowerCaseFilter)) {
                        return true;
                    }  else if (faktura.getMiejsceWystawienia().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (faktura.getKontrahent().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else{
                        return faktura.getIdFaktura().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
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

