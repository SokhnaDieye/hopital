package com.example.hopital.entites;


import javax.persistence.*;

@Entity
@Table(name = "specialite")
@NamedQuery(name = "listeSpecialite",query = "SELECT s FROM Specialite s ORDER BY s.name desc ")
public class Specialite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idM")
    private int idM;
    @Column(name = "name",unique = true)
    private String name;

    public Specialite() {
    }

    public Specialite(int id, String name) {
        this.idM = id;
        this.name = name;
    }

    public int getId() {
        return idM;
    }

    public void setId(int id) {
        this.idM = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
