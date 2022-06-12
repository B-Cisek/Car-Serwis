package car.serwis.controller;

import car.serwis.database.dao.KontrahentDao;
import car.serwis.database.dao.ZlecenieDao;
import car.serwis.database.model.Kontrahent;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;

import java.time.LocalDate;
import java.util.ResourceBundle;

public class ZleceniaController implements Initializable {
    @FXML
    private Button addKontrahent;

    @FXML
    private Button addZlecenie;

    @FXML
    private TableColumn<Zlecenie, LocalDate> dataPrzyjeciaZlecenieTableColumn;

    @FXML
    private Button deleteKontrahent;

    @FXML
    private Button deleteZlecenie;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Zlecenie, Long> idZlecenieTableColumn;

    @FXML
    private TableColumn<Kontrahent, Long> idkontrahentTableView;

    @FXML
    private TableColumn<Kontrahent, String> imieKontrahentTableColumn;

    @FXML
    private TableColumn<Zlecenie, String> klientZlecenieTableColumn;

    @FXML
    private TableView<Kontrahent> kontrahentTableView;

    @FXML
    private TableColumn<Zlecenie, String> samochodZlecenieTableColumn;

    @FXML
    private TableColumn<Kontrahent, String> nazwaFirmyKontrahentTableColumn;

    @FXML
    private TableColumn<Kontrahent, String> nazwiskoKontrahentTableColumn;

    @FXML
    private TableColumn<Kontrahent, String> nipKontrahentTableColumn;

    @FXML
    private TableColumn<Zlecenie, String> opisZlecenieTableColumn;

    @FXML
    private TableColumn<Kontrahent, String> peselKontrahentTableColumn;

    @FXML
    private Text pracownikInfo;

    @FXML
    private TableColumn<Zlecenie, String> pracownikZlecenieTableColumn;

    @FXML
    private TableColumn<Zlecenie, Enum> statusZlecenieTableColumn;

    @FXML
    private TableView<Zlecenie> zleceniaTableView;

    @FXML
    private TextField searchKontrahentBar;

    @FXML
    private TextField searchZlecenieBar;

    @FXML
    private BorderPane zlecenieBorderPane;

    @FXML
    private Button minimalizeButton;



