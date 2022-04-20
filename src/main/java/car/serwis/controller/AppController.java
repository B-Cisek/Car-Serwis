package car.serwis.controller;

import car.serwis.database.dao.StanowiskoDao;
import car.serwis.database.model.Stanowisko;

import car.serwis.helpers.ScenePath;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private TableView<Stanowisko> StanowiskoTable;

    @FXML
    private TableColumn<Stanowisko, Long> idStanowiskoColumn;

    @FXML
    private TableColumn<Stanowisko, String> nazwaColumn;

    @FXML
    private Button openSceneNewPracownik;

    @FXML
    private Button openSceneNewStanowisko;

    ScenePath scenePath;
    StanowiskoDao stanowiskoDao = new StanowiskoDao();
    ObservableList<Stanowisko> vetsObList = FXCollections.observableArrayList();

    private void openAddStanowiskoWindow(){
        openSceneNewStanowisko.setOnAction((x) ->{
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/fxml/addStanowisko.fxml"));
                Scene scene = new Scene(parent,600,400);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        openAddStanowiskoWindow();
    }
}



