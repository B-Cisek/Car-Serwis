package car.serwis.controller;

import car.serwis.database.dao.CzescDao;
import car.serwis.database.model.Czesc;
import car.serwis.helpers.AlertPopUp;
import car.serwis.helpers.UpdateStatus;
import car.serwis.helpers.ValidatorFields;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Kontroler widoku "updateCzesc.fxml"
 * klasa odpowiedzialna za aktualizowanie stanu magazynu po pobraniu części
 */
public class UpdateCzescController implements Initializable {
    @FXML
    private Button anulujButton;

    @FXML
    private ComboBox<Czesc> czescComboBox;

    @FXML
    private Text errorText;

    @FXML
    private Spinner<Double> iloscPobranejCzesci;

    @FXML
    private Button updateButton;

    CzescDao czescDao = new CzescDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iloscPobranejCzesci.setEditable(true);
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0,99.00,0.0,0.1);
        iloscPobranejCzesci.setValueFactory(valueFactory);
        anulujButton.setOnAction(SceneController::close);
        czescComboBox.setItems(getCzesciObservableList());
        updateCzesc();
    }

    /**
     * Metoda pobierająca części z bazy i dodająca ich ObservableList
     * @return zwraca ObservableList części z bazy
     */
    private ObservableList<Czesc> getCzesciObservableList() {
        ObservableList<Czesc> list = FXCollections.observableArrayList();
        list.addAll(czescDao.displayRecords());
        return list;
    }

    /**
     * Metoda nasluchujaca button i aktualizująca ilość częsci
     */
    private void updateCzesc(){
        updateButton.setOnAction((event) -> {
            if (validateInputs()){
                Czesc czesc = new Czesc();
                czesc.setIdCzesc(czescComboBox.getValue().getIdCzesc());
                Czesc czescBaza = czescDao.getCzesc(czesc);

                czesc.setIlosc(iloscPobranejCzesci.getValue());

                if (czescBaza.getIlosc() - czesc.getIlosc() < 0){
                    AlertPopUp.successAlert("Nie ma wystarczajacej ilości części w magazynie!");
                }else {
                    czescBaza.setIlosc(czescBaza.getIlosc() - czesc.getIlosc());
                    czescDao.updateCzesc(czescBaza);
                    delayWindowClose(event);
                    UpdateStatus.setIsCzescUpdated(true);
                    AlertPopUp.successAlert("Pobrano z magazynu!");
                }
            }
        });
    }

    /**
     * Metoda walidująca pola aktualizowania ilości części
     * @return zwraca true jeżeli walidacja przeszła pomyślnie
     */
    private boolean validateInputs() {
        /** Walidacja pola część */
        if (czescComboBox.getValue() == null){
            errorText.setText("Nie wybrano części!");
            return false;
        }

        /** Walidacja pola ilość */
        if (iloscPobranejCzesci.getValue() == 0){
            errorText.setText("Niepoprawna wartość pola ilość!");
            return false;
        }else if (ValidatorFields.isBlank(iloscPobranejCzesci.getValue().toString())){
            errorText.setText("Pole ilość nie może być puste!");
            return false;
        }else if (!ValidatorFields.isDecimal(iloscPobranejCzesci.getValue().toString())){
            errorText.setText("Niepoprawna wartość pola ilość!");
            return false;
        }
        return true;
    }


    /**
     * Metoda zamykająca okno z opóźnieniem po aktualizacji części
     */
    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    /**
     * Metoda zamykająca okno "updateCzesc.fxml"
     */
    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
