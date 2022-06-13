package car.serwis.controller;

import car.serwis.database.dao.FakturaDao;
import car.serwis.database.dao.PozycjaFakturyDao;
import car.serwis.database.model.Faktura;
import car.serwis.database.model.PozycjaFaktury;
import pdf.generator.GeneratorFaktury;
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
import java.util.ArrayList;
import java.util.List;
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

    @FXML
    private Button generujPdfButton;

    @FXML
    private Button showFakturaButton;

    GeneratorFaktury generatorFaktury = new GeneratorFaktury();
    ObservableList<Faktura> fakturaObservableList = FXCollections.observableArrayList();
    WindowManagement windowManagement = new WindowManagement();
    FakturaDao fakturaDao = new FakturaDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        windowManagement.initializeExitButton(exitButton, ksiegowoscBorderPane);
        windowManagement.initializeMinimalizeButton(minimalizeButton, ksiegowoscBorderPane);
        CurrentPracownik.setPracownikInfo(pracownikInfo);
        setObservableList();
        fillTables();
        addTableSettings();
        pdfGenerate();
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
                    } else if (faktura.getMiejsceWystawienia().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (faktura.getKontrahent().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return faktura.getIdFaktura().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
    }

    // ########### PDF Generator #####################

    private void pdfGenerate() {
        generujPdfButton.setOnAction((x) -> {
            if (fakturaTableView.getSelectionModel().getSelectedItem() == null){
                AlertPopUp.successAlert("Nie wybrano faktury!");
            }else {
                ArrayList<pdf.generator.PozycjaFaktury> pfList = new ArrayList<>();
                String imageUrl = "C:\\Users\\Bartek\\Desktop\\warsztat\\warsztat-samochodowy\\src\\main\\resources\\css\\img\\wrench.png";
                Long selectedRow = fakturaTableView.getSelectionModel().getSelectedItem().getIdFaktura();
                Faktura faktura = fakturaDao.getFakturaID(selectedRow);
                List<PozycjaFaktury> list = new PozycjaFakturyDao().getPozycjaFakturyForPdf(faktura);

                for (PozycjaFaktury pozycjaFaktury : list) {
                    pdf.generator.PozycjaFaktury pozycjaFakturyLibrary = new pdf.generator.PozycjaFaktury();
                    pozycjaFakturyLibrary.setOpisPozycji(pozycjaFaktury.getOpisPozycji());
                    pozycjaFakturyLibrary.setIlosc(pozycjaFaktury.getIlosc());
                    pozycjaFakturyLibrary.setCena(pozycjaFaktury.getCena());
                    pfList.add(pozycjaFakturyLibrary);
                }

                generatorFaktury.generujFakture(
                        faktura.getMiejsceWystawienia(),
                        LocalDate.now(),
                        faktura.getKontrahent().getNazwaFirmy(),
                        faktura.getKontrahent().getNip(),
                        faktura.getKontrahent().getUlica(),
                        faktura.getKontrahent().getKodPocztowy() + " " + faktura.getKontrahent().getMiejscowosc(),
                        faktura.getNumerFaktury() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getYear(),
                        pfList,
                        imageUrl

                );
                AlertPopUp.successAlertFaktura("Faktura zapisana w domyślnym folderze: Dokumenty");
            }


        });
    }

    @FXML
    void deleteFaktura(ActionEvent event) throws IOException {
        ObservableList<Faktura> selectedRows = fakturaTableView.getSelectionModel().getSelectedItems();
        if (fakturaTableView.getSelectionModel().getSelectedItems().isEmpty()){
            AlertPopUp.successAlert("Nie wybrano faktury do usunięcia!");
        }else {
            for (Faktura faktura : selectedRows) {
                fakturaDao.deleteFaktura(faktura);
            }
            refreshScreen(event);
            AlertPopUp.successAlert("Faktura usunięta!");
        }
    }



    @FXML
    public void showFaktura(ActionEvent event) throws IOException {
        if (fakturaTableView.getSelectionModel().getSelectedItem() == null){
            AlertPopUp.successAlert("Nie wybrano faktury!");
        }else {
            try {
                ShowFakturaController showFakturaController = new ShowFakturaController();
                Faktura faktura = fakturaTableView.getSelectionModel().getSelectedItem();
                showFakturaController.setDataFaktura(faktura);
                NewWindowController.getShowFakturaWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        SceneController.getKsiegowoscScene(event);
    }
}

