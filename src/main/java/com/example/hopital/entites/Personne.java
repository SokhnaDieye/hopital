package com.example.hopital.entites;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "personnes")
public abstract class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "idP")
    private int idP;
  /*  @Column(name = "nom")*/
    private String nom;
   /* @Column(name = "prenom")*/
    private String prenom;

    public Personne(int idP, String nom, String prenom) {
        this.idP = idP;
        this.nom = nom;
        this.prenom = prenom;
    }
    public Personne() {
    }
    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
