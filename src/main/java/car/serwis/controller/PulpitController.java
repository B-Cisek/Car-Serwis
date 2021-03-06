package car.serwis.controller;

import car.serwis.database.dao.ZlecenieDao;
import car.serwis.database.model.Zlecenie;
import car.serwis.helpers.AlertPopUp;
import car.serwis.helpers.CurrentPracownik;
import car.serwis.helpers.WindowManagement;
import car.serwis.helpers.ZlecenieStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Kontroler widoku "pulpit.fxml"
 */
public class PulpitController implements Initializable {
    @FXML
    private TableColumn<Zlecenie, String> samochodOstatnioPrzyjeteTableCell;

    @FXML
    private TableColumn<Zlecenie, LocalDate> dataOstatnioPrzyjeteTableCell;

    @FXML
    private TableColumn<Zlecenie, LocalDate> dataOstatnioWydaneTableCell;

    @FXML
    private Button exitButton;

    @FXML
    private Text gotoweSprawy;

    @FXML
    private TableColumn<Zlecenie, String> kontrahentOstatnioPrzyjeteTableCell;

    @FXML
    private TableColumn<Zlecenie, String> kontrahentOstatnioWydaneTableCell;

    @FXML
    private Button minimalizeButton;

    @FXML
    private Text noweSprawy;

    @FXML
    private Text oczekujaceSprawy;

    @FXML
    private TableView<Zlecenie> ostatnioPrzyjeteZlecenia;

    @FXML
    private TableView<Zlecenie> ostatnioWydaneZlecenia;

    @FXML
    private Text pracownikInfo;

    @FXML
    private BorderPane pulpitBorderPane;

    @FXML
    private TableColumn<Zlecenie, String> samochodOstatnioWydaneTableCell;

    @FXML
    private TableColumn<Zlecenie, ZlecenieStatus> statusOstatnioPrzyjeteTableCell;

    @FXML
    private TableColumn<Zlecenie, ZlecenieStatus> statusOstatnioWydaneTableCell;

    @FXML
    private Text wTrakcieSprawy;

    @FXML
    private Text sumaZlecen1;

    @FXML
    private Text sumaZlecen2;

    @FXML
    private Text sumaZlecen3;

    @FXML
    private Text sumaZlecen4;

