package car.serwis.controller;

import car.serwis.database.dao.ZlecenieDao;
import car.serwis.database.model.Zlecenie;
import car.serwis.helpers.*;
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

import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 *  Kontroler widoku "warsztat.fxml"
 */
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
    private TableColumn<Zlecenie, ZlecenieStatus> statusMojeZleceniaTableColumn;

    @FXML
    private BorderPane warsztatBorderPane;

    @FXML
    private TableColumn<Zlecenie, LocalDate> dataPrzyjeciaWszystkieZleceniaTableColumn;

    @FXML
    private TableColumn<Zlecenie, Long> idWszystkieZleceniaTableColumn;

    @FXML
    private TableColumn<Zlecenie, String> opisWszystkieZleceniaTableColumn;

    @FXML
    private TableColumn<Zlecenie, String> samochodWszystkieZleceniaTableColumn;

    @FXML
    private TextField searchWszystkieZleceniaTableColumn;

    @FXML
    private TableColumn<Zlecenie, ZlecenieStatus> statusWszystkieZleceniaTableColumn;

    @FXML
    private TableView<Zlecenie> wszystkieZleceniaTableView;

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

    /**
     * Metoda czyszcząca i wypełniająca ObservableList
     */
    private void setObservableList() {
        /** ObservableList - moje zlecenia */
        mojeZleceniaObservableList.clear();
        mojeZleceniaObservableList.addAll(zlecenieDao.getMechanikZlecenia());

        /** ObservableList - wszystkie zlecenia */
        wszystkieZleceniaObservableList.clear();
        wszystkieZleceniaObservableList.addAll(zlecenieDao.getDostepneZlecenia());
    }


    /**
     * Metoda wypełniająca kolumny tabeli
     */
    private void fillTables() {
        /** Kolumny Tabeli - moje zlecenia */
        idMojeZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("idZlecenie"));
        dataPrzyjeciaMojeZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataPrzyjecia"));
        opisMojeZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("opisZlecenie"));
        statusMojeZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        samochodMojeZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("samochod"));

        /** Kolumny Tabeli - wszystkie zlecenia */
        idWszystkieZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("idZlecenie"));
        dataPrzyjeciaWszystkieZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataPrzyjecia"));
        opisWszystkieZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("opisZlecenie"));
        statusWszystkieZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        samochodWszystkieZleceniaTableColumn.setCellValueFactory(new PropertyValueFactory<>("samochod"));
    }

    /**
     * Metoda wypełniająca tabele posortowanymi danymi
     */
    private void addTableSettings() {
        /** TableView - moje zlecenia */
        mojeZleceniaTableView.setEditable(true);
        mojeZleceniaTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        mojeZleceniaTableView.setItems(getSortedListMojeZlecenia());

        /** TableView - wszystkie zlecenia */
        wszystkieZleceniaTableView.setEditable(true);
        wszystkieZleceniaTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        wszystkieZleceniaTableView.setItems(getSortedListWszystkieZlecenia());
    }

    // #################### MOJE ZLECENIA ####################

    /**
     * Metoda przekazująca przefiltrowane moje zlecenia do TableView
     * @return zwraca liste posortowanych zleceń
     */
    private SortedList<Zlecenie> getSortedListMojeZlecenia() {
        SortedList<Zlecenie> sortedList = new SortedList<>(getFilteredListMojeZlecenia());
        sortedList.comparatorProperty().bind(mojeZleceniaTableView.comparatorProperty());
        return sortedList;
    }

    /**
     * Metoda nasłuchująca TextField i filtrująca moje zlecenia
     * @return zwraca przefiltrowaną liste zleceń
     */
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
                    } else if (zlecenie.getSamochod().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return zlecenie.getIdZlecenie().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
    }

    /**
     * Metoda wywołująca widok "updateStatus.fxml"
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    @FXML
    private void changeStatusWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewStatusWindow();
        if (UpdateStatus.isStatusUpdated()) {
            refreshScreen(event);
            UpdateStatus.setIsStatusUpdated(false);
        }
    }

    // ################# WSZYSTKIE ZLECENIA ##################

    /**
     * Metoda przekazująca przefiltrowane wszystkie zlecenia do TableView
     * @return zwraca liste posortowanych zleceń
     */
    private SortedList<Zlecenie> getSortedListWszystkieZlecenia() {
        SortedList<Zlecenie> sortedList = new SortedList<>(getFilteredListWszyskieZlecenia());
        sortedList.comparatorProperty().bind(wszystkieZleceniaTableView.comparatorProperty());
        return sortedList;
    }

    /**
     * Metoda nasłuchująca TextField i filtrująca wszystkie zlecenia
     * @return zwraca przefiltrowaną liste zleceń
     */
    private FilteredList<Zlecenie> getFilteredListWszyskieZlecenia() {
        FilteredList<Zlecenie> filteredList = new FilteredList<>(wszystkieZleceniaObservableList, b -> true);
        searchWszystkieZleceniaTableColumn.textProperty().addListener((observable, oldValue, newValue) ->
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
                    } else if (zlecenie.getSamochod().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return zlecenie.getIdZlecenie().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
    }

    /**
     * Metoda przypisująca zlecenie do mechanika
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
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

    /**
     * Metoda wywołująca widok "showZlecenieForMechanik.fxml"
     */
    @FXML
    public void showMojeZlecenie(){
        if (mojeZleceniaTableView.getSelectionModel().getSelectedItem() == null){
            AlertPopUp.successAlert("Nie wybrano zlecenia!");
        }else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(ScenePath.SHOW_ZLECENIE_FOR_MECHANIK.getPath()));
                Parent parent = loader.load();

                ShowZleceniaForMechanikController showZleceniaForMechanikController = loader.getController();
                showZleceniaForMechanikController.setData(mojeZleceniaTableView.getSelectionModel().getSelectedItem());
                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.setScene(new Scene(parent));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void showPulpitScreen(ActionEvent event) throws IOException {
        SceneController.getPulpitScene(event);
    }

    @FXML
    public void showZleceniaScreen(ActionEvent event) throws IOException {
        SceneController.getZleceniaScene(event);
    }


    @FXML
    public void showKsiegowoscScreen(ActionEvent event) throws IOException {
        SceneController.getKsiegowoscScene(event);
    }

    @FXML
    public void showMagazynScreen(ActionEvent event) throws IOException {
        SceneController.getMagazynScene(event);
    }

    @FXML
    public void showUstawieniaScreen(ActionEvent event) throws IOException {
        SceneController.getUstawieniaScene(event);
    }

    @FXML
    public void showPomocScreen(ActionEvent event) throws IOException {
        SceneController.getPomocScene(event);
    }

    @FXML
    public void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getWarsztatScene(event);
    }
}
