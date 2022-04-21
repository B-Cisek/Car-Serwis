package car.serwis.controller;

import car.serwis.database.dao.StanowiskoDao;
import car.serwis.database.model.Stanowisko;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPracownikController implements Initializable {

    @FXML
    private Button addPracownikButton;

    @FXML
    private TextField hasloTextField;

    @FXML
    private TextField imieTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField nazwiskoTextField;

    @FXML
    private DatePicker pracujeOdDatePicker;

    @FXML
    private ComboBox<Stanowisko> stanowiksoComboBox;

    ObservableList<Stanowisko> stanowiskaObservableList = FXCollections.observableArrayList();

    private void getStanowiskaObservableList() {
        StanowiskoDao stanowiskoDao = new StanowiskoDao();
        stanowiskaObservableList.addAll(stanowiskoDao.getStanowiska());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stanowiksoComboBox.setItems(stanowiskaObservableList);
    }


}
