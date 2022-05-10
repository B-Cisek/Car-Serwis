package car.serwis.controller;

import car.serwis.database.dao.JednostkaDao;
import car.serwis.database.dao.KategoriaDao;
import car.serwis.database.dao.SamochodDao;
import car.serwis.database.model.*;
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
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MagazynController implements Initializable {

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Kategoria, Long> idKategoriaTableColumn;

    @FXML
    private TableView<Kategoria> kategorieTableView;

    @FXML
    private TableColumn<Kategoria, String> nazwaKategoriTableColumn;

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



    // ################ KATEGORIA ################

    KategoriaDao kategoriaDao = new KategoriaDao();
    ObservableList<Kategoria> kategoriaObservableList = FXCollections.observableArrayList();

    @FXML
    private void addKategoriaWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewKategoriaWindow();
        if(UpdateStatus.isKategoriaAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsKategoriaAdded(false);
        }
    }

    private void setObservableList() {
        kategoriaObservableList.clear();
        kategoriaObservableList.addAll(kategoriaDao.getKategorie());

        jednostkaObservableList.clear();
        jednostkaObservableList.addAll(jednostkaDao.getJednostki());
    }

    private void fillTables() {
        idKategoriaTableColumn.setCellValueFactory(new PropertyValueFactory<>("idKategoria"));
        nazwaKategoriTableColumn.setCellValueFactory(new PropertyValueFactory<>("nazwaKategori"));

        nazwaKategoriTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        idJednostkaTableColumn.setCellValueFactory(new PropertyValueFactory<>("idJednostka"));
        nazwaJednostkaTableColumn.setCellValueFactory(new PropertyValueFactory<>("nazwaJednostki"));
        skrotJednostkaTableColumn.setCellValueFactory(new PropertyValueFactory<>("skrot"));

        nazwaKategoriTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        skrotJednostkaTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void addTableSettings() {
        kategorieTableView.setEditable(true);
        kategorieTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        kategorieTableView.setItems(getSortedListKategoria());

        jednostkiTableView.setEditable(true);
        jednostkiTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        jednostkiTableView.setItems(getSortedListJednostka());
    }

    private SortedList<Kategoria> getSortedListKategoria() {
        SortedList<Kategoria> sortedList = new SortedList<>(getFilteredListKategoria());
        sortedList.comparatorProperty().bind(kategorieTableView.comparatorProperty());
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
        ObservableList<Kategoria> selectedRows = kategorieTableView.getSelectionModel().getSelectedItems();
        for (Kategoria kategoria : selectedRows) {
            kategoriaDao.deleteKategoria(kategoria);
        }
        refreshScreen(event);
    }

    // ################ JEDNOSTKA ################


    ObservableList<Jednostka> jednostkaObservableList = FXCollections.observableArrayList();
    JednostkaDao jednostkaDao = new JednostkaDao();


    @FXML
    private void addJednostkaWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewJednostkaWindow();
        if(UpdateStatus.isJednostkaAdded()) {
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
                    }  else {
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
    }







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setObservableList();
        fillTables();
        addTableSettings();
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
