package car.serwis.controller;

import car.serwis.database.dao.CzescDao;
import car.serwis.database.model.Czesc;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCzescController implements Initializable {
    @FXML
    private AnchorPane addCzescAnchorePane;

    @FXML
    private Button anulujButton;

    @FXML
    private ComboBox<Czesc> czescComboBox;

    @FXML
    private Text errorText;

    @FXML
    private Spinner<Double> iloscCzesc;

    @FXML
    private Button updateButton;

    CzescDao czescDao = new CzescDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iloscCzesc.setEditable(true);
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0,99.00,0.0,0.1);
        iloscCzesc.setValueFactory(valueFactory);
        anulujButton.setOnAction(SceneController::close);
        czescComboBox.setItems(getCzesciObservableList());
        addCzesc();
    }

    private ObservableList<Czesc> getCzesciObservableList() {
        ObservableList<Czesc> list = FXCollections.observableArrayList();
        list.addAll(czescDao.displayRecords());
        return list;
    }

    private void addCzesc(){
        updateButton.setOnAction((event) -> {
            if (validateInputs()){
               Czesc czesc = czescComboBox.getValue();
               czesc.setIlosc(czesc.getIlosc() + iloscCzesc.getValue());
               czescDao.updateCzesc(czesc);
                delayWindowClose(event);
                UpdateStatus.setIsCzescUpdated(true);
                AlertPopUp.successAlert("Doano część!");
            }
        });
    }

    private boolean validateInputs() {
        if (czescComboBox.getValue() == null){
            errorText.setText("*Nie wybrano części");
            return false;
        }
        if (iloscCzesc.getValue() == 0){
            errorText.setText("*Niepoprawna wartość pola ilość!");
            return false;
        }
        return true;
    }

    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


}
