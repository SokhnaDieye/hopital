package com.example.hopital.controllers;

import com.example.hopital.entites.Specialite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import javax.persistence.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SpecialiteControlleur implements Initializable {
    EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = managerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    @FXML
    private BorderPane bp;

    @FXML
    private AnchorPane ap;
    @FXML
    void consultation(MouseEvent event) {
        loadPage("patientMedecin");
    }

    @FXML
    void medecin(MouseEvent event) {
        loadPage("medecin");
    }

    @FXML
    void patient(MouseEvent event) {
        loadPage("patient");
    }

    @FXML
    void specialite(MouseEvent event) {
        bp.setCenter(ap);
    }
    private void loadPage(String page) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/pages/"+page + ".fxml"));
            bp.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private TableColumn<Specialite, Integer> idCol;

    @FXML
    private TableColumn<Specialite, String> nomCol;

    @FXML
    private TextField nomTfd;
    @FXML
    private TableView<Specialite> specialiteTbl;
    @FXML
    private Button ajouterBtn;

    @FXML
    private Button supprimerBtn;

    @FXML
    private Button modifierBtn;
    @FXML
    private Text erreur;
    @FXML
    void delete(ActionEvent event) {
        try {
            transaction.begin();
            Specialite specialite = entityManager.find(Specialite.class, id);
            Query query = entityManager.createQuery("SELECT COUNT(m) FROM Medecin m WHERE m.specialiteByIdSpecialite = :specialite");
            query.setParameter("specialite", specialite);
            Long medecinCount = (Long) query.getSingleResult();

            if (medecinCount > 0) {
                erreur.setText("Impossible de supprimer la spécialité car elle est associée à un médecin. ");
                viderChamps();
            }
            entityManager.remove(specialite);
            transaction.commit();
            viderChamps();
            loadTable();
            ajouterBtn.setDisable(false);
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
            if (transaction.isActive())
                transaction.rollback();
        }
    }
    @FXML
    void save(ActionEvent event) {
        try{
            transaction.begin();
            Specialite specialite = new Specialite();
            specialite.setName(nomTfd.getText());
            entityManager.persist(specialite);
            transaction.commit();
            loadTable();
            viderChamps();
            specialiteTbl.refresh();

        }finally {
            if (transaction.isActive())
                transaction.rollback();
        }
    }
    public void viderChamps(){
        nomTfd.setText("");
    }
    int id;
    @FXML
    void getData(MouseEvent event) {
        Specialite specialite=specialiteTbl.getSelectionModel().getSelectedItem();
        if (specialite != null) {
            id = specialite.getId();
            nomTfd.setText(specialite.getName());
            ajouterBtn.setDisable(true);
        }
    }
    @FXML
    void update(ActionEvent event) {
        try{
            transaction.begin();
            Specialite specialite = entityManager.find(Specialite.class, id);
            specialite.setName(nomTfd.getText());
            entityManager.persist(specialite);
            transaction.commit();
            loadTable();
            viderChamps();
            ajouterBtn.setDisable(false);
            specialiteTbl.refresh();
        }finally {
            if (transaction.isActive())
                transaction.rollback();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }
    public ObservableList<Specialite> getSpecialite(){
        ObservableList<Specialite> specialites = FXCollections.observableArrayList();
        TypedQuery<Specialite> query=entityManager.createNamedQuery("listeSpecialite",Specialite.class);
        specialites.addAll(query.getResultList());
        return specialites;
    }
    public void loadTable(){
        specialiteTbl.setItems(getSpecialite());
        idCol.setCellValueFactory(new PropertyValueFactory<Specialite,Integer>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<Specialite,String>("name"));
    }
}
