package com.example.hopital.entites;
import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "patient")
@NamedQuery(name = "listePatient",query = "SELECT p FROM Patient p ORDER BY p.nom desc ")
public class Patient extends Personne{
    @Column(name = "nomDossier",unique = true)
    private String nomDossier;
    @Column(name = "tel")
    private String tel;

   /* @ManyToMany(mappedBy = "patients")
    private Set<Medecin> medecins;
    public Patient() {
    }*/

    public Patient() {
    }
    public Patient(int idP, String nom, String prenom, String nomDossier, String tel) {
        super(idP, nom, prenom);
        this.nomDossier = nomDossier;
        this.tel = tel;
    }

    public Patient(String nomDossier, String tel) {
        this.nomDossier = nomDossier;
        this.tel = tel;
    }


    public String getNomDossier() {
        return nomDossier;
    }

    public void setNomDossier(String nomDossier) {
        this.nomDossier = nomDossier;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
