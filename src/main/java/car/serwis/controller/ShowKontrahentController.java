package car.serwis.controller;

import car.serwis.database.model.Kontrahent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *  Kontroler widoku "showKontrahent.fxml"
 */
public class ShowKontrahentController implements Initializable {
    @FXML
    private Button anulujButton;

    @FXML
    private Text idKontrahent;

    @FXML
    private Text imieKontrahent;

    @FXML
    private Text kodKontrhanet;

    @FXML
    private Text miejscowoscKontrhanet;

    @FXML
    private Text nazwaFirmyKontrahent;

    @FXML
    private Text nazwiskoKontrahent;

    @FXML
    private Text nipKontrhanet;

    @FXML
    private Text peselKontrhanet;

    @FXML
    private Text telefonKontrhanet;

    @FXML
    private Text ulicaKontrhanet;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anulujButton.setOnAction(SceneController::close);
    }

    /**
     * Metoda ustwaiająca dane kontrahenta
     * @param kontrahent obiek kontrahent przekazany z klasy ZlecenieController
     */
    public void setData(Kontrahent kontrahent){
        idKontrahent.setText(kontrahent.getIdKontrahent().toString());
        imieKontrahent.setText(kontrahent.getImie());
        nazwiskoKontrahent.setText(kontrahent.getNazwisko());
        nazwaFirmyKontrahent.setText(kontrahent.getNazwaFirmy());
        telefonKontrhanet.setText(kontrahent.getTelefon());
        nipKontrhanet.setText(kontrahent.getNip());
        peselKontrhanet.setText(kontrahent.getPesel());
        miejscowoscKontrhanet.setText(kontrahent.getMiejscowosc());
        ulicaKontrhanet.setText(kontrahent.getUlica());
        kodKontrhanet.setText(kontrahent.getKodPocztowy());
    }

}