    WindowManagement windowManagement = new WindowManagement();
    ZlecenieDao zlecenieDao = new ZlecenieDao();
    ObservableList<Zlecenie> ostatnioPrzyjeteObservableList = FXCollections.observableArrayList();
    ObservableList<Zlecenie> ostatnioWydaneObservableList = FXCollections.observableArrayList();
    List<Zlecenie> listaZlecen = zlecenieDao.getZlecenie();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        windowManagement.initializeMinimalizeButton(minimalizeButton, pulpitBorderPane);
        windowManagement.initializeExitButton(exitButton, pulpitBorderPane);
        CurrentPracownik.setPracownikInfo(pracownikInfo);
        setZlecenia();
        setObservableList();
        fillTables();
        addTableSettings();
    }

    /**
     * Metoda czyszcz??ca i wype??niaj??ca ObservableList
     */
    private void setObservableList() {
        /** ObservableList - ostatnio przyj??te zlecenia */
        ostatnioPrzyjeteObservableList.clear();
        ostatnioPrzyjeteObservableList.addAll(zlecenieDao.getRecentNoweZlecenia());

        /** ObservableList - ostatnio zako??czone zlecenia */
        ostatnioWydaneObservableList.clear();
        ostatnioWydaneObservableList.addAll(zlecenieDao.getRecentGotoweZlecenia());
    }

    /**
     * Metoda licz??ca i wy??wietlaj??ca ilo???? zlece?? dla ka??dego statusu
     */
    public void setZlecenia() {
        int noweSprawyCount = 0;
        int oczekujaceSprawyCount = 0;
        int wTrakcieSprawyCount = 0;
        int gotoweSprawyCount = 0;


        for (Zlecenie zlecenie : listaZlecen) {
            if (zlecenie.getStatus().equals(ZlecenieStatus.NOWE)) {
                noweSprawyCount++;
            } else if (zlecenie.getStatus().equals(ZlecenieStatus.OCZEKUJACE)) {
                oczekujaceSprawyCount++;
            } else if (zlecenie.getStatus().equals(ZlecenieStatus.W_TRAKCIE)) {
                wTrakcieSprawyCount++;
            } else if (zlecenie.getStatus().equals(ZlecenieStatus.GOTOWE)) {
                gotoweSprawyCount++;
            }
        }
        noweSprawy.setText(String.valueOf(noweSprawyCount));
        oczekujaceSprawy.setText(String.valueOf(oczekujaceSprawyCount));
        wTrakcieSprawy.setText(String.valueOf(wTrakcieSprawyCount));
        gotoweSprawy.setText(String.valueOf(gotoweSprawyCount));
        sumaZlecen1.setText("????cznie: " + listaZlecen.size());
        sumaZlecen2.setText("????cznie: " + listaZlecen.size());
        sumaZlecen3.setText("????cznie: " + listaZlecen.size());
        sumaZlecen4.setText("????cznie: " + listaZlecen.size());
    }

    /**
     * Metoda wype??niaj??ca kolumny tabeli
     */
    private void fillTables() {
        /** Kolumny Tabeli - Ostatnio przyj??te zlecenia */
        dataOstatnioPrzyjeteTableCell.setCellValueFactory(new PropertyValueFactory<>("dataPrzyjecia"));
        samochodOstatnioPrzyjeteTableCell.setCellValueFactory(new PropertyValueFactory<>("samochod"));
        statusOstatnioPrzyjeteTableCell.setCellValueFactory(new PropertyValueFactory<>("status"));
        kontrahentOstatnioPrzyjeteTableCell.setCellValueFactory(new PropertyValueFactory<>("kontrahent"));

        /** Kolumny Tabeli - Ostatnio zako??czone zlecenia */
        dataOstatnioWydaneTableCell.setCellValueFactory(new PropertyValueFactory<>("dataPrzyjecia"));
        samochodOstatnioWydaneTableCell.setCellValueFactory(new PropertyValueFactory<>("samochod"));
        statusOstatnioWydaneTableCell.setCellValueFactory(new PropertyValueFactory<>("status"));
        kontrahentOstatnioWydaneTableCell.setCellValueFactory(new PropertyValueFactory<>("kontrahent"));
    }

    /**
     * Metoda wype??niaj??ca tabele posortowanymi danymi
     */
    private void addTableSettings() {
        /** TableView - ostatnio przyj??te zlecenia */
        ostatnioPrzyjeteZlecenia.setEditable(true);
        ostatnioPrzyjeteZlecenia.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ostatnioPrzyjeteZlecenia.setItems(ostatnioPrzyjeteObservableList);

        /** TableView - ostatnio zako??czone zlecenia */
        ostatnioWydaneZlecenia.setEditable(true);
        ostatnioWydaneZlecenia.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ostatnioWydaneZlecenia.setItems(ostatnioWydaneObservableList);
    }


    @FXML
    public void showZleceniaScreen(ActionEvent event) throws IOException {
        SceneController.getZleceniaScene(event);
    }

    @FXML
    public void showWarsztatScreen(ActionEvent event) throws IOException {
        SceneController.getWarsztatScene(event);
    }

    @FXML
    public void showKsiegowoscScreen(ActionEvent event) throws IOException {
        SceneController.getKsiegowoscScene(event);
    }

    @FXML
    public void showMagazynScreen(ActionEvent event) throws IOException {
        SceneController.getMagazynScene(event);
    }

    @FXML
    public void showUstawieniaScreen(ActionEvent event) throws IOException {
        SceneController.getUstawieniaScene(event);
    }

    @FXML
    public void showPomocScreen(ActionEvent event) throws IOException {
        SceneController.getPomocScene(event);
    }

}
