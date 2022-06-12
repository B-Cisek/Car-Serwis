package car.serwis.controller;

import car.serwis.database.dao.JednostkaDao;
import car.serwis.database.model.Jednostka;
import car.serwis.helpers.AlertPopUp;
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

public class AddJednostkaController implements Initializable {
    @FXML
    private AnchorPane JednostkaAnchorePane;

    @FXML
    private Button addJednostkaButton;

    @FXML
    private Button anulujButton;

    @FXML
    private Text errorText;

    @FXML
    private TextField nazwaJednostkiTextField;

    @FXML
    private TextField skrotTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeExitButton();
        saveNewJednostka();
    }

    private void saveNewJednostka() {
        addJednostkaButton.setOnAction((event) -> {
            if (validateInputs()) {
                Jednostka jednostka = createJednostkaFromInput();
                boolean isSaved = new JednostkaDao().createJednostka(jednostka);

                if (isSaved) {
                    UpdateStatus.setIsJednostkaAdded(true);
                    errorText.setText("Jednostka dodana!");
                    errorText.setStyle("-fx-fill: #2CC97E; -fx-font-size: 15px;");
                    delayWindowClose(event);
                    AlertPopUp.successAlert("Jednostka dodana!");
                }
            }
        });
    }

    private boolean validateInputs() {
        if (nazwaJednostkiTextField.getText().isBlank()) {
            errorText.setText("*Pole nazwa nie może być puste!");
            return false;
        }

        if (skrotTextField.getText().isBlank()){
            errorText.setText("*Pole skrót nie może być puste!");
            return false;
        }

        return true;
    }

    private Jednostka createJednostkaFromInput() {
        Jednostka jednostka = new Jednostka();

        jednostka.setNazwaJednostki(nazwaJednostkiTextField.getText());
        jednostka.setSkrot(skrotTextField.getText());

        return jednostka;
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
        return (Stage) JednostkaAnchorePane.getScene().getWindow();
    }

    private void initializeExitButton(){
        anulujButton.setOnAction((x) -> {
            getStage().close();
        });
    }

}
