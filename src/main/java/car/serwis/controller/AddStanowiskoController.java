package car.serwis.controller;

import car.serwis.database.dao.StanowiskoDao;
import car.serwis.database.model.Stanowisko;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class AddStanowiskoController implements Initializable {

    @FXML
    private Button addStanowiskoButton;

    @FXML
    private Text errorText;

    @FXML
    private TextField nazwaTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveNewStanowisko();
    }


    @FXML
    private void saveNewStanowisko() {
        addStanowiskoButton.setOnAction((x) -> {
            if (validateInputs()) {
                Stanowisko stanowisko = createStanowiskoFromInput();
                boolean isSaved = new StanowiskoDao().createStanowisko(stanowisko);

                if (isSaved) {
                    errorText.setText("Vet is added!");
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

    private Stanowisko createStanowiskoFromInput() {
        Stanowisko stanowisko = new Stanowisko();

        stanowisko.setNazwa(nazwaTextField.getText());

        return stanowisko;
    }

}
