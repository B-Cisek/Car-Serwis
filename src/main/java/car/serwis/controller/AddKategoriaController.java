package car.serwis.controller;

import car.serwis.database.dao.KategoriaDao;
import car.serwis.database.dao.SamochodDao;
import car.serwis.database.model.Kategoria;
import car.serwis.database.model.Samochod;
import car.serwis.helpers.UpdateStatus;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AddKategoriaController implements Initializable {
    @FXML
    private Button addKategoriaButton;

    @FXML
    private Button anulujButton;


    @FXML
    private AnchorPane kategoriaAnchorePane;

    @FXML
    private Text errorText;

    @FXML
    private TextField nazwaTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeExitButton();
        saveNewKategoria();
    }


    private void saveNewKategoria() {
        addKategoriaButton.setOnAction((event) -> {
            if (validateInputs()) {
                Kategoria kategoria = createKategoriaFromInput();
                boolean isSaved = new KategoriaDao().createKategoria(kategoria);

                if (isSaved) {
                    UpdateStatus.setIsKategoriaAdded(true);
                    errorText.setText("Kategoira dodana!");
                    errorText.setStyle("-fx-text-fill: #2CC97E; -fx-font-size: 15px;");
                    delayWindowClose(event);
                }
            }
        });
    }

    private boolean validateInputs() {
        if (nazwaTextField.getText().equals("")) {
            errorText.setText("*Pole nazwa nie może być puste!");
            return false;
        }

        return true;
    }

    private Kategoria createKategoriaFromInput() {
        Kategoria kategoria = new Kategoria();

        kategoria.setNazwaKategori(nazwaTextField.getText());

        return kategoria;
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


    private void initializeExitButton(){
        anulujButton.setOnAction((x) -> {
            getStage().close();
        });
    }

    private Stage getStage(){
        return (Stage) kategoriaAnchorePane.getScene().getWindow();
    }
}
