package com.example.hopital.entites;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "medecin")
@NamedQuery(name = "listeMedecin",query = "SELECT m FROM Medecin m ORDER BY m.nom desc ")
public class Medecin extends Personne{
    @Column(name = "garde")
    private String garde;
    @Column(name = "salaire")
    private int salaire;
    @ManyToOne
    @JoinColumn(name = "idSepecialite", referencedColumnName = "idM", nullable = false)
    private Specialite specialiteByIdSpecialite;
    public Specialite getCategorieByIdCategorie() {
        return specialiteByIdSpecialite;
    }

    public void setspecialiteByIdSpecialite(Specialite specialiteByIdSpecialite) {
        this.specialiteByIdSpecialite = specialiteByIdSpecialite;
    }


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Patient> patients;


    public Medecin() {
    }

    public Medecin(int idP, String nom, String prenom, String garde, int salaire) {
        super(idP, nom, prenom);
        this.garde = garde;
        this.salaire = salaire;
    }

    /*public Medecin(String garde, int salaire) {
        this.garde = garde;
        this.salaire = salaire;
    }*/
    public String getGarde() {
        return garde;
    }

    public void setGarde(String garde) {
        this.garde = garde;
    }

    public int getSalaire() {
        return salaire;
    }

    public void setSalaire(int salaire) {
        this.salaire = salaire;
    }


}
