package car.serwis.controller;

import car.serwis.database.dao.CzescDao;
import car.serwis.database.dao.JednostkaDao;
import car.serwis.database.dao.KategoriaDao;
import car.serwis.database.dao.SamochodDao;
import car.serwis.database.model.*;
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
import java.util.ResourceBundle;

public class MagazynController implements Initializable {

    @FXML
    private Button exitButton;

    @FXML
    private Button minimalizeButton;

    @FXML
    private TableColumn<Kategoria, Long> idKategoraTableColumn;

    @FXML
    private TableView<Kategoria> kategoriaTableView;

    @FXML
    private TableColumn<Kategoria, String> nazwaKategoraTableColumn;

    @FXML
    private Text pracownikInfo;

    @FXML
    private TextField searchKategoriaBar;

    @FXML
    private TableColumn<Jednostka, Long> idJednostkaTableColumn;

    @FXML
    private TableView<Jednostka> jednostkiTableView;

    @FXML
    private TableColumn<Jednostka, String> nazwaJednostkaTableColumn;

    @FXML
    private TextField searchJednostkaBar;

    @FXML
    private TableColumn<Jednostka, String> skrotJednostkaTableColumn;

    @FXML
    private TableView<Samochod> samochodyTableView;

    @FXML
    private TextField searchSamochodBar;

    @FXML
    private TableColumn<Samochod, String> markaSamochodTableColumn;

    @FXML
    private TableColumn<Samochod, String> modelSamochodTableColumn;

    @FXML
    private TableColumn<Samochod, Long> idSamochodTableColumn;

    @FXML
    private TableColumn<Czesc, String> producentCzescTableColumn;

    @FXML
    private TableColumn<Czesc, String> samochodCzescTableColumn;

    @FXML
    private TableColumn<Czesc, String> opisCzescTableColumn;

    @FXML
    private TableColumn<Czesc, String> nazwaCzesciTableColumn;

    @FXML
    private TableColumn<Czesc, String> kategoriaCzescTableColumn;

    @FXML
    private TableColumn<Czesc, Double> iloscCzescTableColumn;

    @FXML
    private TableColumn<Czesc, String> jednostkaCzescTableColumn;


    @FXML
    private TableColumn<Czesc, Long> idCzescTableColumn;

    @FXML
    private TableView<Czesc> czescTableView;

    @FXML
    private TextField searchCzescBar;

    @FXML
    private BorderPane magazynBorderPane;

