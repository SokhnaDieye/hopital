package com.example.hopital.controllers;
import com.example.hopital.entites.Medecin;
import com.example.hopital.entites.Patient;
import com.example.hopital.entites.Specialite;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.persistence.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MedecinControlleur implements Initializable {
    EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = managerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    @FXML
    private TextField nomTfd;

    @FXML
    private TextField prenomTfd;

    @FXML
    private TextField gardeTfd;

    @FXML
    private TextField salaireTfd;

    @FXML
    private ComboBox<String> specialiteTfd;

    @FXML
    private TableView<Medecin> medecinTbl;

    @FXML
    private TableColumn<Medecin, Integer> idCol;

    @FXML
    private TableColumn<Medecin, String> nomCol;

    @FXML
    private TableColumn<Medecin, String> prenomCol;

    @FXML
    private TableColumn<Medecin, String> gardeCol;

    @FXML
    private TableColumn<Medecin, Integer> salaireCol;

    @FXML
    private TableColumn<Medecin, String> specialiteCol;
    @FXML
    private Button ajouterBtn;

    @FXML
    private Button modifierBtn;

    @FXML
    private Button supprimerBtn;
    int id;
    @FXML
    void delete(ActionEvent event) {
        try {
            transaction.begin();
            Medecin medecin = entityManager.find(Medecin.class, id);
            /*patient.getMedecin.clear();*/
            entityManager.remove(medecin);
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
    void getData(MouseEvent event) {
        Medecin medecin=medecinTbl.getSelectionModel().getSelectedItem();
        if (medecin != null) {
            id = medecin.getIdP();
            nomTfd.setText(medecin.getNom());
            prenomTfd.setText(medecin.getPrenom());
            gardeTfd.setText(medecin.getGarde());
            salaireTfd.setText(String.valueOf(medecin.getSalaire()));
            specialiteTfd.getSelectionModel().select(medecin.getCategorieByIdCategorie().getName());
            ajouterBtn.setDisable(true);
        }
    }
    @FXML
    void save(ActionEvent event) {
        try {
            transaction.begin();
            Medecin medecin = new Medecin();
            medecin.setNom(nomTfd.getText());
            medecin.setPrenom(prenomTfd.getText());
            medecin.setGarde(gardeTfd.getText());
            medecin.setSalaire(Integer.parseInt(salaireTfd.getText()));
            String selectedSpecialiteName = specialiteTfd.getValue();
            TypedQuery<Specialite> specialiteQuery = entityManager.createQuery("SELECT s FROM Specialite s WHERE s.name = :name", Specialite.class);
            specialiteQuery.setParameter("name", selectedSpecialiteName);
            Specialite specialite = specialiteQuery.getSingleResult();
            medecin.setspecialiteByIdSpecialite(specialite);
            entityManager.persist(medecin);
            transaction.commit();
            loadTable();
            viderChamps();
            medecinTbl.refresh();
        } finally {
            if (transaction.isActive())
                transaction.rollback();
        }
    }
    public void viderChamps(){
        nomTfd.setText("");
        prenomTfd.setText("");
        gardeTfd.setText("");
        salaireTfd.setText("");
        specialiteTfd.getSelectionModel().clearSelection();
    }
    @FXML
    void update(ActionEvent event) {
        try{
            transaction.begin();
            Medecin medecin = entityManager.find(Medecin.class, id);
            medecin.setNom(nomTfd.getText());
            medecin.setPrenom(prenomTfd.getText());
            medecin.setGarde(gardeTfd.getText());
            medecin.setSalaire(Integer.parseInt(salaireTfd.getText()));
            String selectedSpecialiteName = specialiteTfd.getValue();
            TypedQuery<Specialite> specialiteQuery = entityManager.createQuery("SELECT s FROM Specialite s WHERE s.name = :name", Specialite.class);
            specialiteQuery.setParameter("name", selectedSpecialiteName);
            Specialite specialite = specialiteQuery.getSingleResult();
            medecin.setspecialiteByIdSpecialite(specialite);
            entityManager.persist(medecin);
            transaction.commit();
            loadTable();
            viderChamps();
            ajouterBtn.setDisable(false);
            medecinTbl.refresh();
        }finally {
            if (transaction.isActive())
                transaction.rollback();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
        setListeSpecialite();
    }
    public ObservableList<Medecin> getMedecin(){
        ObservableList<Medecin> medecins = FXCollections.observableArrayList();
        TypedQuery<Medecin> query=entityManager.createNamedQuery("listeMedecin",Medecin.class);
        medecins.addAll(query.getResultList());
        return medecins;
    }
    public void loadTable(){
        medecinTbl.setItems(getMedecin());
        idCol.setCellValueFactory(new PropertyValueFactory<Medecin,Integer>("idP"));
        nomCol.setCellValueFactory(new PropertyValueFactory<Medecin,String>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<Medecin,String>("prenom"));
        gardeCol.setCellValueFactory(new PropertyValueFactory<Medecin,String>("garde"));
        salaireCol.setCellValueFactory(new PropertyValueFactory<Medecin,Integer>("salaire"));
        specialiteCol.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            Medecin medecin = cellData.getValue();
            if (medecin.getCategorieByIdCategorie() != null) {
                property.setValue(medecin.getCategorieByIdCategorie().getName());
            } else {
                property.setValue("Spécialité non définie");
            }
            return property;
        });
    }
    public void setListeSpecialite() {
        List<String> specialiteList = new ArrayList<>();
        try {
            transaction.begin();
            TypedQuery<String> query = entityManager.createQuery("SELECT s.name FROM Specialite s ", String.class);
            List<String> resultList = query.getResultList();
            specialiteList.addAll(resultList);
            transaction.commit();
        }finally {
            if (transaction.isActive())
                transaction.rollback();
        }
        specialiteTfd.getItems().addAll(specialiteList);
    }
}
