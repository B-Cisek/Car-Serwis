package car.serwis.controller;

import car.serwis.database.dao.ZlecenieDao;
import car.serwis.database.model.Zlecenie;
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
 * Klasa odpowiedzialna za scene PULPIT
 */
public class PulpitController implements Initializable{
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

    private void setObservableList() {
        ostatnioPrzyjeteObservableList.clear();
        ostatnioPrzyjeteObservableList.addAll(zlecenieDao.getRecentNoweZlecenia());

        ostatnioWydaneObservableList.clear();
        ostatnioWydaneObservableList.addAll(zlecenieDao.getRecentGotoweZlecenia());
    }

    public void setZlecenia(){
        int noweSprawyCount = 0;
        int oczekujaceSprawyCount = 0;
        int wTrakcieSprawyCount = 0;
        int gotoweSprawyCount = 0;

        for (Zlecenie zlecenie : listaZlecen) {
            if (zlecenie.getStatus().equals(ZlecenieStatus.NOWE)){
                noweSprawyCount++;
            }else if (zlecenie.getStatus().equals(ZlecenieStatus.OCZEKUJACE)){
                oczekujaceSprawyCount++;
            }else if (zlecenie.getStatus().equals(ZlecenieStatus.W_TRAKCIE)){
                wTrakcieSprawyCount++;
            }else if (zlecenie.getStatus().equals(ZlecenieStatus.GOTOWE)){
                gotoweSprawyCount++;
            }
        }
        noweSprawy.setText(String.valueOf(noweSprawyCount));
        oczekujaceSprawy.setText(String.valueOf(oczekujaceSprawyCount));
        wTrakcieSprawy.setText(String.valueOf(wTrakcieSprawyCount));
        gotoweSprawy.setText(String.valueOf(gotoweSprawyCount));
    }

    private void fillTables() {
        // OSTATNIO PRZYJETE
        dataOstatnioPrzyjeteTableCell.setCellValueFactory(new PropertyValueFactory<>("dataPrzyjecia"));
        samochodOstatnioPrzyjeteTableCell.setCellValueFactory(new PropertyValueFactory<>("samochod"));
        statusOstatnioPrzyjeteTableCell.setCellValueFactory(new PropertyValueFactory<>("status"));
        kontrahentOstatnioPrzyjeteTableCell.setCellValueFactory(new PropertyValueFactory<>("kontrahent"));

        // OSTATNIO WYDANE
        dataOstatnioWydaneTableCell.setCellValueFactory(new PropertyValueFactory<>("dataPrzyjecia"));
        samochodOstatnioWydaneTableCell.setCellValueFactory(new PropertyValueFactory<>("samochod"));
        statusOstatnioWydaneTableCell.setCellValueFactory(new PropertyValueFactory<>("status"));
        kontrahentOstatnioWydaneTableCell.setCellValueFactory(new PropertyValueFactory<>("kontrahent"));
    }

    private void addTableSettings() {
        ostatnioPrzyjeteZlecenia.setEditable(true);
        ostatnioPrzyjeteZlecenia.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ostatnioPrzyjeteZlecenia.setItems(ostatnioPrzyjeteObservableList);

        ostatnioWydaneZlecenia.setEditable(true);
        ostatnioWydaneZlecenia.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ostatnioWydaneZlecenia.setItems(ostatnioWydaneObservableList);
    }


    @FXML
    void showZleceniaScreen(ActionEvent event) throws IOException {
        SceneController.getZleceniaScene(event);
    }

    @FXML
    void showWarsztatScreen(ActionEvent event) throws IOException {
        SceneController.getWarsztatScene(event);
    }

    @FXML
    void showKsiegowoscScreen(ActionEvent event) throws IOException {
        SceneController.getKsiegowoscScene(event);
    }

    @FXML
    void showMagazynScreen(ActionEvent event) throws IOException {
        SceneController.getMagazynScene(event);
    }

    @FXML
    void showUstawieniaScreen(ActionEvent event) throws IOException {
        SceneController.getUstawieniaScene(event);
    }

    @FXML
    void showPomocScreen(ActionEvent event) throws IOException {
        SceneController.getPomocScene(event);
    }

}
