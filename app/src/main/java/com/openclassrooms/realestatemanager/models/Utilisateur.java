package com.openclassrooms.realestatemanager.models;


import androidx.room.*;

@Entity(indices = {@Index(value = "id")})
public class Utilisateur {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nom;
    private String prenom;

    public Utilisateur(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    // GETTERS - SETTERS //

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
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
