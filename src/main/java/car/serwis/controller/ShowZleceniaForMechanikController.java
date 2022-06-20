package car.serwis.controller;

import car.serwis.database.model.Zlecenie;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Kontroler widoku "showZlecenieForMechanik.fxml"
 */
public class ShowZleceniaForMechanikController implements Initializable {
    @FXML
    private Button anulujButton;

    @FXML
    private Text data;

    @FXML
    private Text idZlecenie;

    @FXML
    private Text kodMiejscowosc;

    @FXML
    private Text nazwaFirmy;

    @FXML
    private Text nip;

    @FXML
    private TextArea opis;

    @FXML
    private Text samochod;

    @FXML
    private Text statusZlecenie;

    @FXML
    private Text telefon;

    @FXML
    private Text ulica;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anulujButton.setOnAction(SceneController::close);
    }

    /**
     * Metoda ustwaiająca dane zlecenia dla mechanika
     * @param zlecenie obiek zlecenie przekazany z klasy WarsztatController
     */
    public void setData(Zlecenie zlecenie){
        data.setText(zlecenie.getDataPrzyjecia().toString());
        idZlecenie.setText(zlecenie.getIdZlecenie().toString());
        kodMiejscowosc.setText(zlecenie.getKontrahent().getKodPocztowy() + " " + zlecenie.getKontrahent().getMiejscowosc());
        nazwaFirmy.setText(zlecenie.getKontrahent().getNazwaFirmy());
        nip.setText(zlecenie.getKontrahent().getNip());
        opis.setText(zlecenie.getOpisZlecenie());
        samochod.setText(zlecenie.getSamochod().toString());
        statusZlecenie.setText(zlecenie.getStatus().toString());
        telefon.setText(zlecenie.getKontrahent().getTelefon());
        ulica.setText(zlecenie.getKontrahent().getUlica());
    }
}
