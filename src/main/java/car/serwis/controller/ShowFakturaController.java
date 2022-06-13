package car.serwis.controller;

import car.serwis.database.model.Faktura;
import car.serwis.database.model.PozycjaFaktury;
import car.serwis.helpers.WindowManagement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class ShowFakturaController implements Initializable {
    @FXML
    private TableColumn<PozycjaFaktury, Double> bruttoTableColumn;

    @FXML
    private TableColumn<PozycjaFaktury, Double> iloscTableColumn;

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
    private TableView<PozycjaFaktury> showFakturaTableView;

    @FXML
    private Text sumaBruttoText;

    @FXML
    private Text sumaNettoText;

    @FXML
    private Text ulicaText;

    @FXML
    private TableColumn<PozycjaFaktury, String> uslugaTableColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setDataFaktura(Faktura faktura){
        System.out.println(faktura.getClass());
        Set<PozycjaFaktury> list = faktura.getPozycjeFaktury();
        for (PozycjaFaktury pozycjaFaktury : list) {
            miastoText.setText(pozycjaFaktury.getOpisPozycji());
        }

    }



}
