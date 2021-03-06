package car.serwis.controller;

import car.serwis.database.dao.FakturaDao;
import car.serwis.database.dao.KontrahentDao;
import car.serwis.database.dao.PozycjaFakturyDao;
import car.serwis.database.model.Faktura;
import car.serwis.database.model.Kontrahent;
import car.serwis.database.model.PozycjaFaktury;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Kontroler widoku "addFaktura.fxml"
 * klasa odpowiedzialna za dodanie faktury do bazy
 */
public class AddFakturaController implements Initializable {
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
    Faktura faktura = new Faktura();
    PozycjaFakturyDao pozycjaFakturyDao = new PozycjaFakturyDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancelButton.setOnAction(SceneController::close);
        kontrahentComboBox.setItems(getKontrahentObservableList());
        addPozycja();
        deletePozycja();
        addFaktura();
    }

    /**
     * Metoda dodaj??ca pozycje na fakturze
     */
    private void addPozycja() {
        addPozycjaButton.setOnAction((event) -> {
            if (validatePozycjaInputs()) {
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

    /**
     * Metoda usuwaj??ca pozycje z faktury
     */
    private void deletePozycja() {
        deletePozycjaButton.setOnAction((event) -> {
            ObservableList<PozycjaFaktury> selectedRows = pozycjaTableView.getSelectionModel().getSelectedItems();
            for (PozycjaFaktury pozycjaFaktury : selectedRows) {
                pozycjeObservableList.remove(pozycjaFaktury);
            }
            pozycjaTableView.setItems(pozycjeObservableList);
        });
    }

    /**
     * Metoda dodaj??ca faktur?? do bazy
     */
    private void addFaktura() {
        addFakturaButton.setOnAction((event) -> {
            if (pozycjeObservableList.isEmpty()) {
                AlertPopUp.successAlert("Dodaj pozycje faktury!");
            } else {
                if (validateFakturaInputs()) {
                    Faktura faktura = createFakturaFromInput();
                    boolean isSaved = new FakturaDao().createFaktura(faktura);
                    for (PozycjaFaktury pozycjaFaktury : pozycjeObservableList) {
                        pozycja.setOpisPozycji(pozycjaFaktury.getOpisPozycji());
                        pozycja.setIlosc(pozycjaFaktury.getIlosc());
                        pozycja.setCena(pozycjaFaktury.getCena());
                        pozycja.setFaktura(faktura);
                        pozycjaFakturyDao.createPozycjaFaktury(pozycja);
                    }
                    if (isSaved) {
                        UpdateStatus.setIsFakturaAdded(true);
                        delayWindowClose(event);
                        AlertPopUp.successAlert("Faktura dodana!");
                    }
                }
                pozycjaTableView.setItems(pozycjeObservableList);
            }
        });
    }

    /**
     * Metoda pobieraj??ca z bazy obiekty kontrahent i dodj??ca je do ObservableList
     * @return zwraca ObservableList kontrahent??w
     */
    private ObservableList<Kontrahent> getKontrahentObservableList() {
        ObservableList<Kontrahent> list = FXCollections.observableArrayList();
        list.addAll(new KontrahentDao().getKontrahent());
        return list;
    }

    /**
     * Metoda tworz??ca obiekt faktura na podstawie pobranych p??l z widoku "addFaktura.fxml"
     * @return zwraca obiekt Faktura
     */
    private Faktura createFakturaFromInput() {
        faktura.setNumerFaktury(numerFakturyTextField.getText());
        faktura.setDataWystawienia(dataWystawieniaDatePicker.getValue());
        faktura.setMiejsceWystawienia(miejsceWystawieniaTextField.getText());
        faktura.setKontrahent(kontrahentComboBox.getValue());

        return faktura;
    }

    /**
     * Metoda waliduj??ca pola pozycje faktury
     * @return zwraca true je??eli walidacja przesz??a pomy??lnie
     */
    private boolean validatePozycjaInputs() {
        /** Walidacja pola opis */
        if (ValidatorFields.isBlank(opisPozycjaTextField.getText())){
            errorTextPozycja.setText("Pole opis nie mo??e by?? puste!");
            return false;
        }

        /** Walidacja pola ilo???? */
        if (ValidatorFields.isBlank(iloscPozycjaTextField.getText())){
            errorTextPozycja.setText("Pole ilo???? nie mo??e by?? puste!");
            return false;
        }else if (!ValidatorFields.isNumeric(iloscPozycjaTextField.getText())){
            errorTextPozycja.setText("Nieprawid??owa warto???? pola ilo????!");
            return false;
        }

        /** Walidacja pola cena */
        if (ValidatorFields.isBlank(cenaPozycjaTextField.getText())){
            errorTextPozycja.setText("Pole cena nie mo??e by?? puste!");
            return false;
        }else if (!ValidatorFields.isDecimal(cenaPozycjaTextField.getText())){
            errorTextPozycja.setText("Nieprawid??owa warto???? pola cena!");
            return false;
        }

        return true;
    }


    /**
     * Metoda waliduj??ca pola faktury
     * @return zwraca true je??eli walidacja przesz??a pomy??lnie
     */
    private boolean validateFakturaInputs() {
        /** Walidacja pola miejsce */
        if (ValidatorFields.isBlank(miejsceWystawieniaTextField.getText())){
            errorTextFaktura.setText("Pole miejsce nie mo??e by?? puste!");
            return false;
        }else if (!ValidatorFields.isText(miejsceWystawieniaTextField.getText())){
            errorTextFaktura.setText("Nieprawid??owa warto???? pola miejsce!");
            return false;
        }

        /** Walidacja pola data */
        if (dataWystawieniaDatePicker.getValue() == null) {
            errorTextFaktura.setText("Pole data nie mo??e by?? puste!");
            return false;
        }

        /** Walidacja pola kontrahent */
        if (kontrahentComboBox.getValue() == null) {
            errorTextFaktura.setText("Pole kontrahent nie mo??e by?? puste!");
            return false;
        }

        /** Walidacja pola numer */
        if (ValidatorFields.isBlank(numerFakturyTextField.getText())){
            errorTextFaktura.setText("Pole numer nie mo??e by?? puste!");
            return false;
        }else if (!ValidatorFields.isNumerFaktury(numerFakturyTextField.getText())){
            errorTextFaktura.setText("Nieprawid??owa warto???? pola numer!");
            return false;
        }
        return true;
    }

    /**
     * Metoda zamykaj??ca okno z op????nieniem po dodaniu faktury
     */
    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    /**
     * Metoda zamykaj??ca okno "addFaktura.fxml"
     */
    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
