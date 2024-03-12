package com.example.hopital.controllers;
import com.example.hopital.entites.Medecin;
import com.example.hopital.entites.PatientMedecin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import javax.persistence.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PatientMedecinControlleur implements Initializable {
    EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = managerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    @FXML
    private TableView<PatientMedecin> medecinPatientTbl;

    @FXML
    private TableColumn<PatientMedecin, Integer> idCol;

    @FXML
    private TableColumn<PatientMedecin, String> nomPatientCol;

    @FXML
    private TableColumn<PatientMedecin, String> nomMedecinCol;

    @FXML
    private Button ajouterBtn;

    @FXML
    private Button supprimerBtn;

    @FXML
    private Button modifierBtn;

    @FXML
    private ComboBox<String> medecinTfd;

    @FXML
    private ComboBox<String> patientTfd;

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void getData(MouseEvent event) {

    }

    @FXML
    void save(ActionEvent event) {

    }

    @FXML
    void update(ActionEvent event) {

    }
    void setListeMedecin(){
        List<String> medecinListe = new ArrayList<>();
        try {
            transaction.begin();
            TypedQuery<String> query = entityManager.createQuery("SELECT m.prenom FROM Medecin m ", String.class);
            List<String> resultList = query.getResultList();
            medecinListe.addAll(resultList);
            transaction.commit();
        }finally {
            if (transaction.isActive())
                transaction.rollback();
        }
        medecinTfd.getItems().addAll(medecinListe);
    }
    public void setListePatient() {
        List<String> patientListe = new ArrayList<>();
        try {
            transaction.begin();
            TypedQuery<String> query = entityManager.createQuery("SELECT p.prenom FROM Patient p ", String.class);
            List<String> resultList = query.getResultList();
            patientListe.addAll(resultList);
            transaction.commit();
        }finally {
            if (transaction.isActive())
                transaction.rollback();
        }
        patientTfd.getItems().addAll(patientListe);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setListeMedecin();
        setListePatient();
    }
}
