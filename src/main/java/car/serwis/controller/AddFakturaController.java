package car.serwis.controller;

import car.serwis.database.dao.FakturaDao;
import car.serwis.database.dao.KontrahentDao;
import car.serwis.database.dao.PozycjaFakturyDao;
import car.serwis.database.dao.StanowiskoDao;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class AddFakturaController implements Initializable {
    @FXML
    private AnchorPane addFakturaAnchorePane;

    @FXML
    private TableColumn<PozycjaFaktury, Double> cenaTableColumn;

    @FXML
    private DatePicker dataWystawieniaDatePicker;

    @FXML
    private TableColumn<PozycjaFaktury, Integer> iloscTableColumn;

    @FXML
    private ComboBox<Kontrahent> kontrahentComboBox;

    @FXML
    private TextField miejsceWystawieniaTextField;

    @FXML
    private TableColumn<PozycjaFaktury, String> opisTableColumn;

    @FXML
    private TableView<PozycjaFaktury> pozycjaTableView;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField opisPozycjaTextField;

    @FXML
    private TextField iloscPozycjaTextField;

    @FXML
    private TextField cenaPozycjaTextField;

    @FXML
    private Button addPozycjaButton;

    @FXML
    private Button deletePozycjaButton;

    @FXML
    private Button addFakturaButton;


    @FXML
    private Text errorTextFaktura;

    @FXML
    private Text errorTextPozycja;

    @FXML
    private TextField numerFakturyTextField;





    ObservableList<PozycjaFaktury> pozycjeObservableList = FXCollections.observableArrayList();
    PozycjaFaktury pozycja = new PozycjaFaktury();
    Set<PozycjaFaktury> listaPozycji = new HashSet<>();
    FakturaDao fakturaDao = new FakturaDao();
    Faktura faktura = new Faktura();
    PozycjaFakturyDao pozycjaFakturyDao = new PozycjaFakturyDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeExitButton();
        kontrahentComboBox.setItems(getKontrahentObservableList());
        addPozycja();
        deletePozycja();
        addFaktura();
    }






    private void addPozycja() {
        addPozycjaButton.setOnAction((event) -> {
            if(validatePozycjaInputs()){
                PozycjaFaktury pozycjaFaktury = new PozycjaFaktury();
                pozycjaFaktury.setOpisPozycji(opisPozycjaTextField.getText());
                pozycjaFaktury.setIlosc(Integer.valueOf(iloscPozycjaTextField.getText()));
                pozycjaFaktury.setCena(Double.valueOf(cenaPozycjaTextField.getText()));
                pozycjeObservableList.add(pozycjaFaktury);

                opisTableColumn.setCellValueFactory(new PropertyValueFactory<>("opisPozycji"));
                iloscTableColumn.setCellValueFactory(new PropertyValueFactory<>("ilosc"));
                cenaTableColumn.setCellValueFactory(new PropertyValueFactory<>("cena"));

                pozycjaTableView.setItems(pozycjeObservableList);
                opisPozycjaTextField.clear();
                iloscPozycjaTextField.clear();
                cenaPozycjaTextField.clear();
            }
        });
    }

    private void deletePozycja() {
        deletePozycjaButton.setOnAction((event) -> {
            ObservableList<PozycjaFaktury> selectedRows = pozycjaTableView.getSelectionModel().getSelectedItems();
            for (PozycjaFaktury pozycjaFaktury : selectedRows) {
                pozycjeObservableList.remove(pozycjaFaktury);
            }
            pozycjaTableView.setItems(pozycjeObservableList);
        });
    }

    private void addFaktura() {
        addFakturaButton.setOnAction((event) -> {
            if (validateFakturaInputs()){
                Faktura faktura = createFakturaFromInput();
                boolean isSaved = new FakturaDao().createFaktura(faktura);
                for (PozycjaFaktury pozycjaFaktury: pozycjeObservableList) {
                    pozycja.setOpisPozycji(pozycjaFaktury.getOpisPozycji());
                    pozycja.setIlosc(pozycjaFaktury.getIlosc());
                    pozycja.setCena(pozycjaFaktury.getCena());
                    pozycja.setFaktura(faktura);
                    pozycjaFakturyDao.createPozycjaFaktury(pozycja);
                }
                if (isSaved) {
                    UpdateStatus.setIsFakturaAdded(true);
                    errorTextFaktura.setText("Faktura dodana!");
                    errorTextFaktura.setStyle("-fx-text-fill: #2CC97E; -fx-font-size: 15px;");
                    delayWindowClose(event);
                    AlertPopUp.successAlert("Faktura dodana!");
                }
            }
            pozycjaTableView.setItems(pozycjeObservableList);
        });
    }

    private ObservableList<Kontrahent> getKontrahentObservableList() {
        ObservableList<Kontrahent> list = FXCollections.observableArrayList();
        list.addAll(new KontrahentDao().getKontrahent());
        return list;
    }


    private Faktura createFakturaFromInput() {
        faktura.setNumerFaktury(numerFakturyTextField.getText());
        faktura.setDataWystawienia(dataWystawieniaDatePicker.getValue());
        faktura.setMiejsceWystawienia(miejsceWystawieniaTextField.getText());
        faktura.setKontrahent(kontrahentComboBox.getValue());

        return faktura;
    }

    private boolean validatePozycjaInputs() {
        if (opisPozycjaTextField.getText().isBlank()) {
            errorTextPozycja.setText("*Pole opis nie może być puste!");
            return false;
        }

        if(iloscPozycjaTextField.getText().isBlank()) {
            errorTextPozycja.setText("*Pole ilość nie może być puste!");
            return false;
        }

        if (cenaPozycjaTextField.getText().isBlank()) {
            errorTextPozycja.setText("*Pole cena nie może być puste!");
            return false;
        }
        return true;
    }



    private boolean validateFakturaInputs() {
        if (miejsceWystawieniaTextField.getText().isBlank()) {
            errorTextFaktura.setText("*Pole miejsce nie może być puste!");
            return false;
        }

        if(dataWystawieniaDatePicker.getValue() == null) {
            errorTextFaktura.setText("*Pole data nie może być puste!");
            return false;
        }

        if (kontrahentComboBox.getValue() == null) {
            errorTextFaktura.setText("*Pole kontrahent nie może być puste!");
            return false;
        }

        if (numerFakturyTextField.getText().isBlank()) {
            errorTextFaktura.setText("*Pole numer nie może być puste!");
            return false;
        }
        return true;
    }




    private Stage getStage(){
        return (Stage) addFakturaAnchorePane.getScene().getWindow();
    }

    private void initializeExitButton(){
        cancelButton.setOnAction((x) -> {
            getStage().close();
        });
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

}
