package car.serwis.controller;

import car.serwis.database.dao.*;
import car.serwis.database.model.*;
import car.serwis.helpers.AlertPopUp;
import car.serwis.helpers.UpdateStatus;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCzescController implements Initializable {
    @FXML
    private AnchorPane czescAnchorePane;

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
    private Button anulujButton;

    @FXML
    private Text errorText;

    @FXML
    private Button addCzescButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeExitButton();
        samochodCzescComboBox.setItems(getSamochodObservableList());
        jednostkaCzescComboBox.setItems(getJednostkaObservableList());
        kategoriaCzescComboBox.setItems(getKategoriaObservableList());
        saveNewCzesc();
    }

    private ObservableList<Samochod> getSamochodObservableList() {
        ObservableList<Samochod> list = FXCollections.observableArrayList();
        list.addAll(new SamochodDao().getSamochody());
        return list;
    }

    private ObservableList<Jednostka> getJednostkaObservableList() {
        ObservableList<Jednostka> list = FXCollections.observableArrayList();
        list.addAll(new JednostkaDao().getJednostki());
        return list;
    }

    private ObservableList<Kategoria> getKategoriaObservableList() {
        ObservableList<Kategoria> list = FXCollections.observableArrayList();
        list.addAll(new KategoriaDao().getKategorie());
        return list;
    }

    private void saveNewCzesc() {
        addCzescButton.setOnAction((event) -> {
            if (validateInputs()) {
                Czesc czesc = createCzescFromInput();
                boolean isSaved = new CzescDao().createCzesc(czesc);

                if (isSaved) {
                    UpdateStatus.setIsCzescAdded(true);
                    errorText.setText("Część dodana!");
                    errorText.setStyle("-fx-fill: #2CC97E; -fx-font-size: 15px;");
                    delayWindowClose(event);
                    AlertPopUp.successAlert("Część dodana!");
                }
            }
        });
    }

    private boolean validateInputs() {
        if (nazwaCzescTextField.getText().isBlank()) {
            errorText.setText("*Pole nazwa nie może być puste!");
            return false;
        }

        if (opisCzescTextArea.getText().isBlank()){
            errorText.setText("*Pole opis nie może być puste!");
            return false;
        }

        if (producentCzescTextField.getText().isBlank()){
            errorText.setText("*Pole producent nie może być puste!");
            return false;
        }

        // TODO walidacja double
        if (iloscCzescTextField.getText().isBlank()){
            errorText.setText("*Pole ilość nie może być puste!");
            return false;
        }

        if (kategoriaCzescComboBox.getValue() == null){
            errorText.setText("*Pole ilość nie może być puste!");
            return false;
        }

        if (samochodCzescComboBox.getValue() == null){
            errorText.setText("*Pole samochód nie może być puste!");
            return false;
        }

        if (jednostkaCzescComboBox.getValue() == null){
            errorText.setText("*Pole jednostka nie może być puste!");
            return false;
        }

        return true;
    }

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

    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private Stage getStage(){
        return (Stage) czescAnchorePane.getScene().getWindow();
    }

    private void initializeExitButton(){
        anulujButton.setOnAction((x) -> {
            getStage().close();
        });
    }
}
