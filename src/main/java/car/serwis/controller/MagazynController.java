package car.serwis.controller;

import car.serwis.database.dao.*;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Kontroler widoku "magazyn.fxml"
 */
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

    @FXML
    private Tooltip tooltippedTableCell;

    @FXML
    private ComboBox<Kategoria> kategoriaFilterComboBox;

    WindowManagement windowManagement = new WindowManagement();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        windowManagement.initializeMinimalizeButton(minimalizeButton,magazynBorderPane);
        windowManagement.initializeExitButton(exitButton,magazynBorderPane);
        CurrentPracownik.setPracownikInfo(pracownikInfo);
        setObservableList();
        fillTables();
        addTableSettings();
        czescTableView.setPlaceholder(new Label("Brak danych!"));
        kategoriaTableView.setPlaceholder(new Label("Brak danych!"));
        jednostkiTableView.setPlaceholder(new Label("Brak danych!"));
        samochodyTableView.setPlaceholder(new Label("Brak danych!"));
        kategoriaFilterComboBox.setItems(getKategoriaObservableList());
    }

    /**
     * Metoda pobierajaca jednoski z bazy i wy??wietlaj??ca je w tableView
     */
    @FXML
    private void setJednostka(){
        jednostkaObservableList.clear();
        jednostkaObservableList.addAll(jednostkaDao.getJednostki());

        idJednostkaTableColumn.setCellValueFactory(new PropertyValueFactory<>("idJednostka"));
        nazwaJednostkaTableColumn.setCellValueFactory(new PropertyValueFactory<>("nazwaJednostki"));
        skrotJednostkaTableColumn.setCellValueFactory(new PropertyValueFactory<>("skrot"));

        skrotJednostkaTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nazwaJednostkaTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        jednostkiTableView.setEditable(true);
        jednostkiTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        jednostkiTableView.setItems(getSortedListJednostka());
    }

    /**
     * Metoda pobierajaca kategorie z bazy i wy??wietlaj??ca je w tableView
     */
    @FXML
    private void setKategoria(){
        kategoriaObservableList.clear();
        kategoriaObservableList.addAll(kategoriaDao.getKategorie());

        idKategoraTableColumn.setCellValueFactory(new PropertyValueFactory<>("idKategoria"));
        nazwaKategoraTableColumn.setCellValueFactory(new PropertyValueFactory<>("nazwaKategori"));
        nazwaKategoraTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());


        kategoriaTableView.setEditable(true);
        kategoriaTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        kategoriaTableView.setItems(getSortedListKategoria());
    }

    /**
     * Metoda pobierajaca samochody z bazy i wy??wietlaj??ca je w tableView
     */
    @FXML
    private void setSamochod(){
        samochodObservableList.clear();
        samochodObservableList.addAll(samochodDao.getSamochody());

        idSamochodTableColumn.setCellValueFactory(new PropertyValueFactory<>("idSamochod"));
        markaSamochodTableColumn.setCellValueFactory(new PropertyValueFactory<>("marka"));
        modelSamochodTableColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        markaSamochodTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        modelSamochodTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        samochodyTableView.setEditable(true);
        samochodyTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        samochodyTableView.setItems(getSortedListSamochod());
    }

    /**
     * Metoda czyszcz??ca i wype??niaj??ca ObservableList cz????ci
     */
    private void setObservableList() {
        czescObservableList.clear();
        czescObservableList.addAll(czescDao.displayRecords());
    }

    /**
     * Metoda wype??niaj??ca kolumny tabeli cz????ci
     */
    private void fillTables() {

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

    }

    /**
     * Metoda wype??niaj??ca tabele posortowanymi cz????ciami
     */
    private void addTableSettings() {
        czescTableView.setEditable(true);
        czescTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        czescTableView.setItems(getSortedListCzesc());
    }


    // ################### KATEGORIA ################################
    KategoriaDao kategoriaDao = new KategoriaDao();
    ObservableList<Kategoria> kategoriaObservableList = FXCollections.observableArrayList();

    /**
     * Metoda wywo??uj??ca widok "addKategoria.fxml"
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations
     */
    @FXML
    private void addKategoriaWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewKategoriaWindow();
        if (UpdateStatus.isKategoriaAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsKategoriaAdded(false);
        }
    }

    /**
     * Metoda przekazuj??ca przefiltrowane kategoie do TableView
     * @return zwraca liste posortowanych kategorii
     */
    private SortedList<Kategoria> getSortedListKategoria() {
        SortedList<Kategoria> sortedList = new SortedList<>(getFilteredListKategoria());
        sortedList.comparatorProperty().bind(kategoriaTableView.comparatorProperty());
        return sortedList;
    }

    /**
     * Metoda nas??uchuj??ca TextField i filtruja??ca kategorie
     * @return zwraca przefiltrowan?? liste kategorii
     */
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

    /**
     * Metoda usuwaj??ca kategorie
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations
     */
    @FXML
    void deleteKategoria(ActionEvent event) throws IOException {
        ObservableList<Kategoria> selectedRows = kategoriaTableView.getSelectionModel().getSelectedItems();
        for (Kategoria kategoria : selectedRows) {
            kategoriaDao.deleteKategoria(kategoria);
            kategoriaObservableList.remove(kategoria);
        }
        //refreshScreen(event);
        AlertPopUp.successAlert("Kategoria usuni??ta!");
    }

    /**
     * Metoda zmieniaj??ca nazw?? kategori
     */
    @FXML
    private void changeNazwaKategoira(TableColumn.CellEditEvent<Kategoria, String> editEvent) {
        Kategoria selectedKategoira = kategoriaTableView.getSelectionModel().getSelectedItem();
        selectedKategoira.setNazwaKategori(editEvent.getNewValue().toString());
        kategoriaDao.updateKategoira(selectedKategoira);
        AlertPopUp.successAlert("Zmieniono nazw?? kategori!");
    }


    // ################ JEDNOSTKA #####################################
    ObservableList<Jednostka> jednostkaObservableList = FXCollections.observableArrayList();
    JednostkaDao jednostkaDao = new JednostkaDao();

    /**
     * Metoda wywo??uj??ca widok "addJednostka.fxml"
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations
     */
    @FXML
    private void addJednostkaWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewJednostkaWindow();
        if (UpdateStatus.isJednostkaAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsJednostkaAdded(false);
        }
    }

    /**
     * Metoda przekazuj??ca przefiltrowane jednostki do TableView
     * @return zwraca liste posortowanych jednostek
     */
    private SortedList<Jednostka> getSortedListJednostka() {
        SortedList<Jednostka> sortedList = new SortedList<>(getFilteredListJednostka());
        sortedList.comparatorProperty().bind(jednostkiTableView.comparatorProperty());
        return sortedList;
    }

    /**
     * Metoda nas??uchuj??ca TextField i filtruja??ca jednostki
     * @return zwraca przefiltrowan?? liste jednostek
     */
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

    /**
     * Metoda usuwaj??ca jednostke
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations
     */
    @FXML
    void deleteJednostka(ActionEvent event) throws IOException {
        ObservableList<Jednostka> selectedRows = jednostkiTableView.getSelectionModel().getSelectedItems();
        for (Jednostka jednostka : selectedRows) {
            jednostkaDao.deleteJednostka(jednostka);
            jednostkaObservableList.remove(jednostka);
        }
        //refreshScreen(event);
        AlertPopUp.successAlert("Jednostka ununi??ta!");
    }

    /**
     * Metoda zmieniaj??ca skr??t jednostki
     */
    @FXML
    private void changeSkrotCell(TableColumn.CellEditEvent<Jednostka, String> editEvent) {
        Jednostka selectedJednostka = jednostkiTableView.getSelectionModel().getSelectedItem();
        selectedJednostka.setSkrot(editEvent.getNewValue());
        jednostkaDao.updateJednostka(selectedJednostka);
        AlertPopUp.successAlert("Zmieniono skr??t jednostki!");
    }

    /**
     * Metoda zmieniaj??ca nazwe jednostki
     */
    @FXML
    private void changeNazwaJednostkiCell(TableColumn.CellEditEvent<Jednostka, String> editEvent) {
        Jednostka selectedJednostka = jednostkiTableView.getSelectionModel().getSelectedItem();
        selectedJednostka.setNazwaJednostki(editEvent.getNewValue());
        jednostkaDao.updateJednostka(selectedJednostka);
        AlertPopUp.successAlert("Zmieniono nazw?? jednostki!");
    }


    // ################ SAMOCHOD ##########################################

    ObservableList<Samochod> samochodObservableList = FXCollections.observableArrayList();
    SamochodDao samochodDao = new SamochodDao();

    /**
     * Metoda wywo??uj??ca widok "addSamochod.fxml"
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations
     */
    @FXML
    private void addSamochodWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewSamochodWindow();
        if (UpdateStatus.isSamochodAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsSamochodAdded(false);
        }
    }

    /**
     * Metoda przekazuj??ca przefiltrowane samochody do TableView
     * @return zwraca liste posortowanych samochod??w
     */
    private SortedList<Samochod> getSortedListSamochod() {
        SortedList<Samochod> sortedList = new SortedList<>(getFilteredListSamochod());
        sortedList.comparatorProperty().bind(samochodyTableView.comparatorProperty());
        return sortedList;
    }

    /**
     * Metoda nas??uchuj??ca TextField i filtruja??ca samochody
     * @return zwraca przefiltrowan?? liste samochod??w
     */
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

    /**
     * Metoda usuwaj??ca samoch??d
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations
     */
    @FXML
    void deleteSamochod(ActionEvent event) throws IOException {
        ObservableList<Samochod> selectedRows = samochodyTableView.getSelectionModel().getSelectedItems();
        for (Samochod samochod : selectedRows) {
            samochodDao.deleteSamochod(samochod);
            samochodObservableList.remove(samochod);
        }
        //refreshScreen(event);
        AlertPopUp.successAlert("Samoch??d usuni??ty!");
    }


    /**
     * Metoda zmieniaj??ca marke samochodu
     */
    @FXML
    private void changeMarkaSamochod(TableColumn.CellEditEvent<Stanowisko, String> editEvent) {
        Samochod selectedSamochod = samochodyTableView.getSelectionModel().getSelectedItem();
        selectedSamochod.setMarka(editEvent.getNewValue().toString());
        samochodDao.updateSamochod(selectedSamochod);
        AlertPopUp.successAlert("Zmieniono mark?? samochodu!");
    }


    /**
     * Metoda zmieniaj??ca model samochodu
     */
    @FXML
    private void changeModelSamochod(TableColumn.CellEditEvent<Stanowisko, String> editEvent) {
        Samochod selectedSamochod = samochodyTableView.getSelectionModel().getSelectedItem();
        selectedSamochod.setModel(editEvent.getNewValue().toString());
        samochodDao.updateSamochod(selectedSamochod);
        AlertPopUp.successAlert("Zmieniono model samochodu!");
    }


    // ######################## CZ?????? #####################################
    ObservableList<Czesc> czescObservableList = FXCollections.observableArrayList();
    CzescDao czescDao = new CzescDao();


    /**
     * Metoda wywo??uj??ca widok "addCzesc.fxml"
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations
     */
    @FXML
    private void addNewCzescWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewCzescWindow();
        if (UpdateStatus.isCzescAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsCzescAdded(false);
        }
    }


    /**
     * Metoda usuwaj??ca cz??????
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations
     */
    @FXML
    void deleteCzesc(ActionEvent event) throws IOException {
        ObservableList<Czesc> selectedRows = czescTableView.getSelectionModel().getSelectedItems();

        if(czescTableView.getSelectionModel().getSelectedItems().isEmpty()){
            AlertPopUp.successAlert("Nie wybrano cz????ci do usuni??cia!");
        }else {
            for (Czesc czesc : selectedRows) {
                czescDao.deleteCzesc(czesc);
            }
            refreshScreen(event);
            AlertPopUp.successAlert("Cz?????? usuni??ta!");
        }
    }


    /**
     * Metoda przekazuj??ca przefiltrowane cz????ci do TableView
     * @return zwraca liste posortowanych cz????ci
     */
    private SortedList<Czesc> getSortedListCzesc() {
        SortedList<Czesc> sortedList = new SortedList<>(getFilteredListCzesc());
        sortedList.comparatorProperty().bind(czescTableView.comparatorProperty());
        return sortedList;
    }


    /**
     * Metoda nas??uchuj??ca TextField i filtruja??ca cz????ci
     * @return zwraca przefiltrowan?? liste cz????ci
     */
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
                    } else if (czesc.getProducent().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (czesc.getJednostka().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (czesc.getKategoria().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if(czesc.getSamochod().toString().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                    return false;
                }));
        return filteredList;
    }

    /**
     * Metoda zmieniaj??ca nazw?? cz????ci
     */
    @FXML
    private void changeNazwaCzesci(TableColumn.CellEditEvent<Kategoria, String> editEvent) {
        Czesc selectedCzesc = czescTableView.getSelectionModel().getSelectedItem();
        selectedCzesc.setNazwaCzesci(editEvent.getNewValue().toString());
        czescDao.updateCzesc(selectedCzesc);
        AlertPopUp.successAlert("Zmieniono nazw?? cz????ci!");
    }

    /**
     * Metoda zmieniaj??ca producenta cz????ci
     */
    @FXML
    private void changeProducentCzesci(TableColumn.CellEditEvent<Kategoria, String> editEvent) {
        Czesc selectedCzesc = czescTableView.getSelectionModel().getSelectedItem();
        selectedCzesc.setProducent(editEvent.getNewValue().toString());
        czescDao.updateCzesc(selectedCzesc);
        AlertPopUp.successAlert("Zmieniono producenta cz????ci!");
    }

    /**
     * Metoda zmieniaj??ca opis cz????ci
     */
    @FXML
    private void changeOpisCzesci(TableColumn.CellEditEvent<Kategoria, String> editEvent) {
        Czesc selectedCzesc = czescTableView.getSelectionModel().getSelectedItem();
        selectedCzesc.setOpisCzesc(editEvent.getNewValue().toString());
        czescDao.updateCzesc(selectedCzesc);
        AlertPopUp.successAlert("Zmieniono opis cz????ci!");
    }

    /**
     * Metoda wywo??uj??ca widok "updateCzesc.fxml"
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    @FXML
    private void updateCzescWindow(ActionEvent event) throws IOException {
        NewWindowController.getUpdateCzescWindow();
        if (UpdateStatus.isCzescUpdated()) {
            refreshScreen(event);
            UpdateStatus.setIsCzescUpdated(false);
        }
    }

    /**
     * Metoda wywo??uj??ca widok "addCzesc.fxml"
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    @FXML
    private void addCzescWindow(ActionEvent event) throws IOException {
        NewWindowController.getCzescWindow();
        if (UpdateStatus.isCzescUpdated()) {
            refreshScreen(event);
            UpdateStatus.setIsCzescUpdated(false);
        }
    }


    /**
     * Metoda filtruj??ca cz????ci na podstawie kategori
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    @FXML
    private void filterCzesc(ActionEvent event) throws IOException {
        Kategoria kategoria = kategoriaFilterComboBox.getValue();
        ArrayList<Czesc> list = new ArrayList<>(new CzescDao().getCzescForKategoria(kategoria));
        czescObservableList.clear();
        czescObservableList.addAll(list);
    }

    /**
     * Metoda pobieraj??ca z bazy obiekty kategoria i dodj??ca je do ObservableList
     * @return zwraca ObservableList kategori
     */
    private ObservableList<Kategoria> getKategoriaObservableList() {
        ObservableList<Kategoria> list = FXCollections.observableArrayList();
        list.addAll(new KategoriaDao().getKategorie());
        return list;
    }

    /**
     * Metoda czyszcz??ca filtr kategorii
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    @FXML
    private void clearFiltr(ActionEvent event) throws IOException {
        kategoriaFilterComboBox.getSelectionModel().clearSelection();
        czescObservableList.clear();
        czescObservableList.addAll(czescDao.getCzesci());
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
    public void showWarsztatScreen(ActionEvent event) throws IOException {
        SceneController.getWarsztatScene(event);
    }

    @FXML
    public void showKsiegowoscScreen(ActionEvent event) throws IOException {
        SceneController.getKsiegowoscScene(event);
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
        SceneController.getMagazynScene(event);
    }
}
