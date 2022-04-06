module car.serwis.carserwis {
    requires javafx.controls;
    requires javafx.fxml;


    opens car.serwis.carserwis to javafx.fxml;
    exports car.serwis.carserwis;
}