module car.serwis.carserwis {
    requires javafx.controls;
    requires javafx.fxml;


    opens car.serwis.carserwis to javafx.fxml;
    exports car.serwis.carserwis;
    exports car.serwis.carserwis.controller;
    opens car.serwis.carserwis.controller to javafx.fxml;
}