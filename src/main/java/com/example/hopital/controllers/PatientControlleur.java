package com.example.hopital.controllers;

import com.example.hopital.entites.Patient;
import com.example.hopital.entites.Specialite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.persistence.*;
import java.net.URL;
import java.util.ResourceBundle;

public class PatientControlleur  implements Initializable {
    EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = managerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    @FXML
    private TextField nomTfd;

    @FXML
    private TextField prenomTfd;

    @FXML
    private TextField nomDossierTfd;

    @FXML
    private TextField telTfd;

    @FXML
    private TableView<Patient> patientTbl;

    @FXML
    private TableColumn<Patient, Integer> idCol;

    @FXML
    private TableColumn<Patient, String> nomCol;

    @FXML
    private TableColumn<Patient, String> prenomCol;

    @FXML
    private TableColumn<Patient, String> nomDossierCol;

    @FXML
    private TableColumn<Patient, String> telCol;

    @FXML
    private Button ajouterBtn;

    @FXML
    private Button modifierBtn;

    @FXML
    private Button supprimerBtn;

    @FXML
    void delete(ActionEvent event) {
        try {
            transaction.begin();
            Patient patient = entityManager.find(Patient.class, id);
            /*patient.getMedecin.clear();*/
            entityManager.remove(patient);
            transaction.commit();
            viderChamps();
            loadTable();
            ajouterBtn.setDisable(false);
        } catch (Exception e) {
            if (transaction.isActive())
                transaction.rollback();
        }
    }

    @FXML
    void update(ActionEvent event) {
        try{
            transaction.begin();
            Patient patient = entityManager.find(Patient.class, id);
            patient.setNom(nomTfd.getText());
            patient.setPrenom(prenomTfd.getText());
            patient.setNomDossier(nomDossierTfd.getText());
            patient.setTel(telTfd.getText());
            entityManager.persist(patient);
            transaction.commit();
            loadTable();
            viderChamps();
            ajouterBtn.setDisable(false);
            patientTbl.refresh();
        }finally {
            if (transaction.isActive())
                transaction.rollback();
        }
    }


    @FXML
    void save(ActionEvent event) {
        try{
            transaction.begin();
            Patient patient = new Patient();
            patient.setNom(nomTfd.getText());
            patient.setPrenom(prenomTfd.getText());
            patient.setNomDossier(nomDossierTfd.getText());
            patient.setTel(telTfd.getText());
            entityManager.persist(patient);
            transaction.commit();
            loadTable();
            viderChamps();
            patientTbl.refresh();

        }finally {
            if (transaction.isActive())
                transaction.rollback();
        }
    }
    public void viderChamps(){
        nomTfd.setText("");
        prenomTfd.setText("");
        nomDossierTfd.setText("");
        telTfd.setText("");
    }
    int id;
    @FXML
    void getData(MouseEvent event) {
        Patient patient=patientTbl.getSelectionModel().getSelectedItem();
        if (patient != null) {
            id = patient.getIdP();
            nomTfd.setText(patient.getNom());
            prenomTfd.setText(patient.getPrenom());
            nomDossierTfd.setText(patient.getNomDossier());
            telTfd.setText(patient.getTel());
            ajouterBtn.setDisable(true);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }
    public ObservableList<Patient> getPatient(){
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        TypedQuery<Patient> query=entityManager.createNamedQuery("listePatient",Patient.class);
        patients.addAll(query.getResultList());
        return patients;
    }
    public void loadTable(){
        patientTbl.setItems(getPatient());
        idCol.setCellValueFactory(new PropertyValueFactory<Patient,Integer>("idP"));
        nomCol.setCellValueFactory(new PropertyValueFactory<Patient,String>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<Patient,String>("prenom"));
        nomDossierCol.setCellValueFactory(new PropertyValueFactory<Patient,String>("nomDossier"));
        telCol.setCellValueFactory(new PropertyValueFactory<Patient,String>("tel"));
    }
}
