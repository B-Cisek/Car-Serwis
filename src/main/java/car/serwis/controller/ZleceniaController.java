package car.serwis.controller;

import car.serwis.database.dao.ZlecenieDao;
import car.serwis.database.model.Stanowisko;
import car.serwis.database.model.Zlecenie;
import car.serwis.helpers.UpdateStatus;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ZleceniaController implements Initializable {

    @FXML
    private TableColumn<Zlecenie, LocalDate> dataPrzyjeciaTableColumn;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Zlecenie, Long> idZlecenieTableColumn;

    @FXML
    private TableColumn<Zlecenie, String> klientTableColumn;

    @FXML
    private TableColumn<Zlecenie, String> markaTableColumn;

    @FXML
    private TableColumn<Zlecenie, String> modelTableColumn;

    @FXML
    private TableColumn<Zlecenie, String> opisTableColumn;

    @FXML
    private Text pracownikInfo;

    @FXML
    private TableColumn<Zlecenie, String> pracownikTableColumn;

    @FXML
    private TableColumn<Zlecenie, Enum> statusTableColumn;

    @FXML
    private TableView<Zlecenie> zleceniaTableView;

    ZlecenieDao zlecenieDao = new ZlecenieDao();
    ObservableList<Zlecenie> zleceniaObservableList = FXCollections.observableArrayList();


    private void setZleceniaObservableList() {
        zleceniaObservableList.clear();
        zleceniaObservableList.addAll(zlecenieDao.getZlecenia());
    }

    private void fillTableZlecenia() {
        idZlecenieTableColumn.setCellValueFactory(new PropertyValueFactory<>("idZlecenie"));
        dataPrzyjeciaTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataPrzyjecia"));
        opisTableColumn.setCellValueFactory(new PropertyValueFactory<>("opis"));
        statusTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


    }

    private void addTableSettingsZlecenie() {
        zleceniaTableView.setEditable(true);
        zleceniaTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        zleceniaTableView.setItems(zleceniaObservableList);
    }


    @FXML
    private void addZlecenieWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewZlecenieWindow();
        if(UpdateStatus.isZadanieAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsZadanieAdded(false);
        }
    }



    @FXML
    void showPulpitScreen(ActionEvent event) throws IOException {
        SceneController.getPulpitScene(event);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setZleceniaObservableList();
        fillTableZlecenia();
        addTableSettingsZlecenie();
    }

    @FXML
    void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getZleceniaScene(event);
    }
}
