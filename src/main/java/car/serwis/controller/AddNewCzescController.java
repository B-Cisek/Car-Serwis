package car.serwis.controller;

import car.serwis.database.dao.CzescDao;
import car.serwis.database.dao.JednostkaDao;
import car.serwis.database.dao.KategoriaDao;
import car.serwis.database.dao.SamochodDao;
import car.serwis.database.model.Czesc;
import car.serwis.database.model.Jednostka;
import car.serwis.database.model.Kategoria;
import car.serwis.database.model.Samochod;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Kontroler widoku "addNowaCzesc.fxml"
 * klasa odpowiedzialna za dodanie nowej części do bazy
 */
public class AddNewCzescController implements Initializable {
    @FXML
    private TextField iloscCzescTextField;

    @FXML
    private ComboBox<Jednostka> jednostkaCzescComboBox;

    @FXML
    private ComboBox<Kategoria> kategoriaCzescComboBox;

    @FXML
    private TextField nazwaCzescTextField;

    @FXML
    private TextArea opisCzescTextArea;

    @FXML
    private TextField producentCzescTextField;

    @FXML
    private ComboBox<Samochod> samochodCzescComboBox;

    @FXML
    private Text errorText;

    @FXML
    private Button addCzescButton;

    @FXML
    private Button anulujButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anulujButton.setOnAction(SceneController::close);
        samochodCzescComboBox.setItems(getSamochodObservableList());
        jednostkaCzescComboBox.setItems(getJednostkaObservableList());
        kategoriaCzescComboBox.setItems(getKategoriaObservableList());
        saveNewCzesc();
    }

    /**
     * Metoda pobierająca samochody z bazy i dodająca ich ObservableList
     * @return zwraca ObservableList samochodów z bazy
     */
    private ObservableList<Samochod> getSamochodObservableList() {
        ObservableList<Samochod> list = FXCollections.observableArrayList();
        list.addAll(new SamochodDao().getSamochody());
        return list;
    }

    /**
     * Metoda pobierająca jednostki z bazy i dodająca ich ObservableList
     * @return zwraca ObservableList jednostki z bazy
     */
    private ObservableList<Jednostka> getJednostkaObservableList() {
        ObservableList<Jednostka> list = FXCollections.observableArrayList();
        list.addAll(new JednostkaDao().getJednostki());
        return list;
    }

    /**
     * Metoda pobierająca kategorie z bazy i dodająca ich ObservableList
     * @return zwraca ObservableList kategorii z bazy
     */
    private ObservableList<Kategoria> getKategoriaObservableList() {
        ObservableList<Kategoria> list = FXCollections.observableArrayList();
        list.addAll(new KategoriaDao().getKategorie());
        return list;
    }

    /**
     * Metoda nasluchujaca button i dodająca nową część
     */
    private void saveNewCzesc() {
        addCzescButton.setOnAction((event) -> {
            if (validateInputs()) {
                Czesc czesc = createCzescFromInput();
                boolean isSaved = new CzescDao().createCzesc(czesc);

                if (isSaved) {
                    UpdateStatus.setIsCzescAdded(true);
                    delayWindowClose(event);
                    AlertPopUp.successAlert("Część dodana!");
                }
            }
        });
    }

    /**
     * Metoda walidująca pola części
     * @return zwraca true jeżeli walidacja przeszła pomyślnie
     */
    private boolean validateInputs() {
        /** Walidacja pola nazwa */
        if (ValidatorFields.isBlank(nazwaCzescTextField.getText())){
            errorText.setText("Pole nazwa nie może być puste!");
            return false;
        }

        /** Walidacja pola opis */
        if (ValidatorFields.isBlank(opisCzescTextArea.getText())){
            errorText.setText("Pole opis nie może być puste!");
            return false;
        }

        /** Walidacja pola producent */
        if (ValidatorFields.isBlank(producentCzescTextField.getText())){
            errorText.setText("Pole producent nie może być puste!");
            return false;
        }

        /** Walidacja pola ilość */
        if (ValidatorFields.isBlank(iloscCzescTextField.getText())){
            errorText.setText("Pole ilość nie może być puste!");
            return false;
        }else if (!ValidatorFields.isDecimal(iloscCzescTextField.getText())){
            errorText.setText("Nieprawidłowa wartość pola ilość!");
            return false;
        }

        /** Walidacja pola kategoria */
        if (kategoriaCzescComboBox.getValue() == null){
            errorText.setText("*Pole kategoria nie może być puste!");
            return false;
        }

        /** Walidacja pola samochód */
        if (samochodCzescComboBox.getValue() == null){
            errorText.setText("*Pole samochód nie może być puste!");
            return false;
        }

        /** Walidacja pola jednostka */
        if (jednostkaCzescComboBox.getValue() == null){
            errorText.setText("*Pole jednostka nie może być puste!");
            return false;
        }

        return true;
    }


    /**
     * Metoda tworząca obiekt czesc na podstawie pobranych pól z widoku "addNowaCzesc.fxml"
     * @return zwraca obiekt Kategoria
     */
    private Czesc createCzescFromInput() {
        Czesc czesc = new Czesc();
        czesc.setNazwaCzesci(nazwaCzescTextField.getText());
        czesc.setOpisCzesc(opisCzescTextArea.getText());
        czesc.setProducent(producentCzescTextField.getText());
        czesc.setIlosc(Double.valueOf(iloscCzescTextField.getText()));
        czesc.setJednostka(jednostkaCzescComboBox.getValue());
        czesc.setKategoria(kategoriaCzescComboBox.getValue());
        czesc.setSamochod(samochodCzescComboBox.getValue());
        return czesc;
    }


    /**
     * Metoda zamykająca okno z opóźnieniem po dodaniu części
     */
    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    /**
     * Metoda zamykająca okno "addNewCzesc.fxml"
     */
    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
