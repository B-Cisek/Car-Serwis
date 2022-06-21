package car.serwis.controller;

import car.serwis.database.dao.FakturaDao;
import car.serwis.database.dao.PozycjaFakturyDao;
import car.serwis.database.model.Faktura;
import car.serwis.database.model.PozycjaFaktury;
import car.serwis.helpers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pdf.generator.GeneratorFaktury;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Kontroler widoku "ksiegowosc.fxml"
 */
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

    /**
     * Metoda czyszcząca i wypełniająca ObservableList
     */
    private void setObservableList() {
        /** ObservableList - faktury */
        fakturaObservableList.clear();
        fakturaObservableList.addAll(fakturaDao.displayRecords());
    }

    /**
     * Metoda wypełniająca kolumny tabeli
     */
    private void fillTables() {
        /** Kolumny Tabeli - faktura */
        idFakturaTableColumn.setCellValueFactory(new PropertyValueFactory<>("idFaktura"));
        numerFakturaTableColumn.setCellValueFactory(new PropertyValueFactory<>("numerFaktury"));
        miejsceFakturaTableColumn.setCellValueFactory(new PropertyValueFactory<>("miejsceWystawienia"));
        dataFakturaTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataWystawienia"));
        kontrahentFakturaTableColumn.setCellValueFactory(new PropertyValueFactory<>("kontrahent"));
    }

    /**
     * Metoda wypełniająca tabele posortowanymi danymi
     */
    private void addTableSettings() {
        fakturaTableView.setEditable(true);
        fakturaTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        fakturaTableView.setItems(getSortedListFaktura());
    }

    /**
     * Metoda wywołująca widok "addFaktura.fxml"
     * @param event
     * @throws IOException
     */
    @FXML
    private void addFakturaWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewFakturaWindow();
        if (UpdateStatus.isFakturaAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsFakturaAdded(false);
        }
    }

    /**
     * Metoda przekazująca przefiltrowanych faktury do TableView
     * @return zwraca liste posortowanych faktur
     */
    private SortedList<Faktura> getSortedListFaktura() {
        SortedList<Faktura> sortedList = new SortedList<>(getFilteredListFaktura());
        sortedList.comparatorProperty().bind(fakturaTableView.comparatorProperty());
        return sortedList;
    }

    /**
     * Metoda nasłuchująca TextField i filtrująca faktur
     * @return zwraca przefiltrowaną liste faktur
     */
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
                    } else if (faktura.getKontrahent().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return faktura.getIdFaktura().toString().contains(lowerCaseFilter);
                    }
                }));
        return filteredList;
    }


    /**
     * Metoda generujaca PDF faktury
     * metoda korzysta z biblioteki pdf_generator
     */
    private void pdfGenerate() {
        generujPdfButton.setOnAction((x) -> {
            if (fakturaTableView.getSelectionModel().getSelectedItem() == null){
                AlertPopUp.successAlert("Nie wybrano faktury!");
            }else {
                ArrayList<pdf.generator.PozycjaFaktury> pfList = new ArrayList<>();
                // TODO path
                File file = new File("src/main/resources/css/img/wrench.png");
                File test = new File(Objects.requireNonNull(getClass().getResource("/css/img/wrench.png")).toString());;

                System.out.println(test);
                System.out.println(test.getPath());
                System.out.println(test.getAbsolutePath());

                String imageUrl = test.getPath();
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

    /**
     * Metoda usuwająca fakture
     * @param event
     * @throws IOException
     */
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



    /**
     * Metoda wywołująca widok "showFaktura.fxml"
     */
    @FXML
    public void showFaktura(){
        if (fakturaTableView.getSelectionModel().getSelectedItem() == null){
            AlertPopUp.successAlert("Nie wybrano faktury!");
        }else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(ScenePath.SHOW_FAKTURA.getPath()));
                Parent parent = loader.load();

                ShowFakturaController showFakturaController = loader.getController();
                showFakturaController.setData(fakturaTableView.getSelectionModel().getSelectedItem());
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
    public void showWarsztatScreen(ActionEvent event) throws IOException {
        SceneController.getWarsztatScene(event);
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
        SceneController.getKsiegowoscScene(event);
    }
}

