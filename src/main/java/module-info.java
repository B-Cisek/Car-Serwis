module car.serwis.carserwis {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires lombok;
    requires jakarta.persistence;


    opens car.serwis to javafx.fxml;
    opens car.serwis.database.model;
    exports car.serwis;
    exports car.serwis.controller;
    opens car.serwis.controller to javafx.fxml;
}