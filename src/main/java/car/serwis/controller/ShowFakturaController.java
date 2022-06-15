package car.serwis.controller;

import car.serwis.database.model.Faktura;
import car.serwis.database.model.PozycjaFaktury;
import car.serwis.database.model.Zlecenie;
import car.serwis.helpers.WindowManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ShowFakturaController implements Initializable {
    @FXML
    private TableColumn<PozycjaFaktury, Double> bruttoTableColumn;

    @FXML
    private TableColumn<PozycjaFaktury, Integer> iloscTableColumn;

    @FXML
    private TableColumn<PozycjaFaktury, Integer> lpTableColumn;

    @FXML
    private Text miastoText;

    @FXML
    private Text nazwaFirmyText;

    @FXML
    private TableColumn<PozycjaFaktury, Double> nettoTableColumn;

    @FXML
    private Text nipText;

    @FXML
    private Text numerFakturyText;

    @FXML
    private AnchorPane showFakturaAnchorePane;

    @FXML
    private TableView<PozycjaFaktury> showPozycjaTableView;

    @FXML
    private Text sumaBruttoText;

    @FXML
    private Text sumaNettoText;

    @FXML
    private Text ulicaText;

    @FXML
    private TableColumn<PozycjaFaktury, String> uslugaTableColumn;

    @FXML
    private Button anulujButton;

    WindowManagement windowManagement = new WindowManagement();
    ObservableList<PozycjaFaktury> pozycjaFakturyObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        windowManagement.initializeExitButtonAnchorPane(anulujButton,showFakturaAnchorePane);
        fillTable();
        addTableSettings();
    }


    private void fillTable() {
        uslugaTableColumn.setCellValueFactory(new PropertyValueFactory<>("opisPozycji"));
        iloscTableColumn.setCellValueFactory(new PropertyValueFactory<>("ilosc"));
        nettoTableColumn.setCellValueFactory(new PropertyValueFactory<>("cena"));


    }

    private void addTableSettings() {
        showPozycjaTableView.setEditable(true);
        showPozycjaTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        showPozycjaTableView.setItems(pozycjaFakturyObservableList);

    }


    public void setText(Faktura faktura){
        numerFakturyText.setText(faktura.getNumerFaktury());
        ulicaText.setText(faktura.getKontrahent().getUlica());
        nipText.setText("NIP: " + faktura.getKontrahent().getNip());
        nazwaFirmyText.setText(faktura.getKontrahent().getNazwaFirmy());
        miastoText.setText(faktura.getKontrahent().getKodPocztowy() + " " + faktura.getKontrahent().getMiejscowosc());



        Set<PozycjaFaktury> pfList = faktura.getPozycjeFaktury();
        Double sumaNetto = 0D;
        Double sumaBrutto = 0D;
        for (PozycjaFaktury pozycjaFaktury : pfList) {
            pozycjaFakturyObservableList.add(pozycjaFaktury);
            sumaNetto += pozycjaFaktury.getCena();
            sumaBrutto += pozycjaFaktury.getCena() + 0.23D * pozycjaFaktury.getCena();
        }

        sumaNettoText.setText(String.valueOf(sumaNetto));
        sumaBruttoText.setText(String.valueOf(sumaBrutto));

    }



}
