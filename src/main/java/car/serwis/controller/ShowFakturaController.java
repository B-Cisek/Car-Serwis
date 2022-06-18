package car.serwis.controller;

import car.serwis.database.model.Faktura;
import car.serwis.database.model.PozycjaFaktury;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Kontroler dla widoku "showFakura.fxml"
 */
public class ShowFakturaController implements Initializable {
    @FXML
    private TableColumn<PozycjaFaktury, Integer> iloscTableColumn;

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
    private TableView<PozycjaFaktury> pozycjaFakturyTableView;

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

    /** Inicjalizacja ObservableList dla pozycji faktury */
    ObservableList<PozycjaFaktury> pozycjaFakturyObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anulujButton.setOnAction(SceneController::close);
        fillTable();
        addTableSettings();
    }

    /**
     * Metoda wypełniająca danymi kolumny TableView pozycjaFakturyTableView
     */
    private void fillTable() {
        uslugaTableColumn.setCellValueFactory(new PropertyValueFactory<>("opisPozycji"));
        iloscTableColumn.setCellValueFactory(new PropertyValueFactory<>("ilosc"));
        nettoTableColumn.setCellValueFactory(new PropertyValueFactory<>("cena"));
    }

    /**
     * Metoda ustawień TableView
     */
    private void addTableSettings() {
        pozycjaFakturyTableView.setEditable(false);
        pozycjaFakturyTableView.setItems(pozycjaFakturyObservableList);
    }

    /**
     * Metoda wyświetlająca dane faktury
     * @param faktura Obiekt faktura przekazany z KsiegowoscController
     */
    public void setData(Faktura faktura){
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
