module com.example.hopital {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;


    opens com.example.hopital to javafx.fxml;
    exports com.example.hopital;
    opens com.example.hopital.controllers to javafx.fxml;
    exports com.example.hopital.controllers;
    opens com.example.hopital.entites ;
}