    KontrahentDao kontrahentDao = new KontrahentDao();
    ZlecenieDao zlecenieDao = new ZlecenieDao();
    WindowManagement windowManagement = new WindowManagement();
    ObservableList<Kontrahent> kontrahentciObservableList = FXCollections.observableArrayList();
    ObservableList<Zlecenie> zlecenieObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        windowManagement.initializeExitButton(exitButton,zlecenieBorderPane);
        windowManagement.initializeMinimalizeButton(minimalizeButton,zlecenieBorderPane);
        CurrentPracownik.setPracownikInfo(pracownikInfo);
        setObservableList();
        fillTables();
        addTableSettings();
    }



    private void setObservableList() {
        kontrahentciObservableList.clear();
        kontrahentciObservableList.addAll(kontrahentDao.getKontrahent());

        zlecenieObservableList.clear();
        zlecenieObservableList.addAll(zlecenieDao.getZlecenie());
    }

    private void fillTables() {
        // Kontrahent
        idkontrahentTableView.setCellValueFactory(new PropertyValueFactory<>("idKontrahent"));
        imieKontrahentTableColumn.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwiskoKontrahentTableColumn.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        nazwaFirmyKontrahentTableColumn.setCellValueFactory(new PropertyValueFactory<>("nazwaFirmy"));
        nipKontrahentTableColumn.setCellValueFactory(new PropertyValueFactory<>("nip"));
        peselKontrahentTableColumn.setCellValueFactory(new PropertyValueFactory<>("pesel"));

        imieKontrahentTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nazwiskoKontrahentTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nazwaFirmyKontrahentTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nipKontrahentTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        peselKontrahentTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Zlecenie

        idZlecenieTableColumn.setCellValueFactory(new PropertyValueFactory<>("idZlecenie"));
        dataPrzyjeciaZlecenieTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataPrzyjecia"));
        opisZlecenieTableColumn.setCellValueFactory(new PropertyValueFactory<>("opisZlecenie"));
        statusZlecenieTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        samochodZlecenieTableColumn.setCellValueFactory(new PropertyValueFactory<>("samochod"));
        klientZlecenieTableColumn.setCellValueFactory(new PropertyValueFactory<>("kontrahent"));
        pracownikZlecenieTableColumn.setCellValueFactory(new PropertyValueFactory<>("pracownik"));

        opisZlecenieTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());

    }

    private void addTableSettings() {
        kontrahentTableView.setEditable(true);
        kontrahentTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        kontrahentTableView.setItems(getSortedListKontrahent());


        zleceniaTableView.setEditable(true);
        zleceniaTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        zleceniaTableView.setItems(getSortedListZlecenie());
    }


    // #################### KONTRAHENT ####################

    @FXML
    private void addKontrahentWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewKontrahentWindow();
        if(UpdateStatus.isKontrahentAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsKontrahentAdded(false);
        }
    }

    private SortedList<Kontrahent> getSortedListKontrahent() {
        SortedList<Kontrahent> sortedList = new SortedList<>(getFilteredListKontrahent());
        sortedList.comparatorProperty().bind(kontrahentTableView.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Kontrahent> getFilteredListKontrahent() {
        FilteredList<Kontrahent> filteredList = new FilteredList<>(kontrahentciObservableList, b -> true);
        searchKontrahentBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(kontrahent -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (kontrahent.getImie().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (kontrahent.getNazwisko().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }  else if (kontrahent.getNazwaFirmy().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (kontrahent.getNip().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else if (kontrahent.getPesel().toString().contains(lowerCaseFilter)){
                        return true;
                    }else{
                        return kontrahent.getIdKontrahent().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
    }

    @FXML
    void deleteKontrahent(ActionEvent event) throws IOException {
        ObservableList<Kontrahent> selectedRows = kontrahentTableView.getSelectionModel().getSelectedItems();
        for (Kontrahent kontrahent : selectedRows) {
            kontrahentDao.deleteKontrahent(kontrahent);
        }
        refreshScreen(event);
        AlertPopUp.successAlert("Kontrahent usunięty");
    }


    // ########### ZLECENIE ####################

    @FXML
    private void addZlecenieWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewZlecenieWindow();
        if(UpdateStatus.isZlecenieAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsZlecenieAdded(false);
        }
    }

    private SortedList<Zlecenie> getSortedListZlecenie() {
        SortedList<Zlecenie> sortedList = new SortedList<>(getFilteredListZlecenie());
        sortedList.comparatorProperty().bind(zleceniaTableView.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Zlecenie> getFilteredListZlecenie() {
        FilteredList<Zlecenie> filteredList = new FilteredList<>(zlecenieObservableList, b -> true);
        searchZlecenieBar.textProperty().addListener((observable, oldValue, newValue) ->
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
                    } else if (zlecenie.getKontrahent().toString().contains(lowerCaseFilter)){
                        return true;
                    }else{
                        return zlecenie.getIdZlecenie().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
    }

    @FXML
    void deleteZlecenie(ActionEvent event) throws IOException {
        ObservableList<Zlecenie> selectedRows = zleceniaTableView.getSelectionModel().getSelectedItems();
        for (Zlecenie zlecenie : selectedRows) {
            zlecenieDao.deleteZlecenie(zlecenie);
        }
        refreshScreen(event);
        AlertPopUp.successAlert("Zlecenie usunięte!");
    }




    @FXML
    void showPulpitScreen(ActionEvent event) throws IOException {
        SceneController.getPulpitScene(event);
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
    void showUstawieniaScreen(ActionEvent event) throws IOException {
        SceneController.getUstawieniaScene(event);
    }

    @FXML
    void showPomocScreen(ActionEvent event) throws IOException {
        SceneController.getPomocScene(event);
    }


    @FXML
    void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getZleceniaScene(event);
    }
}