    WindowManagement windowManagement = new WindowManagement();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        windowManagement.initializeMinimalizeButton(minimalizeButton,magazynBorderPane);
        windowManagement.initializeExitButton(exitButton,magazynBorderPane);
        CurrentPracownik.setPracownikInfo(pracownikInfo);
        setObservableList();
        fillTables();
        addTableSettings();
    }

    private void setObservableList() {
        kategoriaObservableList.clear();
        kategoriaObservableList.addAll(kategoriaDao.getKategorie());

        jednostkaObservableList.clear();
        jednostkaObservableList.addAll(jednostkaDao.getJednostki());

        samochodObservableList.clear();
        samochodObservableList.addAll(samochodDao.getSamochody());

        czescObservableList.clear();
        czescObservableList.addAll(czescDao.displayRecords());
    }

    private void fillTables() {
        // Jednostka Table View
        idJednostkaTableColumn.setCellValueFactory(new PropertyValueFactory<>("idJednostka"));
        nazwaJednostkaTableColumn.setCellValueFactory(new PropertyValueFactory<>("nazwaJednostki"));
        skrotJednostkaTableColumn.setCellValueFactory(new PropertyValueFactory<>("skrot"));
        skrotJednostkaTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());


        // Samochod Table View
        idSamochodTableColumn.setCellValueFactory(new PropertyValueFactory<>("idSamochod"));
        markaSamochodTableColumn.setCellValueFactory(new PropertyValueFactory<>("marka"));
        modelSamochodTableColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        markaSamochodTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        modelSamochodTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Czesci Table View
        idCzescTableColumn.setCellValueFactory(new PropertyValueFactory<>("idCzesc"));
        nazwaCzesciTableColumn.setCellValueFactory(new PropertyValueFactory<>("nazwaCzesci"));
        opisCzescTableColumn.setCellValueFactory(new PropertyValueFactory<>("opisCzesc"));
        iloscCzescTableColumn.setCellValueFactory(new PropertyValueFactory<>("ilosc"));
        producentCzescTableColumn.setCellValueFactory(new PropertyValueFactory<>("producent"));
        kategoriaCzescTableColumn.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        jednostkaCzescTableColumn.setCellValueFactory(new PropertyValueFactory<>("jednostka"));
        samochodCzescTableColumn.setCellValueFactory(new PropertyValueFactory<>("samochod"));
        nazwaCzesciTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        opisCzescTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        producentCzescTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Kategoria Table View
        idKategoraTableColumn.setCellValueFactory(new PropertyValueFactory<>("idKategoria"));
        nazwaKategoraTableColumn.setCellValueFactory(new PropertyValueFactory<>("nazwaKategori"));
        nazwaKategoraTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());


    }

    private void addTableSettings() {
        kategoriaTableView.setEditable(true);
        kategoriaTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        kategoriaTableView.setItems(getSortedListKategoria());

        jednostkiTableView.setEditable(true);
        jednostkiTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        jednostkiTableView.setItems(getSortedListJednostka());

        samochodyTableView.setEditable(true);
        samochodyTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        samochodyTableView.setItems(getSortedListSamochod());

        czescTableView.setEditable(true);
        czescTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        czescTableView.setItems(getFilteredListCzesc());
    }


    // ################ KATEGORIA ##################################################################
    KategoriaDao kategoriaDao = new KategoriaDao();
    ObservableList<Kategoria> kategoriaObservableList = FXCollections.observableArrayList();

    @FXML
    private void addKategoriaWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewKategoriaWindow();
        if (UpdateStatus.isKategoriaAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsKategoriaAdded(false);
        }
    }

    private SortedList<Kategoria> getSortedListKategoria() {
        SortedList<Kategoria> sortedList = new SortedList<>(getFilteredListKategoria());
        sortedList.comparatorProperty().bind(kategoriaTableView.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Kategoria> getFilteredListKategoria() {
        FilteredList<Kategoria> filteredList = new FilteredList<>(kategoriaObservableList, b -> true);
        searchKategoriaBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(kategoria -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (kategoria.getNazwaKategori().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return kategoria.getIdKategoria().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
    }

    @FXML
    void deleteKategoria(ActionEvent event) throws IOException {
        ObservableList<Kategoria> selectedRows = kategoriaTableView.getSelectionModel().getSelectedItems();
        for (Kategoria kategoria : selectedRows) {
            kategoriaDao.deleteKategoria(kategoria);
            kategoriaObservableList.remove(kategoria);
        }
        //refreshScreen(event);
        AlertPopUp.successAlert("Kategoria usunięta");
    }

    @FXML
    private void changeNazwaKategoira(TableColumn.CellEditEvent<Kategoria, String> editEvent) {
        Kategoria selectedKategoira = kategoriaTableView.getSelectionModel().getSelectedItem();
        selectedKategoira.setNazwaKategori(editEvent.getNewValue().toString());
        kategoriaDao.updateKategoira(selectedKategoira);
        AlertPopUp.successAlert("Zmieniono nazwę kategori!");
    }

    //########################################################################################################






    // ################ JEDNOSTKA ################

    ObservableList<Jednostka> jednostkaObservableList = FXCollections.observableArrayList();

    JednostkaDao jednostkaDao = new JednostkaDao();


    @FXML
    private void addJednostkaWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewJednostkaWindow();
        if (UpdateStatus.isJednostkaAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsJednostkaAdded(false);
        }
    }

    private SortedList<Jednostka> getSortedListJednostka() {
        SortedList<Jednostka> sortedList = new SortedList<>(getFilteredListJednostka());
        sortedList.comparatorProperty().bind(jednostkiTableView.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Jednostka> getFilteredListJednostka() {
        FilteredList<Jednostka> filteredList = new FilteredList<>(jednostkaObservableList, b -> true);
        searchJednostkaBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(jednostka -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (jednostka.getNazwaJednostki().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (jednostka.getSkrot().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return jednostka.getIdJednostka().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
    }

    @FXML
    void deleteJednostka(ActionEvent event) throws IOException {
        ObservableList<Jednostka> selectedRows = jednostkiTableView.getSelectionModel().getSelectedItems();
        for (Jednostka jednostka : selectedRows) {
            jednostkaDao.deleteJednostka(jednostka);
        }
        refreshScreen(event);
        AlertPopUp.successAlert("Usunięto jednostkę!");
    }

    @FXML
    private void changeSkrotCell(TableColumn.CellEditEvent<Jednostka, String> editEvent) {
        Jednostka selectedJednostka = jednostkiTableView.getSelectionModel().getSelectedItem();
        selectedJednostka.setSkrot(editEvent.getNewValue());
        jednostkaDao.updateJednostka(selectedJednostka);
    }


    // ################ SAMOCHOD ################
    ObservableList<Samochod> samochodObservableList = FXCollections.observableArrayList();

    SamochodDao samochodDao = new SamochodDao();


    @FXML
    private void addSamochodWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewSamochodWindow();
        if (UpdateStatus.isSamochodAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsSamochodAdded(false);
        }
    }

    private SortedList<Samochod> getSortedListSamochod() {
        SortedList<Samochod> sortedList = new SortedList<>(getFilteredListSamochod());
        sortedList.comparatorProperty().bind(samochodyTableView.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Samochod> getFilteredListSamochod() {
        FilteredList<Samochod> filteredList = new FilteredList<>(samochodObservableList, b -> true);
        searchSamochodBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(samochod -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (samochod.getMarka().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (samochod.getModel().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return samochod.getIdSamochod().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
    }

    @FXML
    void deleteSamochod(ActionEvent event) throws IOException {
        ObservableList<Samochod> selectedRows = samochodyTableView.getSelectionModel().getSelectedItems();
        for (Samochod samochod : selectedRows) {
            samochodDao.deleteSamochod(samochod);
            System.out.println(samochod.getIdSamochod());
        }
        refreshScreen(event);
    }

    @FXML
    private void changeMarkaSamochod(TableColumn.CellEditEvent<Stanowisko, String> editEvent) {
        Samochod selectedSamochod = samochodyTableView.getSelectionModel().getSelectedItem();
        selectedSamochod.setMarka(editEvent.getNewValue().toString());
        samochodDao.updateSamochod(selectedSamochod);
    }

    @FXML
    private void changeModelSamochod(TableColumn.CellEditEvent<Stanowisko, String> editEvent) {
        Samochod selectedSamochod = samochodyTableView.getSelectionModel().getSelectedItem();
        selectedSamochod.setModel(editEvent.getNewValue().toString());
        samochodDao.updateSamochod(selectedSamochod);
    }


    // ############ CZĘŚC #####################################
    ObservableList<Czesc> czescObservableList = FXCollections.observableArrayList();

    CzescDao czescDao = new CzescDao();

    @FXML
    private void addCzescWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewCzescWindow();
        if (UpdateStatus.isCzescAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsCzescAdded(false);
        }
    }

    @FXML
    void deleteCzesc(ActionEvent event) throws IOException {
        ObservableList<Czesc> selectedRows = czescTableView.getSelectionModel().getSelectedItems();
        for (Czesc czesc : selectedRows) {
            czescDao.deleteCzesc(czesc);
        }
        refreshScreen(event);
    }

    private SortedList<Czesc> getSortedListCzesc() {
        SortedList<Czesc> sortedList = new SortedList<>(getFilteredListCzesc());
        sortedList.comparatorProperty().bind(czescTableView.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Czesc> getFilteredListCzesc() {
        FilteredList<Czesc> filteredList = new FilteredList<>(czescObservableList, b -> true);
        searchCzescBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(czesc -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (czesc.getNazwaCzesci().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (czesc.getOpisCzesc().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (czesc.getIdCzesc().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else if (czesc.getIlosc().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else if (czesc.getProducent().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else if (czesc.getJednostka().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else if (czesc.getKategoria().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return czesc.getSamochod().toString().contains(lowerCaseFilter);
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
    void showKsiegowoscScreen(ActionEvent event) throws IOException {
        SceneController.getKsiegowoscScene(event);
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
        SceneController.getMagazynScene(event);
    }
}
